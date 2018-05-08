package com.kirdow.sbg.world.mesh;

import com.kirdow.sbg.render.RenderHelper;
import com.kirdow.sbg.util.math.Vector;
import org.lwjgl.opengl.GL11;

public class Vertex {

    private Vector color;
    private Vector position;
    private Vector normal;
    private Vector texCoord;

    public Vertex() {
        this.color = new Vector(1.0f, 1.0f, 1.0f, 1.0f);
        this.position = new Vector(0.0f, 0.0f, 0.0f);
        this.normal = null;
        this.texCoord = null;
    }

    public Vertex setColor(float r, float g, float b) {
        return setColor(r, g, b, 1.0f);
    }

    public Vertex setColor(float r, float g, float b, float a) {
        color = new Vector(r, g, b, a);

        return this;
    }

    public Vertex setPos(float x, float y, float z) {
        position = new Vector(x, y, z);

        return this;
    }

    public Vertex setNormal(float x, float y, float z) {
        normal = new Vector(x, y, z);

        return this;
    }

    public Vertex setNormal() {
        normal = null;

        return this;
    }

    public Vertex setTexCoord(float x, float y) {
        texCoord = new Vector(x, y);

        return this;
    }

    public Vertex setTexCoord() {
        texCoord = null;

        return this;
    }

    public void run() {
        if (position == null) return;

        RenderHelper.color(color.x, color.y, color.z, color.w);
        if (normal != null)
            RenderHelper.normal(normal.x, normal.y, normal.z);
        if (texCoord != null)
            RenderHelper.texcoord(texCoord.x, texCoord.y);
        RenderHelper.vertex(position.x, position.y, position.z);
    }

}
