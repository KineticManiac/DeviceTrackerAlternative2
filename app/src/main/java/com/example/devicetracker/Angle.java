package com.example.devicetracker;

public final class Angle {
    public static final Utils UTILS = new Utils();
    public static final Angle ZERO = new Angle(0);

    private static final double HALF_PI = Math.PI / 2;
    private final double value;

    public Angle(double value){
        assert Double.isFinite(value);
        this.value = value % HALF_PI;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Angle){
            return equals(this, (Angle) obj);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Double.hashCode(value);
    }

    static public boolean equals(Angle a1, Angle a2){
        return a1.value == a2.value;
    }

    static public Angle add(Angle a1, Angle a2){
        return new Angle(a1.value + a2.value);
    }

    static public Angle scale(Angle a, double d) { return new Angle(a.value * d); }

    static public class Utils implements VectorUtils<Angle>{

        @Override
        public boolean equals(Angle t1, Angle t2) {
            return Angle.equals(t1, t2);
        }

        @Override
        public Angle add(Angle t1, Angle t2) {
            return Angle.add(t1, t2);
        }

        @Override
        public Angle scale(Angle angle, double d) {
            return Angle.scale(angle, d);
        }
    }
}
