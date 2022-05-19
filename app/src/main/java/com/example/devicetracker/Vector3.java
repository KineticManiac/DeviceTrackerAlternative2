package com.example.devicetracker;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;

public final class Vector3 {
    public static final Utils UTILS = new Utils();
    public static final Vector3 ZERO = new Vector3(0, 0 ,0);

    private final double x, y, z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v){
        this(v.x, v.y, v.z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getAbsoluteSquare(){
        return x*x + y*y + z*z;
    }

    public double getAbsolute(){
        return Math.sqrt(getAbsoluteSquare());
    }

    public Vector3 getUnit(){
        return scale(this, 1.0 / getAbsolute());
    }

    public Vector3 applyRotation(Rotation r){
        final double a = r.getX().getValue(), b = r.getY().getValue(), c = r.getZ().getValue();
        final double cosa = Math.cos(a), cosb = Math.cos(b), cosc = Math.cos(c);
        final double sina = Math.sin(a), sinb = Math.sin(b), sinc = Math.sin(c);
        return new Vector3(
                x * (cosb * cosc) +
                        y * (sina * sinb * cosc - cosa * sinc) +
                        z * (cosa * sinb * cosc + sina * sinc),
                x * (cosb * sinc) +
                        y * (sina * sinb * sinc + cosa * cosc) +
                        z * (cosa * sinb * sinc - sina * cosc),
                x * (-sinb) +
                        y * (sina * cosb) +
                        z * (cosa * cosb));
    }

    static public boolean equals(@NonNull Vector3 v1, @NonNull Vector3 v2){
        return (v1.x == v2.x) && (v1.y == v2.y) && (v1.z == v2.z);
    }

    static public Vector3 add(@NonNull Vector3 v1, @NonNull Vector3 v2){
        return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    static public Vector3 scale(@NonNull Vector3 v, double d){
        return new Vector3(v.x * d, v.y * d, v.z * d);
    }

    @Override
    public String toString(){
        return String.format("x: %f, y: %f, z: %f", x, y ,z);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Vector3){
            return equals(this, (Vector3) obj);
        }
        else{
            return false;
        }
    }

    static public final class Utils implements VectorUtils<Vector3>{

        @Override
        public boolean equals(Vector3 t1, Vector3 t2) {
            return Vector3.equals(t1, t2);
        }

        @Override
        public Vector3 add(Vector3 t1, Vector3 t2) {
            return Vector3.add(t1, t2);
        }

        @Override
        public Vector3 scale(Vector3 t, double d) {
            return Vector3.scale(t, d);
        }
    }

}
