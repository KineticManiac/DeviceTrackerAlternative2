package com.example.devicetracker;

import android.hardware.SensorManager;

//Sensor.TYPE_LINEAR_ACCELERATION ile uğraştım ama çalıştıramadım.
public class LinearAccelerationMeter extends Meter<Vector3>{
    private static final double NS2S = 1.0 / 1000000000.0;

    private final AccelerationMeter accelerationMeter;
    private final Meter<Rotation> rotationMeter;

    private Vector3 gravityValue = Vector3.ZERO;
    private Vector3 biasValue = Vector3.ZERO;

    public LinearAccelerationMeter(SensorManager sensorManager){
        accelerationMeter = new AccelerationMeter(sensorManager);
        accelerationMeter.setOnValueChangedListener(new OnAccelerationMeterValueChangedListener());

        /*
        GyroscopeMeter gyroscopeMeter = new GyroscopeMeter(sensorManager);
        rotationMeter = new IntegrationMeter<>(gyroscopeMeter, 1,
                Rotation.UTILS, Rotation.ZERO);
        rotationMeter.setOnValueChangedListener(new OnRotationMeterValueChangedListener());
        */

        rotationMeter = new RotationMeter(sensorManager);
        rotationMeter.setOnValueChangedListener(new OnRotationMeterValueChangedListener());
    }

    @Override
    public void pause() {
        accelerationMeter.pause();
        rotationMeter.pause();
    }

    @Override
    public void resume() {
        accelerationMeter.resume();
        rotationMeter.resume();
    }

    @Override
    public void calibrate() {
        accelerationMeter.calibrate();
        rotationMeter.calibrate();
        gravityValue = Vector3.ZERO;
    }

    private class OnAccelerationMeterValueChangedListener implements OnValueChangedListener<Vector3>{

        @Override
        public void onValueChanged(Vector3 value, long timestamp) {
            if(Vector3.equals(gravityValue, Vector3.ZERO)) {
                gravityValue = value;
                setValue(Vector3.ZERO, timestamp);
            }
            else {
                setValue(Vector3.add(value, biasValue), timestamp);
            }
        }
    }

    private class OnRotationMeterValueChangedListener implements OnValueChangedListener<Rotation>{

        @Override
        public void onValueChanged(Rotation value, long timestamp) {
            if(!Vector3.equals(gravityValue, Vector3.ZERO)) {
                biasValue = Vector3.scale(gravityValue.applyRotation(value), -1);
            }
        }
    }
}
