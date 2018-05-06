package com.kirdow.sbg.world;

import com.kirdow.sbg.world.blocks.Block;

public class Chunk {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 128;

    private final Block[][] blocks;
    private final int x, y;

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
        this.blocks = new Block[HEIGHT][WIDTH*WIDTH];
    }

    public Block getBlock(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= WIDTH || y >= HEIGHT || z >= WIDTH) return null;

        return blocks[y][x + z * WIDTH];
    }

    public void setBlock(int x, int y, int z, Block block) {
        if (x < 0 || y < 0 || z < 0 || x >= WIDTH || y >= HEIGHT || z >= WIDTH) return;

        blocks[y][x + z * WIDTH] = block;
    }

}
