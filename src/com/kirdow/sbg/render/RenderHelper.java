package com.kirdow.sbg.render;

import com.kirdow.sbg.GameSettings;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class RenderHelper {

    public static void color(float red, float green, float blue, float alpha) {
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void color(float red, float green, float blue) {
        GL11.glColor3f(red, green, blue);
    }

    public static void vertex(float x, float y, float z) {
        GL11.glVertex3f(x, y, z);
    }

    public static void vertex(float x, float y) {
        GL11.glVertex2f(x, y);
    }

    public static void texcoord(float x, float y) {
        GL11.glTexCoord2f(x, y);
    }

    public static void normal(float x, float y, float z) {
        GL11.glNormal3f(x, y, z);
    }

    public static void begin(int mode) {
        GL11.glBegin(mode);
    }

    public static void end() {
        GL11.glEnd();
    }

    public static void texture(int texture) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
    }

    public static void texture() {
        texture(0);
    }

    public static void enableTexture() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void disableTexture() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public static void enableAlpha() {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    public static void disableAlpha() {
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    public static void enableBlend() {
        GL11.glEnable(GL11.GL_BLEND);
    }

    public static void disableBlend() {
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void enableDepth() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void disableDepth() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void clear(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    public static void clearDepth() {
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void clearColor() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public static void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void mode2D() {
        double width = GameSettings.getWidth();
        double height = GameSettings.getHeight();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        identity();
        GL11.glOrtho(0.0, width, height, 0.0, -100.0, 100.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        identity();
    }

    public static void mode3D() {
        float width = (float)GameSettings.getWidth();
        float height = (float)GameSettings.getHeight();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        identity();
        GLU.gluPerspective(70.0f, width / height, 0.13f, 1000.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        identity();
    }

    public static void identity() {
        GL11.glLoadIdentity();
    }

}
