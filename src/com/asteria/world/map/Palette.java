/* Copyright (c) 2009 Graham Edgecombe, Blake Beaupain and Brett Russell
 *
 * More information about Hyperion may be found on this website:
 *    http://hyperion.grahamedgecombe.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.asteria.world.map;

/**
 * Manages a palette of map regions for use in the constructed map region
 * packet.
 * 
 * @author Graham
 * 
 */
public class Palette {

    /**
     * Normal direction.
     */
    public static final int DIRECTION_NORMAL = 0;

    /**
     * Rotation direction clockwise by 0 degrees.
     */
    public static final int DIRECTION_CW_0 = 0;

    /**
     * Rotation direction clockwise by 90 degrees.
     */
    public static final int DIRECTION_CW_90 = 1;

    /**
     * Rotation direction clockwise by 180 degrees.
     */
    public static final int DIRECTION_CW_180 = 2;

    /**
     * Rotation direction clockwise by 270 degrees.
     */
    public static final int DIRECTION_CW_270 = 3;

    /**
     * Represents a tile to copy in the palette.
     * 
     * @author Graham Edgecombe
     * 
     */
    public static class PaletteTile {

        /**
         * X coordinate.
         */
        private int x;

        /**
         * Y coordinate.
         */
        private int y;

        /**
         * Z coordinate.
         */
        private int z;

        /**
         * Rotation.
         */
        private int rot;

        /**
         * Creates a tile.
         * 
         * @param x
         *            The x coordinate.
         * @param y
         *            The y coordinate.
         */
        public PaletteTile(int x, int y) {
            this(x, y, 0);
        }

        /**
         * Creates a tile.
         * 
         * @param x
         *            The x coordinate.
         * @param y
         *            The y coordinate.
         * @param z
         *            The z coordinate.
         */
        public PaletteTile(int x, int y, int z) {
            this(x, y, z, DIRECTION_NORMAL);
        }

        /**
         * Creates a tile.
         * 
         * @param x
         *            The x coordinate.
         * @param y
         *            The y coordinate.
         * @param z
         *            The z coordinate.
         * @param rot
         *            The rotation.
         */
        public PaletteTile(int x, int y, int z, int rot) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.rot = rot;
        }

        /**
         * Gets the x coordinate.
         * 
         * @return The x coordinate.
         */
        public int getX() {
            return x / 8;
        }

        /**
         * Gets the y coordinate.
         * 
         * @return The y coordinate.
         */
        public int getY() {
            return y / 8;
        }

        /**
         * Gets the z coordinate.
         * 
         * @return The z coordinate.
         */
        public int getZ() {
            return z % 4;
        }

        /**
         * Gets the rotation.
         * 
         * @return The rotation.
         */
        public int getRotation() {
            return rot % 4;
        }

    }

    /**
     * The array of tiles.
     */
    private PaletteTile[][][] tiles = new PaletteTile[13][13][4];

    /**
     * Gets a tile.
     * 
     * @param x
     *            X position.
     * @param y
     *            Y position.
     * @param z
     *            Z position.
     * @return The tile.
     */
    public PaletteTile getTile(int x, int y, int z) {
        return tiles[x][y][z];
    }

    /**
     * Sets a tile.
     * 
     * @param x
     *            X position.
     * @param y
     *            Y position.
     * @param z
     *            Z position.
     * @param tile
     *            The tile.
     */
    public void setTile(int x, int y, int z, PaletteTile tile) {
        tiles[x][y][z] = tile;
    }

}