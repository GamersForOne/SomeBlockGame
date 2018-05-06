package com.kirdow.sbg.util;

import org.lwjgl.Sys;

public class TickLimit {

    private static final double ONE_SECOND = 1_000_000_000.0;

    private final double tps;
    private final double limitTime;

    private double timeElapsed;
    private int readyTicks;

    private double lastNano = -1.0;

    public TickLimit(double tps) {
        this.tps = tps;
        this.limitTime = ONE_SECOND / tps;

        timeElapsed = 0.0;
        readyTicks = 0;
    }

    private double deltaTime() {
        double nanoTime = System.nanoTime();

        if (lastNano < 0.0 && lastNano > -1.1) {
            lastNano = nanoTime;
            return ONE_SECOND / 1000_000.0;
        }

        double deltaTime = nanoTime - lastNano;
        lastNano = nanoTime;
        return deltaTime;
    }

    public boolean poll() {
        double delta = deltaTime();

        timeElapsed += delta;
        while (timeElapsed >= limitTime) {
            timeElapsed -= limitTime;
            ++readyTicks;
        }

        return this.readyTicks-- > 0;
    }

}
