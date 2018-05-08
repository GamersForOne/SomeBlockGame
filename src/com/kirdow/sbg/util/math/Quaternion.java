package com.kirdow.sbg.util.math;

public class Quaternion {

    public final float x, y, z, w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector axis, float angle) {
        float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2.0f));
        float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2.0f));

        this.x = axis.x * sinHalfAngle;
        this.y = axis.y * sinHalfAngle;
        this.z = axis.z * sinHalfAngle;
        this.w = cosHalfAngle;
    }

    public float length() {
        return (float)Math.sqrt(x*x+y*y+z*z+w*w);
    }

    public Quaternion normalized() {
        float length = length();

        return new Quaternion(x / length, y / length, z / length, w / length);
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion mul(float r) {
        return new Quaternion(x * r, y * r, z * r, w * r);
    }

    public Quaternion mul(Quaternion r) {
        float w_ = w * r.w - x * r.x - y * r.y - z * r.z;
        float x_ = x * r.w + w * r.x + y * r.z - z * r.y;
        float y_ = y * r.w + w * r.y + z * r.x - x * r.z;
        float z_ = z * r.w + w * r.z + x * r.y - y * r.x;

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion mul(Vector r) {
        float w_ = -x * r.x - y * r.y - z * r.z;
        float x_ = w * r.x + y * r.z - z * r.y;
        float y_ = w * r.y + z * r.x - x * r.z;
        float z_ = w * r.z + x * r.y - y * r.x;

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion sub(Quaternion r) {
        return new Quaternion(x - r.x, y - r.y, z - r.z, w - r.w);
    }

    public Quaternion add(Quaternion r) {
        return new Quaternion(x + r.x, y + r.y, z + r.z, w + r.w);
    }

    public float dot(Quaternion r) {
        return x * r.x + y * r.y + z * r.z + w * r.w;
    }

    public Vector getForward() {
        return new Vector(0, 0, 1).rotate(this);
    }

    public Vector getBackward() {
        return new Vector(0, 0, -1).rotate(this);
    }

    public Vector getLeft() {
        return new Vector(-1, 0, 0).rotate(this);
    }

    public Vector getRight() {
        return new Vector(1, 0, 0).rotate(this);
    }

    public Vector getUp() {
        return new Vector(0, 1, 0).rotate(this);
    }

    public Vector getDown() {
        return new Vector(0, -1, 0).rotate(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Quaternion)) return false;
        Quaternion other = (Quaternion) obj;
        return x == other.x && y == other.y && z == other.z && w == other.w;
    }

}
