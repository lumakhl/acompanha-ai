package com.ai.acompanha.acompanhaai.ui.main.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.ai.acompanha.acompanhaai.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;

    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            if(bitmap != null)
                Log.d("Info", "Imagem convertida para bitmap com sucesso.");
            else
                Log.d("Info", "Ocorreu um erro ao tentar converter a imagem para bitmap.");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCamera = getCameraInstance();

        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        FloatingActionButton captureButton = findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCamera.takePicture(null, null, mPicture);
                }
            }
        );

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private static Camera getCameraInstance(){
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e){ }

        return camera;
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }

}

