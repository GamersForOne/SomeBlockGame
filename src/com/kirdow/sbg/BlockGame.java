package com.kirdow.sbg;

import com.kirdow.sbg.render.RenderWorld;
import com.kirdow.sbg.util.TickCounter;
import com.kirdow.sbg.util.TickLimit;
import com.kirdow.sbg.util.math.Vector;
import com.kirdow.sbg.world.Chunk;
import com.kirdow.sbg.world.World;
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

    private World world;
    private RenderWorld renderWorld;

    public BlockGame() {
        gameThread = new Thread(this::run);
        tickLimit = new TickLimit(40.0);
        tickLimit.setOverflow(false);

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

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(70.0f, (float)width / (float)height, 0.03f, 1000.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        world = new World();
        renderWorld = new RenderWorld();

        renderWorld.setWorld(world);
        for (int z = -2; z <= 2; z++) {
            for (int x = -2; x <= 2; x++) {
                world.loadChunk(x, z);
            }
        }
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

    private Vector position = new Vector(8.0f, Chunk.HEIGHT / 3f - 5.0f, 8.0f);
    private Vector rotation = new Vector();

    private void runTick() {
        position = position.add(new Vector(0.0f, 3.5f / 40.0f, 0.0f));
    }

    private void runRender() {
        renderWorld.setPosition(position);
        renderWorld.setRotation(rotation);

        renderWorld.draw();
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
