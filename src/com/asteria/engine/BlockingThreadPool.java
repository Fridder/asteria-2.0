package com.asteria.engine;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * A thread pool that will execute a queue of tasks and block until all of them
 * have completed. The tasks are first appended to the internal queue using the
 * {@link #append} method and then ran using the {@link #fireAndAwait} method.
 * Once the pool is ran it will be shutdown and can no longer be used. This
 * class is <b>NOT</b> intended for use across multiple threads.
 * 
 * @author lare96
 */
public final class BlockingThreadPool {

    /** The backing thread pool that will execute the pending tasks. */
    private final ExecutorService executor;

    /** A queue that will hold all of the pending tasks. */
    private final Queue<Runnable> tasks = new ArrayDeque<>();

    /**
     * Create a new {@link BlockingThreadPool} with the argued size.
     * 
     * @param size
     *            the maximum amount of threads that will be allocated in this
     *            thread pool.
     */
    public BlockingThreadPool(int size) {
        this.executor = Executors.newFixedThreadPool(
            size,
            new ThreadFactoryBuilder().setNameFormat("BlockingServiceThread").setPriority(
                Thread.MIN_PRIORITY).build());
    }

    /**
     * Create a new {@link BlockingThreadPool} with the size equal to how many
     * processors are available to the JVM.
     */
    public BlockingThreadPool() {
        this(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Appends the argued task to the queue of pending tasks. When this pool is
     * ran using <code>fireAndAwait()</code> all of the pending tasks will be
     * executed in <i>FIFO</> order.
     * 
     * @param r
     *            the task to add to the queue of pending tasks.
     */
    public void append(Runnable r) {
        tasks.add(r);
    }

    /**
     * Submit all of the pending tasks to the backing thread pool and wait for
     * them to complete. Once all of the tasks have completed the backing thread
     * pool will shutdown.
     * 
     * @throws InterruptedException
     *             if this thread is interrupted while awaiting completion.
     */
    public void awaitCompletion() throws InterruptedException {

        // Submit all pending tasks to the executor.
        Runnable r;
        while ((r = tasks.poll()) != null) {
            executor.execute(r);
        }

        // Then shutdown the executor and wait for the tasks to complete.
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }
}