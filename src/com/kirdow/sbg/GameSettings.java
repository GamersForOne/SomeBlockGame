package com.kirdow.sbg;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class GameSettings {

    private static GameSettings settings;

    private int width, height;

    private GameSettings(String[] args) {
        width = 1280;
        height = 720;

        String preArg = null;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (preArg != null) {
                if (preArg.equals("width")) {
                    try {
                        width = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                    }
                } else if (preArg.equals("height")) {
                    try {
                        height = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                    }
                }

                preArg = null;
            } else {
                if (arg.charAt(0) == '-') {
                    preArg = arg.substring(1);
                    continue;
                }
            }
        }
    }

    public static void init(String[] args) {
        settings = new GameSettings(args);
    }

    private static final Object lock = new Object();

    public static int getWidth() {
        synchronized (lock) {
            if (settings == null) return -1;
            return settings.width;

        }
    }

    public static int getHeight() {
        synchronized (lock) {
            if (settings == null) return -1;
            return settings.height;
        }
    }

    public static void setWidth(int width) {
        synchronized (lock) {
            if (settings == null) return;
            settings.width = width;
        }
    }

    public static void setHeight(int height) {
        synchronized (lock) {
            if (settings == null) return;
            settings.height = height;
        }
    }

    public static void setSize(int width, int height) {
        synchronized (lock) {
            if (settings == null) return;
            settings.width = width;
            settings.height = height;
        }
    }

}
