package com.asteria.world.object;

import java.util.Arrays;

import com.asteria.world.World;
import com.asteria.world.map.Position;

/**
 * An object that can be constructed and placed anywhere in the {@link World}.
 * 
 * @author lare96
 */
public final class WorldObject {

    /** The id of this object. */
    private final int id;

    /** The position of this object */
    private final Position position;

    /** The direction this object is facing. */
    private final Direction direction;

    /** The type of object that this is. */
    private final Type type;

    /**
     * All of the directions an object can face.
     * 
     * @author lare96
     */
    public enum Direction {
        NORTH(1),
        SOUTH(3),
        EAST(2),
        WEST(0);

        /** The ID of this direction. */
        private int id;

        /**
         * Create a new {@link Direction}.
         * 
         * @param direction
         *            the ID of this direction.
         */
        private Direction(int id) {
            this.id = id;
        }

        /**
         * Gets the ID of this direction.
         * 
         * @return the ID of this direction.
         */
        public int getId() {
            return id;
        }

        /**
         * Retrieves the direction type based on the argued ID.
         * 
         * @param id
         *            the id to retrieve the type of.
         * @return the retrieved object type that corresponds to the argued ID.
         */
        public static Direction forId(int id) {

            // Will throw a 'NoSuchElementException' if an invalid id was given.
            return Arrays.stream(values()).filter(d -> d.id == id).findFirst().get();
        }
    }

    /**
     * All of the types an object can be.
     * 
     * @author Ryley Kimmel <ryley.kimmel@live.com>
     * @author Maxi <http://www.rune-server.org/members/maxi/>
     * @author lare96
     */
    public enum Type {
        STRAIGHT_WALL(0),
        DIAGONAL_CORNER_WALL(1),
        ENTIRE_WALL(2),
        WALL_CORNER(3),
        STRAIGHT_INSIDE_WALL_DECORATION(4),
        STRAIGHT_OUTSIDE_WALL_DECORATION(5),
        DIAGONAL_OUTSIDE_WALL_DECORATION(6),
        DIAGONAL_INSIDE_WALL_DECORATION(7),
        DIAGONAL_INTERIOR_WALL_DECORATION(8),
        DIAGONAL_WALL(9),
        DEFAULT(10),
        WALKABLE_DEFAULT(11),
        STRAIGHT_SLOPED_ROOF(12),
        DIAGONAL_SLOPED_ROOF(13),
        DIAGONAL_SLOPED_CONNECTING_ROOF(14),
        STRAIGHT_SLOPED_CORNER_CONNECTING_ROOF(15),
        STRAIGHT_SLOPED_CORNER_ROOF(16),
        STRAIGHT_FLAT_TOP_ROOF(17),
        STRAIGHT_BOTTOM_EDGE_ROOF(18),
        DIAGONAL_BOTTOM_EDGE_CONNECTING_ROOF(19),
        STRAIGHT_BOTTOM_EDGE_CONNECTING_ROOF(20),
        STRAIGHT_BOTTOM_EDGE_CONNECTING_CORNER_ROOF(21),
        GROUND_PROP(22);

        /** The ID of this object type. */
        private int id;

        /**
         * Create a new {@link Type}.
         *
         * @param id
         *            the ID of this object type.
         */
        private Type(int id) {
            this.id = id;
        }

        /**
         * Gets the ID of this object type.
         * 
         * @return the ID of this object type.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the object group that this object type is in.
         * 
         * @return the object group that this object type is in.
         */
        public ObjectGroup getGroup() {
            return ObjectGroup.forType(this);
        }

        /**
         * Retrieves the object type based on the argued ID.
         * 
         * @param id
         *            the id to retrieve the type of.
         * @return the retrieved object type that corresponds to the argued ID.
         */
        public static Type forId(int id) {

            // Will throw a 'NoSuchElementException' if an invalid id was given.
            return Arrays.stream(values()).filter(t -> t.id == id).findFirst().get();
        }
    }

    /**
     * Represents the group a object belongs to.
     *
     * @author Ryley Kimmel <ryley.kimmel@live.com>
     * @author lare96
     */
    public enum ObjectGroup {
        WALL(0),
        WALL_DECORATION(1),
        GROUP_2(2),
        GROUP_3(3);

        /**
         * Represents the object groups for every type of object based on its
         * index.
         */
        public static final int[] OBJECT_GROUPS = { 0, 0, 0, 0, 1, 1, 1, 1, 1,
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3 };

        /** The id of this object group. */
        private int id;

        /**
         * Constructs a new {@link ObjectGroup} with the specified id.
         *
         * @param id
         *            The if of the object group.
         */
        private ObjectGroup(int id) {
            this.id = id;
        }

        /**
         * Returns an object group for the specified object type
         *
         * @param type
         *            The type of object.
         * @return The object group associated with the object type.
         */
        public static ObjectGroup forType(Type type) {
            int id = OBJECT_GROUPS[type.getId()];

            // Will throw a 'NoSuchElementException' if an invalid type was
            // given.
            return Arrays.stream(values()).filter(obj -> obj.id == id).findFirst().get();
        }
    }

    /**
     * Create a new {@link WorldObject}.
     * 
     * @param id
     *            the id of this object.
     * @param position
     *            the position of this object.
     * @param direction
     *            the direction this object is facing.
     * @param type
     *            the type of object that this is.
     */
    public WorldObject(int id, Position position, Direction direction, Type type) {
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.type = type;
    }

    /**
     * Create a new {@link WorldObject} with the default {@link Type}.
     * 
     * @param id
     *            the id of this object.
     * @param position
     *            the position of this object.
     * @param direction
     *            the direction this object is facing.
     */
    public WorldObject(int id, Position position, Direction direction) {
        this(id, position, direction, Type.DEFAULT);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WorldObject)) {
            return false;
        }

        WorldObject object = (WorldObject) obj;

        return object.id == id && object.position.equals(position) && object.direction == direction && object.type == type;
    }

    /**
     * Gets the id of this object.
     * 
     * @return the id of this object.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the position of this object.
     * 
     * @return the position of this object.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Gets the direction this object is facing.
     * 
     * @return the direction this object is facing.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Gets the type of object that this is.
     * 
     * @return the type of object that this is.
     */
    public Type getType() {
        return type;
    }
}
