package com.example.devicetracker;

import androidx.annotation.NonNull;

//Bir değerin integralini (gerekirse birden fazla kez) alır.
public class IntegrationMeter<T> extends Meter<T>{
    private static final double NS2S = 1.0 / 1000000000.0;

    private final VectorUtils<T> utils;

    private final Meter<T> meter;
    private T rawValue, biasValue;
    private long lastTimestamp = -1;

    public IntegrationMeter(@NonNull Meter<T> meter, int integrationCount,
                            @NonNull VectorUtils<T> utils, T zeroValue)
    {
        assert integrationCount > 0;
        if(integrationCount == 1){
            this.meter = meter;
        }
        else{
            this.meter = new IntegrationMeter<>(meter, integrationCount - 1, utils, zeroValue);
        }
        this.meter.setOnValueChangedListener(new OnMeterValueChangedListener());
        this.utils = utils;
        rawValue = biasValue = zeroValue;
    }

    @Override
    public void pause() {
        meter.pause();
    }

    @Override
    public void resume() {
        meter.resume();
    }

    @Override
    public void calibrate() {
        biasValue = utils.scale(rawValue, -1);
        meter.calibrate();
    }

    private class OnMeterValueChangedListener implements OnValueChangedListener<T>{

        @Override
        public void onValueChanged(T value, long timestamp) {
            if(lastTimestamp != -1){
                long lTime = timestamp - lastTimestamp;
                double dTime = lTime * NS2S;

                T change = utils.scale(value, dTime);
                rawValue = utils.add(rawValue, change);

                setValue(utils.add(rawValue, biasValue), timestamp);
            }
            lastTimestamp = timestamp;
        }
    }
}
