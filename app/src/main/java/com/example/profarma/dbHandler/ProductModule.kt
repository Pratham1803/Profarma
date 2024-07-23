package com.example.profarma.dbHandler

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.profarma.Params
import com.example.profarma.R
import com.example.profarma.databinding.ActivityAdminHomeBinding
import com.example.profarma.databinding.AddProductLayoutBinding
import com.example.profarma.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayOutputStream

// ProductModule class to handle product operations like add product, get all products, add category
class ProductModule(private val binding: ActivityAdminHomeBinding) {
    private val context = binding.root.context // Get the context of the activity
    private lateinit var bindingView: AddProductLayoutBinding // Binding view for add product layout
    private lateinit var product: Product // Product object
    private lateinit var sweetAlertDialog: SweetAlertDialog

    // Add product to database and show dialog
    fun addProduct(layoutProductBinding: AddProductLayoutBinding) {
        bindingView = layoutProductBinding // Set the layout product binding to the binding view
        product = Product() // Initialize the product object
        sweetAlertDialog = SweetAlertDialog(
            context,
            SweetAlertDialog.PROGRESS_TYPE
        ) // Initialize the sweet alert dialog
        sweetAlertDialog.setTitleText("Loading...") // Set the title of the dialog
        sweetAlertDialog.setCancelable(false) // Set the dialog to not cancelable
        sweetAlertDialog.show() // Show the dialog

        product.productName =
            bindingView.edtProductName.text.toString()  // Get the product name from the edit text
        product.price =
            bindingView.edtProductPrice.text.toString() // Get the retail price from the edit text
        product.quantity =
            bindingView.edtProductQuan.text.toString() // Get the quantity from the edit text
        product.category =
            bindingView.spCategory.selectedItem.toString() // Get the category from the spinner
        product.subCategory =
            bindingView.spSubCategory.selectedItem.toString() // Get the sub category from the edit text

        // Add product to database if all fields are filled and show dialog
        if (verifyDetails()) { // Verify if all fields are filled
            dbUploadProductDetails() // Save product to database
        }

    }

    // Verify if all fields are filled
    private fun verifyDetails(): Boolean {
        var isFilled = true // Set verification to true
        // Check if all fields are filled
        if (bindingView.edtProductName.text.toString().isEmpty()) { // If product name is empty
            bindingView.edtProductName.error = "Enter Product Name" // Show error message
            isFilled = false // Set verification to false
        }
        if (bindingView.edtProductPrice.text.toString().isEmpty()) { // If retail price is empty
            bindingView.edtProductPrice.error = "Enter Retail Price" // Show error message
            isFilled = false // Set verification to false
        }
        if (bindingView.edtProductQuan.text.toString().isEmpty()) { // If quantity is empty
            bindingView.edtProductQuan.error = "Enter Quantity" // Show error message
            isFilled = false // Set verification to false
        }

        return isFilled // Return the verification status
    }

    // upload product data in database
    private fun dbUploadProductDetails() {
        val ref =
            Params.getDbReference().child("products").child(product.category).push()
        product.productId = ref.key.toString() // Set the product ID
        // Upload bitmap to Firebase Storage
        val image: String = product.productId + ".jpg" // setting the name of image
        val baos = ByteArrayOutputStream() // initializing byte array output stream

        val bitmap =
            (bindingView.imgProduct.getDrawable() as BitmapDrawable).bitmap // getting bitmap from the image view
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            baos
        ) // compressing bitmap data into image file of jpeg
        val data = baos.toByteArray() // storing byte data in list

        // Upload image to Firebase Storage and get the download URL
        Params.getFirebaseStorage().child("product").child(image).putBytes(data)
            .addOnSuccessListener { // On successful upload
                // Get the download URL
                Params.getFirebaseStorage().child("product")
                    .child(image).downloadUrl.addOnSuccessListener { // On successful download URL
                        product.img = it.toString() // Set the image URL
                        // Save product details to Firebase database
                        ref.setValue(product)
                            .addOnSuccessListener { // On successful save
                                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE) // Change the dialog type to success
                                resetAddProduct() // Reset the add product fields
                            }
                    }
            }
    }

    // Reset the add product fields
    private fun resetAddProduct() {
        bindingView.edtProductName.setText("") // Reset the serial number
        bindingView.edtProductPrice.setText("") // Reset the product name
        bindingView.edtProductQuan.setText("") // Reset the retail price
        bindingView.imgProduct.setImageResource(R.drawable.gallery_add) // Reset the image
    }

    // Function to load the products from the database
    fun dbLoadProducts() {
        loadCart()
        Params.getDbReference().child("products")
            .addValueEventListener(object : // Add a value event listener to the database reference
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) { // If the data is changed
                    if (snapshot.exists()) { // Check if the snapshot exists
                        Params.getMapProduct().clear() // Clear the map of products
                        Params.getMapProductCategory()
                            .clear() // Clear the map of product categories

                        for (category in snapshot.children) { // Loop through the categories
                            val productList = ArrayList<String>() // Create a list of products
                            for (product in category.children) { // Loop through the products
                                val productData =
                                    product.getValue(Product::class.java) // Get the product data
                                Params.getMapProduct()[productData?.productId.toString()] =
                                    productData as Product // Add the product to the map of products

                                productList.add(productData.productId) // Add the product id to the list of products
                            }
                            Params.getMapProductCategory()[category.key.toString()] =
                                productList // Add the list of products to the map of product categories
                            Log.d(
                                "ProductLog",
                                "onDataChange: " + Params.getMapProduct()
                            ) // Log the map of products
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) { // Error handling
                    Log.d(
                        "ProductLog",
                        "dbGetAllProduct: " + error.message
                    ) // Log the error message
                }
            })
    }

    // Add category to database
    fun dbAddCategory(categoryName: String, spCategory: Spinner) {
        Params.getDbReference().child("products").child(categoryName)
            .setValue("") // Add category to database
            .addOnSuccessListener { // On successful addition
                Params.getMapProductCategory()[categoryName] =
                    ArrayList() // Add category to map of product categories
                spCategory.setAdapter(
                    ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_spinner_dropdown_item,
                        Params.getMapProductCategory().keys.toTypedArray<String>()
                    )
                )
                spCategory.setSelection(Params.getMapProductCategory().keys.indexOf(categoryName))
                Toast.makeText(context, "$categoryName added successfully", Toast.LENGTH_SHORT)
                    .show() // Show success message
            }.addOnFailureListener { // On failure to add category
                Toast.makeText(context, "Failed to add category", Toast.LENGTH_SHORT)
                    .show() // Show error message
                Log.d("ProductLog", "dbAddCategory: " + it.message) // Log the error message
            }
    }

    fun loadCart() {
        Params.getDbReference().child("cart").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Params.getArrCart().clear()
                for (productSnapshot in snapshot.children) {
                    Params.getArrCart().add(productSnapshot.key!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ErrorMsg", "onCancelled: " + error.message)
            }
        })
    }
}