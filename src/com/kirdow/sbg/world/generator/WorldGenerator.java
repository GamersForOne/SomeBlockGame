package com.kirdow.sbg.world.generator;

import com.kirdow.sbg.world.Chunk;
import com.kirdow.sbg.world.blocks.Block;

public class WorldGenerator {

    public static final float scale = 1.0f;
    public static final float offset = 10000.0f;


    public static void generateChunk(Chunk chunk) {
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        float[] height = new float[Chunk.WIDTH * Chunk.WIDTH];

        for (int z = 0; z < Chunk.WIDTH; z++) {
            float fZ = (chunkZ + z + offset) / scale;
            for (int x = 0; x < Chunk.WIDTH; x++) {
                float fX = (chunkX + x + offset) / scale;

                float mapped = Perlin.perlinValue(fX, fZ, 21);

                height[x + z * Chunk.WIDTH] = mapped;
            }
        }

        int[] chunkHeight = new int[height.length];

        for (int i = 0; i < height.length; i++) {
            float h = height[i];
            if (h < 0.0f) h = 0.0f;
            if (h > 1.0f) h = 1.0f;

            int iH = (int)(h * (float)Chunk.HEIGHT);

            chunkHeight[i] = iH;
        }

        Block block;
        for (int z = 0; z < Chunk.WIDTH; z++) {
            for (int x = 0; x < Chunk.WIDTH; x++) {
                int index = x + z * Chunk.WIDTH;

                int heightValue = chunkHeight[index];
                if (heightValue < 1) heightValue = 1;

                for (int y = 0; y < Chunk.HEIGHT; y++) {
                    block = Block.blockAir;
                    if (y < heightValue)
                        block = Block.blockStone;

                    chunk.setBlock(x, y, z, block);
                }
            }
        }
    }


}
