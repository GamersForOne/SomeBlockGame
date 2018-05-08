package com.kirdow.sbg.world;

import com.kirdow.sbg.world.blocks.Block;

public class Chunk {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 128;

    private final Block[][] blocks;
    private final int x, z;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
        this.blocks = new Block[HEIGHT][WIDTH*WIDTH];
    }

    public Block getBlock(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= WIDTH || y >= HEIGHT || z >= WIDTH) return Block.blockAir;

        Block block = blocks[y][x + z * WIDTH];

        return block == null ? Block.blockAir : block;
    }

    public void setBlock(int x, int y, int z, Block block) {
        if (x < 0 || y < 0 || z < 0 || x >= WIDTH || y >= HEIGHT || z >= WIDTH) return;

        blocks[y][x + z * WIDTH] = (block == null ? Block.blockAir : block);
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public boolean isSolid(int x, int y, int z) {
        return getBlock(x, y, z).isSolid();
    }

    public boolean isVisible(int x, int y, int z) {
        return getBlock(x, y, z).isVisible();
    }

}
