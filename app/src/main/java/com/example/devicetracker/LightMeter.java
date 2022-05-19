package com.example.devicetracker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public final class LightMeter extends Meter<Float>{

    private final SensorManager sensorManager;
    private final Sensor sensor;
    private final LightSensorEventListener listener;

    public LightMeter(SensorManager sensorManager){
        this.sensorManager = sensorManager;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        listener = new LightSensorEventListener();
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

    private class LightSensorEventListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            setValue(sensorEvent.values[0], sensorEvent.timestamp);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) { }
    }
}
