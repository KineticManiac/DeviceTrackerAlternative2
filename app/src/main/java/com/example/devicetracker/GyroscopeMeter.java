package com.example.devicetracker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GyroscopeMeter extends Meter<Rotation>{
    private final SensorManager sensorManager;
    private final Sensor sensor;
    private final GyroscopeSensorEventListener listener;

    public GyroscopeMeter(SensorManager sensorManager){
        this.sensorManager = sensorManager;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        listener = new GyroscopeSensorEventListener();
    }

    @Override
    public void pause() {
        sensorManager.unregisterListener(listener);
    }

    @Override
    public void resume() {
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void calibrate() {
        //Do nothing
    }

    private class GyroscopeSensorEventListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            setValue(new Rotation(
                    new Angle(sensorEvent.values[0]),
                    new Angle(sensorEvent.values[1]),
                    new Angle(sensorEvent.values[2])
            ), sensorEvent.timestamp);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) { }
    }
}
