package com.kirdow.sbg.render;

import com.kirdow.sbg.util.math.Vector;
import com.kirdow.sbg.world.World;
import com.kirdow.sbg.world.mesh.WorldMesh;
import org.lwjgl.util.glu.GLU;

public class RenderWorld {

    private World world;

    private Vector origin;
    private Vector orientation;

    public RenderWorld() {
        this.world = null;
    }

    public void setWorld(World world) {
        this.world = world;
        origin = new Vector(0.0f, 0.0f, 0.0f);
        orientation = new Vector(0.0f, 0.0f, 0.0f);
    }

    public void setPosition(float x, float y, float z) {
        setPosition(new Vector(x, y, z));
    }

    public void setPosition(Vector vector) {
        this.origin = vector;
    }

    public void setRotation(float pitch, float yaw, float roll) {
        setRotation(new Vector(pitch, yaw, roll));
    }

    public void setRotation(Vector vector) {
        this.orientation = vector;
    }

    public Vector getRenderPosition() {
        return origin == null ? new Vector(0.0f, 0.0f, 0.0f) : origin;
    }

    public Vector getRenderRotation() {
        return orientation == null ? new Vector(0.0f, 0.0f, 0.0f) : orientation;
    }

    public void orient() {
        Vector direction = Vector.fromEuler(getRenderRotation()).normalized();
        Vector origin = getRenderPosition();

        GLU.gluLookAt(origin.x, origin.y, origin.z, direction.x, direction.y, direction.z, 0, 1, 0);
    }

    public void draw() {
        RenderHelper.clear();
        RenderHelper.mode3D();
        RenderHelper.identity();

        orient();

        if (world != null) {
            WorldMesh mesh = world.getMesh();

            if (mesh != null) {
                RenderHelper.enableDepth();
                mesh.run();
                RenderHelper.disableDepth();
            }
        }

        RenderHelper.mode2D();
        RenderHelper.identity();

        draw2d();
    }

    private void draw2d() {

    }

}
