package com.kirdow.sbg.world.blocks;

import com.kirdow.sbg.util.math.Vector;

public class BlockAir extends Block {

    public BlockAir(int id) {
        super(id);
    }

    public Vector getColor() {
        return new Vector();
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
