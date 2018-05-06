package com.kirdow.sbg;

import com.kirdow.sbg.util.TickCounter;
import com.kirdow.sbg.util.TickLimit;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class BlockGame {

    private static BlockGame instance;
    public static BlockGame getBlockGame() {
        return instance;
    }

    private Thread gameThread;
    private boolean running;
    private TickLimit tickLimit;
    private TickCounter tickCounter;

    public BlockGame() {
        gameThread = new Thread(this::run);
        tickLimit = new TickLimit(40.0);
        tickCounter = new TickCounter(1.0);
    }

    private void init() {
        int width = GameSettings.getWidth();
        int height = GameSettings.getHeight();

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            running = false;
        }

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(70.0f, (float)width / (float)height, 0.13f, 1000.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void start() {
        running = true;
        gameThread.start();
    }

    public void join() {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        init();

        while (running) {
            runInterval();

            if (Display.isCloseRequested())
                running = false;
        }
    }

    private void runTick() {

    }

    private void runRender() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        GL11.glEnd();
    }

    private void runInterval() {
        while (tickLimit.poll())
            runTick();

        tickCounter.poll(this::logFps);
        runRender();
        Display.update();
    }

    private void logFps(double fps) {
        int whole = (int)fps;
        int part = (int)(fps * 100.0 - whole * 100);

        System.out.println(String.format("FPS: %s.%s", whole, part));
    }

}
