package com.kirdow.sbg.util.math;

public class MathUtils {

    public static int splitLong(long input, int byteIndex) {
        int num = 0x0;

        for (int i = 0; i < 4; i++) {
            int byteIndex2 = byteIndex + i;
            if (byteIndex2 >= 8 || byteIndex2 < 0) continue;

            int bitIndex = byteIndex2 * 8;
            int bitIndex2 = i * 8;

            int byteNumber = (int)((input >> bitIndex) & 0xFF);
            int frameNumber = byteNumber << bitIndex2;

            num |= frameNumber;
        }

        return num;
    }

    public static float translatePoint(float srcMin, float srcMax, float dstMin, float dstMax, float point) {
        float factor = (point - srcMin) / (srcMax - srcMin);

        return factor * (dstMax - dstMin) + dstMin;
    }

}
