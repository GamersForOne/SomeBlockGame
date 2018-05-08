package com.kirdow.sbg.world.generator;

import com.kirdow.sbg.util.math.MathUtils;

import java.util.Random;

public final class Perlin {

    private Perlin() {}

    private static final int IYMAX_FINAL;
    private static final int IXMAX_FINAL;
    private static final float[][][] GRADIENT_FINAL;

    public static int IYMAX;
    public static int IXMAX;
    public static float[][][] GRADIENT;


    public static float lerp(float a0, float a1, float w) {
        return (1.0f - w) * a0 + w * a1;
    }

    public static float dotGridGradient(int ix, int iy, float x, float y) {
        float dx = x - (float)ix;
        float dy = y - (float)iy;

        return (dx*GRADIENT[iy % IYMAX][ix % IXMAX][0] + dy*GRADIENT[iy % IYMAX][ix % IXMAX][1]);
    }

    public static float perlin(float x, float y) {
        int x0 = (int)x;
        int x1 = x0 + 1;
        int y0 = (int)y;
        int y1 = y0 + 1;

        float sx = x - (float)x0;
        float sy = y - (float)y0;

        float n0, n1, ix0, ix1, value;
        n0 = dotGridGradient(x0, y0, x, y);
        n1 = dotGridGradient(x1, y0, x, y);
        ix0 = lerp(n0, n1, sx);
        n0 = dotGridGradient(x0, y1, x, y);
        n1 = dotGridGradient(x1, y1, x, y);
        ix1 = lerp(n0, n1, sx);
        value = lerp(ix0, ix1, sy);

        return MathUtils.translatePoint(-0.28850755f, 0.2916104f, 0.0f, 1.0f, value);
    }

    public static float perlinValue(float x, float y) {
        return perlinValue(x, y, 2);
    }

    private static float min = 0.0f, max = 0.0f;

    public static float perlinValue(float x, float y, int segments) {
        if (segments < 1) segments = 1;
        float total = 0;
        int nums = 0;

        for (float yseg = 1; yseg <= segments; yseg++) {
            float yPos = y / yseg;
            for (float xseg = 1; xseg <= segments; xseg++) {
                float xPos = x / xseg;

                float value = perlin(xPos, yPos);
                if (value < min) min = value;
                if (value > max) max = value;

//                System.out.println("VALUE: " + value);

                total += value;
                ++nums;
            }
        }

        return total / nums;
    }

    static {
        Random random = new Random(0);
        int ymax = random.nextInt(100) + 51;
        int xmax = ymax;

        float[][][] gradient = new float[ymax][xmax][2];

        for (int y = 0; y < ymax; y++) {
            for (int x = 0; x < xmax; x++) {
                float g0 = Math.abs(random.nextFloat()) + 0.00001f;
                float g1 = Math.abs(random.nextFloat()) + 0.00002f;
                float gl = (float)Math.sqrt(g0*g0+g1*g1);

                gradient[y][x][0] = g0 / gl;
                gradient[y][x][1] = g1 / gl;
            }
        }

        IYMAX = IYMAX_FINAL = ymax;
        IXMAX = IXMAX_FINAL = xmax;
        GRADIENT = GRADIENT_FINAL = gradient;
    }

}
