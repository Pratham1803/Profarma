package com.example.profarma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profarma.Params;
import com.example.profarma.R;
import com.example.profarma.databinding.LayoutProductBinding;
import com.example.profarma.model.ProductModel;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {
    private ArrayList<ProductModel> localDataSet = new ArrayList<>();
    private static Context context;

    public static class ProductListViewHolder extends RecyclerView.ViewHolder {
        private final LayoutProductBinding bindingLayout;

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingLayout = LayoutProductBinding.bind(itemView);
        }

        public void bind(int position, ProductModel product) {
            bindingLayout.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assert product != null;
                    Params.getDbReference().child("cart").child(product.getProductId()).setValue(true)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(context, "Product Added to Cart", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                            });
                }
            });

        }

        public LayoutProductBinding getBindingLayout() {
            return bindingLayout;
        }
    }

    public ProductListAdapter(Context context) {
        this.context = context;
    }

    public void setLocalDataSet(ArrayList<ProductModel> localDataSet, String category) {
        if (localDataSet == null) {
            localDataSet = new ArrayList<>();
            for (ProductModel product : Params.getMapProduct().values()) {
                if (product.getCategory().equals(category))
                    localDataSet.add(product);
            }
        }
        this.localDataSet = localDataSet;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product, parent, false);

        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        ProductModel product = localDataSet.get(position);
        holder.getBindingLayout().txtProductName.setText(product.getProductName());
        holder.getBindingLayout().txtProductPrice.setText("Price: " + product.getPrice());
        holder.bind(position, product);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
