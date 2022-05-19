package com.example.devicetracker;

public final class Rotation {
    public static final Utils UTILS = new Utils();
    public static final Rotation ZERO = new Rotation(Angle.ZERO, Angle.ZERO, Angle.ZERO);

    private final Angle x, y, z;

    public Rotation(Angle x, Angle y, Angle z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Angle getX() {
        return x;
    }

    public Angle getY() {
        return y;
    }

    public Angle getZ() {
        return z;
    }

    public Vector3 getUnitVector() {
        return new Vector3(
                Math.cos(x.getValue()),
                Math.cos(y.getValue()),
                Math.cos(z.getValue())
        );
    }

    static public boolean equals(Rotation r1, Rotation r2){
        return Angle.equals(r1.x, r2.x) &&
                Angle.equals(r1.y, r2.y) &&
                Angle.equals(r1.z, r2.z);
    }

    static public Rotation add(Rotation r1, Rotation r2){
       return new Rotation(
               Angle.add(r1.x, r2.x),
               Angle.add(r1.y, r2.y),
               Angle.add(r1.z, r2.z)
       );
    }

    static public Rotation scale(Rotation r, double d){
        return new Rotation(
                Angle.scale(r.x, d),
                Angle.scale(r.y, d),
                Angle.scale(r.z, d)
        );
    }

    public static class Utils implements VectorUtils<Rotation>{

        @Override
        public boolean equals(Rotation t1, Rotation t2) {
            return Rotation.equals(t1, t2);
        }

        @Override
        public Rotation add(Rotation t1, Rotation t2) {
            return Rotation.add(t1, t2);
        }

        @Override
        public Rotation scale(Rotation rotation, double d) {
            return Rotation.scale(rotation, d);
        }
    }
}
