package com.kirdow.sbg.world.blocks;

public abstract class Block {

    private static final Block[] blockTable = new Block[2048];

    public final int id;

    public Block(int id) {
        this.id = id;
        if (blockTable[id] != null)
            throw new RuntimeException("Block type '" + this.getClass() + "' with id " + id + " has the same id as type '" + blockTable[id].getClass() + "'");
        blockTable[id] = this;
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

}
