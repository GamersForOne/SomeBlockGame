package com.kirdow.sbg.world.blocks;

public class BlockAir extends Block {

    public BlockAir(int id) {
        super(id);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
