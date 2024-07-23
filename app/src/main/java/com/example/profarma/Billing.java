package com.example.profarma;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.profarma.adapter.Bill_product_adapter;
import com.example.profarma.databinding.ActivityBillingBinding;
import com.example.profarma.model.OrderCart;
import com.example.profarma.model.OrderProduct;
import com.example.profarma.model.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Billing extends AppCompatActivity {
    private ActivityBillingBinding binding;
    private OrderCart orderCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() != null) {
            orderCart = (OrderCart) getIntent().getSerializableExtra("orderCart");
        }

        setSupportActionBar(binding.toolbarBill);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(orderCart.getCompanyName() + "'s Invoice");

        binding.txtCompanyAddress.setText(orderCart.getCompanyAddress());
        binding.txtPersonName.setText(orderCart.getCompanyName());
        binding.txtBillNoShow.setText(orderCart.getOrderId());

        Date date = new Date(Long.parseLong(orderCart.getOrderId()));
        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(date);
        binding.txtTodayDate.setText(formattedDate);

        Bill_product_adapter adapter = new Bill_product_adapter(this,orderCart.getLsProduct());
        binding.lsProductView.setAdapter(adapter);
        binding.lsProductView.setLayoutManager(new LinearLayoutManager(this));

        binding.btnProceedBill.setOnClickListener(v -> {
            binding.btnProceedBill.setVisibility(View.GONE);
            PdfUtils.generatePdfFromView(binding.scrollView, orderCart.getCompanyName() +"'s Invoice");
        });

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Bill Generated")
                        .setSmallIcon(R.drawable.ic_launcher_foreground).build();


        binding.txtTotalItems.setText("Total Items: " + adapter.getItemCount());
        float total=0, quantity=0;
        for(OrderProduct product: orderCart.getLsProduct()){
            float quan = Float.parseFloat(product.getProductQty());
            quantity += quan;
            total += quantity * Float.parseFloat(product.getProductPrice());
        }
        binding.txtTotalItems.setText(String.valueOf(orderCart.getLsProduct().size()));
        binding.txtTotalAmount.setText(String.valueOf(total));
        binding.txtTotalQuantity.setText(String.valueOf(quantity));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        orderCart.getLsProduct().clear();
    }
}