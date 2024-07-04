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
import com.example.profarma.model.Product;

public class Billing extends AppCompatActivity {
    private ActivityBillingBinding binding;
    private String name;
    private String phone;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() != null) {
            name = getIntent().getExtras().getString("name");
            phone = getIntent().getExtras().getString("phone");
            address = getIntent().getExtras().getString("address");
        }

        setSupportActionBar(binding.toolbarBill);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name + "'s Invoice");

        binding.txtCompanyAddress.setText(address);
        binding.txtPersonName.setText(name);

        Bill_product_adapter adapter = new Bill_product_adapter(this);
        binding.lsProductView.setAdapter(adapter);
        binding.lsProductView.setLayoutManager(new LinearLayoutManager(this));

        binding.btnProceedBill.setOnClickListener(v -> {
            binding.btnProceedBill.setVisibility(View.GONE);
            PdfUtils.generatePdfFromView(binding.scrollView, name+"'s Invoice");
        });

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Bill Generated")
                        .setSmallIcon(R.drawable.ic_launcher_foreground).build();


        binding.txtTotalItems.setText("Total Items: " + adapter.getItemCount());
        int total=0, quantity=0;
        for(Product product: OrderCart.getLsProduct()){
            int quan = Integer.parseInt(product.getQuantity());
            quantity += quan;
            total += quantity * Integer.parseInt(product.getPrice());
        }
        binding.txtTotalItems.setText(String.valueOf(OrderCart.getLsProduct().size()));
        binding.txtTotalAmount.setText(String.valueOf(total));
        binding.txtTotalQuantity.setText(String.valueOf(quantity));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OrderCart.getLsProduct().clear();
    }
}