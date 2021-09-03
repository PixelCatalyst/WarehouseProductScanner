package com.pixcat.warehouseproductscanner.ui.camera;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraWrapper {

    private static CameraWrapper instance;
    private static final int CAMERA_PERMISSION_REQUEST = 2111387;

    private boolean permissionsGrantDenied = false;
    private ProcessCameraProvider cameraProvider;

    private CameraWrapper() {

    }

    public static CameraWrapper getInstance() {
        if (instance == null) {
            instance = new CameraWrapper();
        }
        return instance;
    }

    public void initCamera(Activity activity) {
        String[] cameraPermissions = new String[]{
                Manifest.permission.CAMERA,
        };
        if (checkIfPermissionsGranted(activity, cameraPermissions)) {
            provideCamera(activity);
        } else {
            activity.requestPermissions(cameraPermissions, CAMERA_PERMISSION_REQUEST);
        }
    }

    private boolean checkIfPermissionsGranted(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(Activity activity, int requestCode, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initCamera(activity);
            } else {
                permissionsGrantDenied = true;
            }
        }
    }

    private void provideCamera(Activity activity) {

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

        cameraProviderFuture = ProcessCameraProvider.getInstance(activity.getApplicationContext());

        cameraProviderFuture.addListener(() -> {
                    try {
                        cameraProvider = cameraProviderFuture.get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                }, ContextCompat.getMainExecutor(activity.getApplicationContext())
        );
    }

    public ProcessCameraProvider getCameraProvider() {
        return cameraProvider;
    }
}
