package com.example.devicetracker;

public abstract class Meter<T> {
    private OnValueChangedListener<T> onValueChangedListener;
    protected T value;

    abstract public void pause();
    abstract public void resume();
    abstract public void calibrate();

    public final void setOnValueChangedListener(OnValueChangedListener<T> listener) {
        onValueChangedListener = listener;
    }

    protected final void setValue(T value, long timestamp){
        this.value = value;
        if(onValueChangedListener != null)
            onValueChangedListener.onValueChanged(value, timestamp);
    }

    public final T getValue(){
        return value;
    }

    interface OnValueChangedListener<T>{
        void onValueChanged(T value, long timestamp);
    }
}
