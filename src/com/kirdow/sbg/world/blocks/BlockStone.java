package com.kirdow.sbg.world.blocks;

import com.kirdow.sbg.util.math.Vector;

public class BlockStone extends Block {

    public BlockStone(int id) {
        super(id);
    }

    public Vector getColor() {
        return new Vector(0.9f, 0.9f, 0.9f, 1.0f);
    }

}
