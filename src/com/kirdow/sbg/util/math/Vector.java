package com.kirdow.sbg.util.math;

import com.kirdow.sbg.util.UtilUtils;

public class Vector {

    public final float x, y, z, w;

    public Vector(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector(float x, float y, float z) {
        this(x, y, z, 0.0f);
    }

    public Vector(float x, float y) {
        this(x, y, 0.0f);
    }

    public Vector(float f) {
        this(f, f);
    }

    public Vector() {
        this(0.0f, 0.0f);
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    public Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    public Vector mul(Vector v) {
        return new Vector(x * v.x, y * v.y, z * v.z, w * v.w);
    }

    public Vector div(Vector v) {
        return new Vector(x / v.x, y / v.y, z / v.z, w / v.w);
    }

    public Vector divSafe(final Vector v) {
        float _x = UtilUtils.saveCall(() -> x / v.x, 0.0f);
        float _y = UtilUtils.saveCall(() -> y / v.y, 0.0f);
        float _z = UtilUtils.saveCall(() -> z / v.z, 0.0f);
        float _w = UtilUtils.saveCall(() -> w / v.w, 0.0f);

        return new Vector(_x, _y, _z, _w);
    }

    public float dot(Vector v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    public float length() {
        return (float)Math.sqrt(dot(this));
    }

    public Vector normalized() {
        float length = length();

        return new Vector(x / length, y / length, z / length, w / length);
    }

    public Vector sub() {
        return new Vector(-x, -y, -z, -w);
    }

}
