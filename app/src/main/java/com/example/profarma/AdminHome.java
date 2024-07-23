package com.example.profarma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.profarma.databinding.ActivityAdminHomeBinding;
import com.example.profarma.databinding.AddProductLayoutBinding;

import com.example.profarma.dbHandler.ProductModule;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {
    private ActivityAdminHomeBinding binding;
    private static final int REQUEST_IMAGE_CAPTURE = 1; // camera access
    private static final int REQUEST_IMAGE_GALLERY = 2; // gallery access
    private AlertDialog.Builder builder; // alert dialog box builder
    private AddProductLayoutBinding layoutProductBinding; // view binding for add product layout
    private ProductModule productModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productModule = new ProductModule(binding);
        productModule.dbLoadProducts();

        binding.layoutAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHome.this);
                layoutProductBinding = AddProductLayoutBinding.inflate(getLayoutInflater());
                builder.setView(layoutProductBinding.getRoot());

                ArrayList<String> subCategories = new ArrayList<String>() {{
                    add("Large");
                    add("Medium");
                    add("Small");
                }};

                layoutProductBinding.spCategory.setAdapter(new ArrayAdapter<String>(AdminHome.this, android.R.layout.simple_spinner_dropdown_item, Params.getMapProductCategory().keySet().toArray(new String[0])));
                layoutProductBinding.spSubCategory.setAdapter(new ArrayAdapter<String>(AdminHome.this, android.R.layout.simple_spinner_dropdown_item, subCategories));

                layoutProductBinding.imgProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                        showImageSelectionDialog();
                    }
                });

                layoutProductBinding.btnAddCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutProductBinding.layoutAddCategory.setVisibility(View.VISIBLE);
                        layoutProductBinding.btnAddCategory2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String categoryName = layoutProductBinding.edtCategory.getText().toString();
                                if (categoryName.isEmpty()) {
                                    layoutProductBinding.edtCategory.setError("Category Name Required!");
                                    return;
                                }
                                productModule.dbAddCategory(categoryName, layoutProductBinding.spCategory);
                                layoutProductBinding.edtCategory.setText("");
                                layoutProductBinding.layoutAddCategory.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                layoutProductBinding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productModule.addProduct(layoutProductBinding);
                    }
                });

                builder.show();
            }
        });

        binding.layoutSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                startActivity(new Intent(AdminHome.this, EmployeeHome.class));
            }
        });

        binding.layoutAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                Toast.makeText(AdminHome.this, "Admin Can Generate New Employee Details Here Ex. Id, Password and Name!", Toast.LENGTH_LONG).show();
            }
        });

        binding.layoutEmployeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                Toast.makeText(AdminHome.this, "Admin Can View And Manage All Employee Details Here!", Toast.LENGTH_LONG).show();
            }
        });

        binding.layoutProductDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                Toast.makeText(AdminHome.this, "Admin Can View And Manage All Product Details Here!", Toast.LENGTH_LONG).show();
            }
        });

        binding.layoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
                Toast.makeText(AdminHome.this, "Admin Can View And Manage Admin Details Here! Like Update Profile, Password, Names", Toast.LENGTH_LONG).show();
            }
        });
    }

//    private void addProduct(AddProductLayoutBinding layoutProductBinding) {
//        layoutProductBinding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.startAnimation(AnimationUtils.loadAnimation(AdminHome.this, R.anim.fade));
//                DbHandler dbHandler = new DbHandler(AdminHome.this);
//
//                Product product = new Product();
//                product.setProductName(layoutProductBinding.edtProductName.getText().toString());
//                product.setQuantity(layoutProductBinding.edtProductQuan.getText().toString());
//                product.setPrice(layoutProductBinding.edtProductPrice.getText().toString());
//                product.setCategory(layoutProductBinding.spCategory.getSelectedItem().toString());
//                product.setSubCategory(layoutProductBinding.spSubCategory.getSelectedItem().toString());
//
//                if(isDetailsFilled(product)) {
//                    reset(layoutProductBinding);
//                    dbHandler.addRecord(product);
//                    Toast.makeText(AdminHome.this, "Product Added Successfully!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    // select image from gallery or camera  using this method, when product is not available when scan
    private void showImageSelectionDialog() {
        builder = new AlertDialog.Builder(this); // initializing builder
        builder.setMessage(null); // setting message to null

        String[] options = {"Capture Photo", "Choose from Gallery"}; // options for dialog box

        // setting options in dialog box
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) { // switch case for options
                    case 0: // if user select camera
                        dispatchTakePictureIntent(); // open camera
                        break;
                    case 1: // if user select gallery
                        dispatchPickImageIntent(); // open gallery
                        break;
                }
            }
        });

        builder.create().show(); // show dialog box
    }

    // opening camera for taking picture
    private void dispatchTakePictureIntent() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // intent for camera
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); // start camera
        } catch (Exception e) {
            // if any error rise
            Toast.makeText(binding.getRoot().getContext(), "Can not Open Camera", Toast.LENGTH_SHORT).show();
            Log.d("ErrorMsg", "dispatchTakePictureIntent: " + e.getMessage());
        }
    }

    // opening local files to find the image
    private void dispatchPickImageIntent() {
        try {
            Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // intent for gallery
            pickImageIntent.setType("image/*"); // setting type of image
            startActivityForResult(pickImageIntent, REQUEST_IMAGE_GALLERY); // start gallery
        } catch (Exception e) {
            // if any error rise
            Toast.makeText(binding.getRoot().getContext(), "Can not Open Gallery", Toast.LENGTH_SHORT).show();
            Log.d("ErrorMsg", "dispatchPickImageIntent: " + e.getMessage());
        }
    }

    // image is taken from camera or from file, now set in imageview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // calling super method

        // if image is taken from camera
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // image from camera
            Bundle extras = data.getExtras(); // getting extras
            Bitmap imageBitmap = (Bitmap) extras.get("data"); // getting image from extras
            layoutProductBinding.imgProduct.setImageBitmap(imageBitmap); // setting image in image view
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) { // if image is taken from gallery
            // image from gallery is selected
            Uri selectedImageUri = data.getData(); // getting image uri
            layoutProductBinding.imgProduct.setImageURI(selectedImageUri); // setting image in image view
        }
    }
}