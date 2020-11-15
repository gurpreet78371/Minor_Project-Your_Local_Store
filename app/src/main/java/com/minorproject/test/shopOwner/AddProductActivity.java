package com.minorproject.test.shopOwner;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.minorproject.test.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = "AddProductActivity";
    private final String user_id = "";
    private final Uri imageUri_2 = null;
    private final Uri imageUri_3 = null;
    private final Uri imageUri_4 = null;
    private final Uri imageUri_5 = null;
    // views
    private RadioButton radioBtnOutOfStock, radioBtnReadyStock, radioBtnSuspend;
    private TextInputLayout productName, productDescription, productPrice, productStock, productDiscount;
    private ImageView back;
    private Button cancel, confirm, image1, image2, image3, image4, image5, saveProduct;
    private Spinner unitSpinner;
    private TextView unit;
    // firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    // others
    private Bitmap compressed;
    private ProgressDialog progressDialog;
    // variables
    private String status, ProductNameTxt, PriceTxt, StockTxt, PriceDiscountTxt, DescriptionTxt;
    private Uri imageUri_1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Radio Group
        radioBtnReadyStock = findViewById(R.id.radioBtnReadyStock);
        radioBtnOutOfStock = findViewById(R.id.radioBtnOutOfStock);
        radioBtnSuspend = findViewById(R.id.radioBtnSuspend);

        // item details
        productName = findViewById(R.id.txtProductName);
        productDescription = findViewById(R.id.txtDescription);
        productPrice = findViewById(R.id.item_price);
        productStock = findViewById(R.id.item_stock);
        productDiscount = findViewById(R.id.item_discount);
        unitSpinner = findViewById(R.id.item_type);
        unit = findViewById(R.id.item_unit);
        image1 = findViewById(R.id.btn_image1);
        image2 = findViewById(R.id.btn_image2);
        image3 = findViewById(R.id.btn_image3);
        image4 = findViewById(R.id.btn_image4);
        image5 = findViewById(R.id.btn_image5);

        // navigation
        back = findViewById(R.id.back);
        cancel = findViewById(R.id.item_cancel);
        confirm = findViewById(R.id.item_save);

        // Firebase
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
//        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        // Create an ArrayAdapter using the string array and a default stockSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unit_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the stockSpinner
        unitSpinner.setAdapter(adapter);
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String unitSelected = parent.getItemAtPosition(position).toString();
                unit.setText(unitSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder confirmProductResponse = new AlertDialog.Builder(v.getContext());
                confirmProductResponse.setTitle("Adding product...");
                confirmProductResponse.setMessage("Do you want to add this product?");

                confirmProductResponse.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO add product to database
                    }
                });
                confirmProductResponse.setNegativeButton("No", null);
                confirmProductResponse.create().show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isCompleteData() {
        final String name = productName.getEditText().getText().toString();
        final String description = productDescription.getEditText().getText().toString();
        final String price = productPrice.getEditText().getText().toString();
        final String stock = productStock.getEditText().getText().toString();

        if (name.isEmpty() && description.isEmpty() && price.isEmpty() && stock.isEmpty() && imageUri_1 != null) {
            Toast.makeText(AddProductActivity.this, "Please enter product details", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.isEmpty()) {
            productName.setError("Please enter your name");
            productName.requestFocus();
            return false;
        } else if (description.isEmpty()) {
            productDescription.setError("Please enter your email.");
            productDescription.requestFocus();
            return false;
        } else if (price.isEmpty()) {
            productPrice.setError("Please enter your password.");
            productPrice.requestFocus();
            return false;
        } else if (stock.isEmpty()) {
            productStock.setError("Please enter your password.");
            productStock.requestFocus();
            return false;
        }
        return true;
    }

    private void choseImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(AddProductActivity.this);
    }

    private void saveData() {
        progressDialog.setMessage("Storing Data...");
        progressDialog.show();
        final String name = productName.getEditText().getText().toString();
        final String description = productDescription.getEditText().getText().toString();
        final String price = productPrice.getEditText().getText().toString();
        final String stock = productStock.getEditText().getText().toString();

        File newFile = new File(imageUri_1.getPath());
        try {
            compressed = new Compressor(AddProductActivity.this)
                    .setMaxHeight(125)
                    .setMaxWidth(125)
                    .setQuality(50)
                    .compressToBitmap(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] thumbData = byteArrayOutputStream.toByteArray();
        UploadTask image_path = storageReference.child("user_image").child(user_id + ".jpg").putBytes(thumbData);
        image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storeData(task,
                            name,
                            description,
                            price,
                            stock);
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(AddProductActivity.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void storeData(Task<UploadTask.TaskSnapshot> task, String name, String description, String price, String stock) {
        Uri download_uri;
        if (task != null) {
            download_uri = task.getResult().getUploadSessionUri();
        } else {
            download_uri = imageUri_1;
        }
        Map<String, String> userData = new HashMap<>();
        userData.put("itemName", name);
        userData.put("itemDescription", description);
        userData.put("itemPrice", price);
        userData.put("itemStock", stock);
        userData.put("itemImage1", download_uri.toString());
        firebaseFirestore.collection("Users").document(user_id).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(AddProductActivity.this, "Product Data is Stored Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddProductActivity.this, VendorProfileActivity.class));
                    finish();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(AddProductActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri_1 = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void selectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AddProductActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddProductActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(AddProductActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                choseImage();
            }
        } else {
            choseImage();
        }
    }

//    public void cancelButtonClicked(View view) {
//        //TODO: Change to right activity
//
//    }

//    public void saveButtonClicked(View view) {
//        ProductNameTxt = Objects.requireNonNull(productName.getEditText()).getText().toString();
//        PriceTxt = Objects.requireNonNull(productPrice.getEditText()).getText().toString();
//        StockTxt = Objects.requireNonNull(productStock.getEditText()).getText().toString();
//        PriceDiscountTxt = Objects.requireNonNull(productDiscount.getEditText()).getText().toString();
//        DescriptionTxt = Objects.requireNonNull(productDescription.getEditText()).getText().toString();
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("Name", ProductNameTxt);
//        data.put("Price", PriceTxt);
//        data.put("Stock", StockTxt);
//        data.put("Discount", PriceDiscountTxt);
//        data.put("Description", DescriptionTxt);
//        data.put("Status", status);
//
//        db.collection("products")
//                .add(data)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });
//    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioBtnReadyStock:
                if (checked)
                    status = "ReadyStock";
                break;
            case R.id.radioBtnOutOfStock:
                if (checked)
                    status = "OutOfStock";
                break;
            case R.id.radioBtnSuspend:
                if (checked)
                    status = "Suspend";
                break;
            default:
                status = "ReadyStock";
                break;
        }
    }
}