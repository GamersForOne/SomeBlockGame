package com.kirdow.sbg.world;

import com.kirdow.sbg.world.blocks.Block;

import java.util.HashMap;
import java.util.Map;

public class World {

    private Map<Long, Chunk> chunkMap;

    public World() {
        chunkMap = new HashMap<>();
    }

    public void loadChunk(int x, int y) {
        long _x = (long)x;
        long _y = (long)y;

        long _c = (_x << 32) | _y;
    }

    public long maskChunk(int x, int y) {
        return ((long)x) << 32 | ((long)y);
    }

    public int getChunkCoord(int c, boolean inside) {
        if (inside) {
            if (c < 0)
                return 15 - (-c - 1) % Chunk.WIDTH;
            return c % Chunk.WIDTH;
        } else {
            if (c < 0)
                return -((-c - 1) / Chunk.WIDTH) - 1;
            return c / Chunk.WIDTH;
        }
    }

    public long maskChunkFromWorld(int x, int y) {
        return maskChunk(getChunkCoord(x, false), getChunkCoord(y, false));
    }

    public Chunk getChunk(int x, int y) {
        long chunkMask = maskChunk(x, y);

        return chunkMap.getOrDefault(chunkMask, null);
    }

    public Chunk getChunkAt(int x, int y) {
        int _x = getChunkCoord(x, false);
        int _y = getChunkCoord(y, false);

        return getChunk(_x, _y);
    }

    public boolean isChunkLoaded(int x, int y) {
        return getChunk(x, y) != null;
    }

    public boolean isChunkLoadedAt(int x, int y) {
        return getChunkAt(x, y) != null;
    }

    public Block getBlockAt(int x, int y, int z) {
        Chunk chunk = getChunkAt(x, z);

        if (chunk == null) return Block.blockAir;

        int _x = getChunkCoord(x, true);
        int _z = getChunkCoord(z, true);

        return chunk.getBlock(_x, y, _z);
    }

    public Block setBlockAt(int x, int y, int z, Block block) {
        Chunk chunk = getChunkAt(x, z);

        if (chunk == null) return Block.blockAir;

        int _x = getChunkCoord(x, true);
        int _z = getChunkCoord(z, true);

        Block _block = chunk.getBlock(_x, y, _z);
        chunk.setBlock(_x, y, _z, block);

        return _block;
    }

}
