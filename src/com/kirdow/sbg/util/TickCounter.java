package com.kirdow.sbg.util;

import org.lwjgl.Sys;

public class TickCounter {

    public interface TickCounterCallback { void run(double ticks); }
    private static final double ONE_SECOND = 1_000_000_000.0;

    private final double interval;

    private double timeElapsed;
    private double ticker;

    public TickCounter(double seconds) {
        interval = ONE_SECOND * seconds;

        timeElapsed = 0.0;
        ticker = 0.0;
    }

    private double lastNano = -0.5;
    private double deltaTime() {
        double nanoTime = System.nanoTime();

        if (lastNano < 0.0 && lastNano > -1.0) {
            lastNano = nanoTime;
            return ONE_SECOND / 1000_000.0;
        }

        double deltaNano = nanoTime - lastNano;
        lastNano = nanoTime;
        return deltaNano;
    }

    public void poll(TickCounterCallback callback) {
        double delta = deltaTime();

        timeElapsed += delta;
        ++ticker;

        if (timeElapsed >= interval) {
            timeElapsed -= interval;
            double tps = ticker - (timeElapsed / delta);
            ticker = 0.0;

            callback.run(tps);
        }
    }

}
