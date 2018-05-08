package com.kirdow.sbg.world.mesh;

import com.kirdow.sbg.render.RenderHelper;
import com.kirdow.sbg.util.math.Vector;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class WorldMesh {

    private List<Vertex> vertexList;
    private Vertex[] vertices;

    public WorldMesh() {
        this.vertexList = new ArrayList<>();
        vertices = new Vertex[0];
    }

    public void clear() {
        this.vertexList.clear();
    }

    public void addVertex(VertexBuilder vb) {
        Vertex vertex = vb.build(new Vertex());

        if (vertex != null)
            vertexList.add(vertex);
    }

    public void flush() {
        vertices = vertexList.toArray(new Vertex[0]);

        System.out.println("Vertices: " + vertices.length);
    }

    public void run() {
        if (vertexList != null) {
            RenderHelper.begin(GL11.GL_TRIANGLES);

            for (int i = 0; i < vertices.length; i++)
                vertices[i].run();

            RenderHelper.end();
        }
    }

}
