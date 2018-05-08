package com.kirdow.sbg.world;

import com.kirdow.sbg.util.math.MathUtils;
import com.kirdow.sbg.util.math.Vector;
import com.kirdow.sbg.world.blocks.Block;
import com.kirdow.sbg.world.generator.WorldGenerator;
import com.kirdow.sbg.world.mesh.WorldMesh;

import java.util.HashMap;
import java.util.Map;

public class World {

    private Map<Long, Chunk> chunkMap;
    private WorldMesh mesh;
    private boolean dirty;


    private final Vector colorX = new Vector(0.85f, 0.85f, 0.85f, 1.0f).mul(new Vector(1.0f, 0.75f, 0.5f, 1.0f));
    private final Vector colorZ = new Vector(0.82f, 0.82f, 0.82f, 1.0f).mul(new Vector(0.5f, 1.0f, 0.75f, 1.0f));
    private final Vector colorY = new Vector(0.91f, 0.91f, 0.91f, 1.0f).mul(new Vector(0.75f, 0.5f, 1.0f, 1.0f));

    public World() {
        chunkMap = new HashMap<>();
        mesh = new WorldMesh();
        dirty = false;
    }

    public World loadChunk(int chunkX, int chunkY) {
        long chunkMask = maskChunk(chunkX, chunkY);

        System.out.println("ChunkMask for " + chunkX + ";" + chunkY + " is " + Long.toHexString(chunkMask));

        Chunk chunk = new Chunk(chunkX, chunkY);

        WorldGenerator.generateChunk(chunk);

        chunkMap.put(chunkMask, chunk);

        dirty = true;

        return this;
    }

    public long maskChunk(int x, int y) {
        return (((long)x) << 32) | (y & 0xFFFFFFFFL);
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

        dirty = true;

        return _block;
    }

    public WorldMesh getMesh() {
        if (dirty) {
            updateMesh();
        }

        return this.mesh;
    }

    private void updateMeshBlock(int x, int y, int z, int worldX, int worldY, int worldZ, Chunk chunk) {
        Block block = chunk.getBlock(x, y, z);
        if (!block.isVisible()) return;

        Block blockUp = chunk.getBlock(x, y+1, z);
        Block blockDown = chunk.getBlock(x, y-1, z);

        Block blockXup = chunk.getBlock(x+1, y, z);
        Block blockXdown = chunk.getBlock(x-1, y, z);

        Block blockZup = chunk.getBlock(x, y, z+1);
        Block blockZdown = chunk.getBlock(x, y, z-1);

        Vector blockColor = block.getColor();

        if (!blockUp.isVisible()) {
            Vector vector = blockColor.mul(colorY);

            mesh.addVertex(vertex -> vertex.setColor(vector.x / 2.0f, vector.y * 1.05f / 2.0f, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY + 1, worldZ));

            mesh.addVertex(vertex -> vertex.setColor(vector.x * 2.0f, vector.y / 1.05f / 2.0f, vector.z / 2.0f, vector.w).setPos(worldX + 1, worldY + 1, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY + 1, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ + 1));
        }

        if (!blockDown.isVisible()) {
            Vector vector = blockColor.mul(colorY);

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ + 1));

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY, worldZ));
        }

        if (!blockZdown.isVisible()) {
            Vector vector = blockColor.mul(colorZ);

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY, worldZ));

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY + 1, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ));
        }

        if (!blockZup.isVisible()) {
            Vector vector = blockColor.mul(colorZ);

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY + 1, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ + 1));

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY + 1, worldZ + 1));
        }

        if (!blockXdown.isVisible()) {
            Vector vector = blockColor.mul(colorX);

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ));

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX, worldY + 1, worldZ + 1));
        }

        if (!blockXup.isVisible()) {
            Vector vector = blockColor.mul(colorX);

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY + 1, worldZ));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY, worldZ + 1));

            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY + 1, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1 , worldY, worldZ + 1));
            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(worldX + 1, worldY + 1, worldZ));
        }
    }
// Vertices: 719112
    private void updateMeshChunk(int chunkX, int chunkZ, Chunk chunk) {
        if (true) {
            updateMeshChunk2(chunkX, chunkZ, chunk);
            return;
        }
        int chunkWorldX = chunkX * Chunk.WIDTH;
        int chunkWorldZ = chunkZ * Chunk.WIDTH;

        for (int y = 0; y < Chunk.HEIGHT; y++) {
            for (int z = 0; z < Chunk.WIDTH; z++) {
                for (int x = 0; x < Chunk.WIDTH; x++) {
                    int worldX = chunkWorldX + x;
                    int worldY = y;
                    int worldZ = chunkWorldZ + z;

                    updateMeshBlock(x, y, z, worldX, worldY, worldZ, chunk);
                }
            }
        }
    }

    private void updateMeshChunk2(int chunkX, int chunkZ, Chunk chunk) {
        int[] dims = new int[]{Chunk.WIDTH, Chunk.HEIGHT, Chunk.WIDTH};
        for (int axis = 0; axis < 3; axis++) {
            final int u = (axis + 1) % 3;
            final int v = (axis + 2) % 3;

            int[] x = new int[3], q = new int[3], mask = new int[dims[u] * dims[v]];

            q[axis] = 1;
            for (x[axis] = -1; x[axis] < dims[axis];) {
                int counter = 0;
                for (x[v] = 0; x[v] < dims[v]; x[v]++) {
                    for (x[u] = 0; x[u] < dims[u]; x[u]++, counter++) {
                        final int a = 0 <= x[axis] ? chunk.getBlock(x[0], x[1], x[2]).id : Block.blockAir.id;
                        final int b = x[axis] < dims[axis] - 1 ? chunk.getBlock(x[0] + q[0], x[1] + q[1], x[2] + q[2]).id : Block.blockAir.id;

                        final boolean ba = a != 0;
                        if (ba == (b != 0))
                            mask[counter] = 0;
                        else if (ba)
                            mask[counter] = a;
                        else
                            mask[counter] = -b;
                    }
                }

                x[axis]++;

                int width = 0, height = 0;

                counter = 0;
                for (int j = 0; j < dims[v]; j++)
                    for (int i = 0; i < dims[u];) {
                        int c = mask[counter];
                        if (c != 0) {
                            for (width = 1; i + width < dims[u] && c == mask[counter + width]; width++) {}

                            boolean done = false;
                            for (height = 1; j + height < dims[v]; height++) {
                                for (int k = 0; k < width; k++) {
                                    if (c != mask[counter + k + height * dims[u]]) {
                                        done = true;
                                        break;
                                    }
                                }
                                if (done)
                                    break;
                            }

                            x[u] = i;
                            x[v] = j;

                            int[] du = new int[3], dv = new int[3];

                            if (c > 0) {
                                dv[v] = height;
                                du[u] = width;
                            } else {
                                c = -c;
                                du[v] = height;
                                dv[u] = width;
                            }

                            Block block = Block.getBlock(c);
                            Vector vectorRaw = block.getColor();
                            Vector vector = vectorRaw.mul(axis == 0 ? colorX : (axis == 1 ? colorY : colorZ));

                            mesh.addVertex(vertex -> vertex.setColor(vector.x + 0.05f, vector.y, vector.z, vector.w).setPos(x[0], x[1], x[2]));;
                            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(x[0] + du[0], x[1] + du[1], x[2] + du[2]));
                            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z - 0.05f, vector.w).setPos(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1], x[2] + du[2] + dv[2]));;

                            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y * 1.05f, vector.z, vector.w).setPos(x[0], x[1], x[2]));;
                            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y / 1.05f, vector.z, vector.w).setPos(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1], x[2] + du[2] + dv[2]));;
                            mesh.addVertex(vertex -> vertex.setColor(vector.x, vector.y, vector.z, vector.w).setPos(x[0] + dv[0], x[1] + dv[1], x[2] + dv[2]));;

                            for (int b = 0; b < width; b++)
                                for (int a = 0; a < height; a++)
                                    mask[counter + b + a * dims[u]] = 0;

                            i += width; counter += width;
                        } else {
                            ++i;
                            ++counter;
                        }
                    }
            }
        }
    }

    public void updateMesh() {
        mesh.clear();

        for (Map.Entry<Long, Chunk> chunkEntry : chunkMap.entrySet()) {
            long key = chunkEntry.getKey();
            int chunkX = (int)(key >> 32);
            int chunkZ = (int)key;
            Chunk chunk = chunkEntry.getValue();

            updateMeshChunk(chunkX, chunkZ, chunk);
        }

        mesh.flush();

        dirty = false;
    }


}
