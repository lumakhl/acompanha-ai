package com.ai.acompanha.acompanhaai.ui.main.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.ai.acompanha.acompanhaai.R;

public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCamera = getCameraInstance();

        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    private static Camera getCameraInstance(){
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e){ }

        return camera;
    }

}

