package com.example.shoprime;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PERMISSION_REQUEST_CODE = 100;

    private TextInputEditText etName, etPrice, etDesc;
    private ImageView ivProduct;
    private Button btnSave, btnSelectImage;
    private TextView tvTitle;
    private Product existingProduct;
    private Uri currentPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        tvTitle = findViewById(R.id.tvAddTitle);
        etName = findViewById(R.id.etProdName);
        etPrice = findViewById(R.id.etProdPrice);
        etDesc = findViewById(R.id.etProdDesc);
        ivProduct = findViewById(R.id.ivProductImage);
        btnSave = findViewById(R.id.btnSaveProduct);
        btnSelectImage = findViewById(R.id.btnSelectImage);

        existingProduct = (Product) getIntent().getSerializableExtra("product");

        if (existingProduct != null) {
            tvTitle.setText("Editar Producto");
            etName.setText(existingProduct.getName());
            etPrice.setText(String.valueOf(existingProduct.getPrice()));
            etDesc.setText(existingProduct.getDescription());
            if (existingProduct.getImageUri() != null) {
                ivProduct.setImageURI(Uri.parse(existingProduct.getImageUri()));
            }
        }

        btnSelectImage.setOnClickListener(v -> showImageOptions());

        btnSave.setOnClickListener(v -> saveProduct());
    }

    private void showImageOptions() {
        String[] options = {"Galería", "Cámara"};
        new AlertDialog.Builder(this)
                .setTitle("Seleccionar Imagen")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) openGallery();
                    else checkCameraPermission();
                }).show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error al crear archivo", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                currentPhotoUri = FileProvider.getUriForFile(this, "com.example.shoprime.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void saveProduct() {
        String name = etName.getText().toString();
        String priceStr = etPrice.getText().toString();
        String desc = etDesc.getText().toString();

        if (name.isEmpty() || priceStr.isEmpty()) return;

        double price = Double.parseDouble(priceStr);
        Product product = existingProduct != null ? existingProduct : new Product(String.valueOf(System.currentTimeMillis()), name, price, desc);
        
        product.setName(name);
        product.setPrice(price);
        product.setDescription(desc);
        if (currentPhotoUri != null) {
            product.setImageUri(currentPhotoUri.toString());
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("product", product);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null) {
                currentPhotoUri = data.getData();
                ivProduct.setImageURI(currentPhotoUri);
            } else if (requestCode == CAMERA_REQUEST) {
                ivProduct.setImageURI(currentPhotoUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        }
    }
}