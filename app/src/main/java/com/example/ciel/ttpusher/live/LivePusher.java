package com.example.ciel.ttpusher.live;

import android.app.Activity;
import android.view.SurfaceHolder;

import com.example.ciel.ttpusher.live.channel.AudioChannel;
import com.example.ciel.ttpusher.live.channel.VideoChannel;


public class LivePusher {


    static {
        System.loadLibrary("native-lib");
    }

    private AudioChannel audioChannel;
    private VideoChannel videoChannel;

    public LivePusher(Activity activity, int width, int height, int bitrate,
                      int fps, int cameraId) {
        native_init();
        videoChannel = new VideoChannel(this, activity, width, height, bitrate, fps, cameraId);
        audioChannel = new AudioChannel(this);
    }

    public void setPreviewDisplay(SurfaceHolder surfaceHolder) {
        videoChannel.setPreviewDisplay(surfaceHolder);
    }

    public void switchCamera() {
        videoChannel.switchCamera();
    }

    public void startLive(String path) {
        native_start(path);
        videoChannel.startLive();
        audioChannel.startLive();
    }

    public void stopLive() {
        videoChannel.stopLive();
        audioChannel.stopLive();
        native_stop();
    }


    public void release() {
        videoChannel.release();
        audioChannel.release();
        native_release();
    }

    public native void native_init();

    public native void native_start(String path);

    public native void native_setVideoEncInfo(int width, int height, int fps, int bitrate);

    public native void native_setAudioEncInfo(int sampleRateInHz, int channels);

    public native void native_pushVideo(byte[] data);

    public native void native_stop();

    public native void native_release();

    public native int getInputSamples();

    public native void native_pushAudio(byte[] data);

    public native int native_test();
}
