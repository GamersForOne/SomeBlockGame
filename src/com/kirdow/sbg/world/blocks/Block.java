package com.kirdow.sbg.world.blocks;

import com.kirdow.sbg.util.math.Vector;

public abstract class Block {

    private static final Block[] blockTable = new Block[2048];

    public final int id;

    public Block(int id) {
        this.id = id;
        if (blockTable[id] != null)
            throw new RuntimeException("Block type '" + this.getClass() + "' with id " + id + " has the same id as type '" + blockTable[id].getClass() + "'");
        blockTable[id] = this;
    }

    public abstract Vector getColor();

    public final Vector getColor(Vector blend) {
        return getColor().mul(blend);
    }

    public boolean isSolid() {
        return true;
    }

    public boolean isVisible() {
        return true;
    }

    public static final Block blockAir;
    public static final Block blockStone;

    static {
        blockAir = (new BlockAir(0));
        blockStone = (new BlockStone(1));
    }

    public static Block getBlock(int id) {
        if (id < 0 || id >= blockTable.length) return blockAir;
        if (blockTable[id] == null) return blockAir;

        return blockTable[id];
    }

}
