@echo off
title asteria_run
"C:/Program Files/Java/jdk1.8.0_11/bin/java.exe" -Xmx1024m -cp bin;deps/gson-2.2.4.jar;deps/guava-18.0.jar; com.asteria.Main 
pause