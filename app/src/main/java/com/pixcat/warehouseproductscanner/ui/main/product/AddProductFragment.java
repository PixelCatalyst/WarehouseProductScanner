package com.pixcat.warehouseproductscanner.ui.main.product;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixcat.warehouseproductscanner.R;
import com.pixcat.warehouseproductscanner.data.model.ActiveUser;
import com.pixcat.warehouseproductscanner.data.model.ProductDto;
import com.pixcat.warehouseproductscanner.ui.camera.CameraWrapper;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Objects;

public class AddProductFragment extends Fragment {

    private final ActiveUser activeUser;

    private final ProductDto.Builder productBuilder = new ProductDto.Builder();
    private ProductViewModel productViewModel;

    private EditText productIdEdit;
    private EditText descriptionEdit;
    private EditText heightEdit;
    private EditText widthEdit;
    private EditText lengthEdit;
    private EditText weightEdit;
    private RadioGroup tempRadioGroup;
    private BarcodesAdapter barcodesAdapter;
    private ImageView imageView;

    private SensorManager sensorManager;

    private ImageCapture imageCapture;
    private Camera camera;
    private byte[] imageBuffer;

    public AddProductFragment(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        productIdEdit = view.findViewById(R.id.edit_productid);
        descriptionEdit = view.findViewById(R.id.edit_description);
        heightEdit = view.findViewById(R.id.edit_height);
        widthEdit = view.findViewById(R.id.edit_width);
        lengthEdit = view.findViewById(R.id.edit_length);
        weightEdit = view.findViewById(R.id.edit_weight);
        tempRadioGroup = view.findViewById(R.id.radio_temp);
        imageView = view.findViewById(R.id.product_image_add);

        detectAmbientTemperature();

        attachCameraToView(view);

        Button createProductButton = view.findViewById(R.id.button_create_product);
        createProductButton.setOnClickListener(v -> dispatchCreation());
        createProductButton.setEnabled(false);

        RecyclerView barcodesRecycler = view.findViewById(R.id.barcodes_recycler);
        barcodesAdapter = new BarcodesAdapter(new ArrayList<>(), true);
        barcodesRecycler.setAdapter(barcodesAdapter);
        barcodesRecycler.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        barcodesRecycler.setNestedScrollingEnabled(false);

        ImageButton addBarcodeButton = view.findViewById(R.id.button_add_barcode);
        addBarcodeButton.setOnClickListener(v -> barcodesAdapter.addBarcode());

        productViewModel = new ViewModelProvider(this, new ProductViewModelFactory(activeUser))
                .get(ProductViewModel.class);

        productViewModel.getProductFormState().observe(getViewLifecycleOwner(), productFormState -> {
            if (productFormState == null) {
                return;
            }
            createProductButton.setEnabled(productFormState.isDataValid());
            if (productFormState.getProductIdError() != null) {
                productIdEdit.setError(getString(productFormState.getProductIdError()));
            }
            if (productFormState.getHeightError() != null) {
                heightEdit.setError(getString(productFormState.getHeightError()));
            }
            if (productFormState.getWidthError() != null) {
                widthEdit.setError(getString(productFormState.getWidthError()));
            }
            if (productFormState.getLengthError() != null) {
                lengthEdit.setError(getString(productFormState.getLengthError()));
            }
            if (productFormState.getWeightError() != null) {
                weightEdit.setError(getString(productFormState.getWeightError()));
            }
        });
        productViewModel.getCreateProductResult().observe(getViewLifecycleOwner(), createProductResult -> {
            if (createProductResult == null) {
                return;
            }
            if (createProductResult.getError() != null) {
                Toast.makeText(
                        requireActivity().getApplicationContext(),
                        createProductResult.getError(),
                        Toast.LENGTH_LONG)
                        .show();
            }
            if (createProductResult.getSuccess() != null) {
                Toast.makeText(
                        requireActivity().getApplicationContext(),
                        getString(R.string.product_created) + ". " +
                                (createProductResult.isImageSuccess()
                                        ? getString(R.string.image_upload_success)
                                        : getString(R.string.image_upload_fail)),
                        Toast.LENGTH_LONG)
                        .show();

                productViewModel.consumeCreateProductResult();
                imageBuffer = null;
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ViewProductFragment(createProductResult.getSuccess()))
                        .addToBackStack(null)
                        .commit();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                notifyFormUpdate();
            }
        };
        productIdEdit.addTextChangedListener(afterTextChangedListener);
        descriptionEdit.addTextChangedListener(afterTextChangedListener);
        heightEdit.addTextChangedListener(afterTextChangedListener);
        widthEdit.addTextChangedListener(afterTextChangedListener);
        lengthEdit.addTextChangedListener(afterTextChangedListener);
        weightEdit.addTextChangedListener(afterTextChangedListener);
        tempRadioGroup.setOnCheckedChangeListener((group, checkedId) -> notifyFormUpdate());

        return view;
    }

    private void detectAmbientTemperature() {
        SensorEventListener temperatureListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float ambientCelsius = event.values[0];
                tempRadioGroup.check(ambientCelsius < 10.0f
                        ? R.id.radio_temp_chilled
                        : R.id.radio_temp_ambient);

                sensorManager.unregisterListener(this);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // ignore
            }
        };
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(temperatureListener, temperature, SensorManager.SENSOR_DELAY_UI);

        if (tempRadioGroup.getCheckedRadioButtonId() == -1) {
            tempRadioGroup.check(R.id.radio_temp_ambient);
        }
    }

    private void attachCameraToView(View view) {
        ProcessCameraProvider cameraProvider = CameraWrapper.getInstance().getCameraProvider();

        if (cameraProvider == null) {
            imageView.setOnClickListener(v -> Toast
                    .makeText(requireContext(), getString(R.string.camera_unavailable_message), Toast.LENGTH_LONG)
                    .show());
        } else {
            imageCapture = new ImageCapture.Builder()
                    .setTargetRotation(Surface.ROTATION_270)
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build();

            Preview preview = new Preview.Builder()
                    .build();
            PreviewView previewView = view.findViewById(R.id.camera_preview);
            preview.setSurfaceProvider(previewView.getSurfaceProvider());
            RelativeLayout previewWrapper = view.findViewById(R.id.camera_preview_wrapper);
            Button previewButton = view.findViewById(R.id.camera_preview_button);
            NestedScrollView productScrollView = view.findViewById(R.id.product_scroll_view);

            cameraProvider.unbindAll();
            camera = cameraProvider.bindToLifecycle(
                    getViewLifecycleOwner(),
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture);
            imageView.setOnClickListener(v -> {
                productScrollView.setVisibility(View.GONE);
                previewWrapper.setVisibility(View.VISIBLE);
            });

            previewButton.setOnClickListener(v -> imageCapture.takePicture(
                    ContextCompat.getMainExecutor(requireActivity()),
                    new ImageCapture.OnImageCapturedCallback() {

                        @Override
                        @ExperimentalGetImage
                        public void onCaptureSuccess(@NonNull ImageProxy image) {
                            super.onCaptureSuccess(image);

                            previewWrapper.setVisibility(View.GONE);
                            productScrollView.setVisibility(View.VISIBLE);

                            Image.Plane[] planes = Objects.requireNonNull(image.getImage()).getPlanes();
                            if (planes.length > 0) {
                                ByteBuffer buffer = planes[0].getBuffer();
                                buffer.rewind();
                                byte[] bytes = new byte[buffer.capacity()];
                                buffer.get(bytes);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                Matrix rotationMatrix = new Matrix();
                                rotationMatrix.postRotate(90.0f);
                                Bitmap rotated = Bitmap.createBitmap(
                                        bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotationMatrix, true);

                                ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
                                rotated.compress(Bitmap.CompressFormat.PNG, 100, compressedStream);
                                imageBuffer = compressedStream.toByteArray();

                                imageView.setImageBitmap(rotated);
                            }
                            image.close();
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            super.onError(exception);
                        }
                    }
            ));
        }
    }

    private void dispatchCreation() {
        ProductDto product = assembleProductData();

        productViewModel.createProduct(product, imageBuffer);
    }

    private void notifyFormUpdate() {
        ProductDto product = assembleProductData();
        productViewModel.productFormChanged(product);
    }

    private ProductDto assembleProductData() {
        return productBuilder
                .productId(productIdEdit.getText().toString())
                .description(descriptionEdit.getText().toString())
                .heightInMillimeters(mapMillimeters(heightEdit.getText().toString()))
                .widthInMillimeters(mapMillimeters(widthEdit.getText().toString()))
                .lengthInMillimeters(mapMillimeters(lengthEdit.getText().toString()))
                .weightInKilograms(mapKilograms(weightEdit.getText().toString()))
                .storageTemperature(mapCheckedRadioToTemperature(tempRadioGroup.getCheckedRadioButtonId()))
                .barcodes(barcodesAdapter.getBarcodes())
                .build();
    }

    private Integer mapMillimeters(String value) {
        if (value != null && !value.trim().isEmpty()) {
            return Integer.parseInt(value);
        }
        return null;
    }

    private BigDecimal mapKilograms(String value) {
        if (value != null && !value.trim().isEmpty()) {
            return new BigDecimal(value);
        }
        return null;
    }

    private String mapCheckedRadioToTemperature(int id) {
        if (id == R.id.radio_temp_ambient) {
            return "AMBIENT";
        } else {
            return "CHILL";
        }
    }
}
