package com.example.profarma.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.profarma.Params;
import com.example.profarma.R;
import com.example.profarma.databinding.ActivityProductListBinding;
import com.example.profarma.databinding.LayoutProductBinding;
import com.example.profarma.model.OrderCart;
import com.example.profarma.model.Product;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private ArrayList<String> localDataSet;
    private Context context;
    private ActivityProductListBinding parent_binding;

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final LayoutProductBinding bindingLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingLayout = LayoutProductBinding.bind(itemView);
            bindingLayout.imgBtnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Product product = Params.getMapProduct().get(localDataSet.get(position));
                    int quantity = Integer.parseInt(bindingLayout.txtQuantity.getText().toString());
                    if (quantity > 0) {
                        quantity--;
                        product.setQuantity(String.valueOf(quantity));
                        bindingLayout.txtQuantity.setText(product.getQuantity());
                    }
                }
            });

            bindingLayout.imgBtnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Product product = Params.getMapProduct().get(localDataSet.get(position));
                    int quantity = Integer.parseInt(bindingLayout.txtQuantity.getText().toString());
                    quantity++;
                    product.setQuantity(String.valueOf(quantity));
                    bindingLayout.txtQuantity.setText(product.getQuantity());
                }
            });

            bindingLayout.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView txtQuantity = parent_binding.txtCartCount;
                    int quantity = Integer.parseInt(bindingLayout.txtQuantity.getText().toString());

                    if(quantity == 0 ){
                        Toast.makeText(context, "Please Add The Quantity!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int cartCount = Integer.parseInt(txtQuantity.getText().toString());
                    cartCount++;
                    txtQuantity.setText(String.valueOf(cartCount));
                    int position = getAdapterPosition();

                    OrderCart.getLsProduct().add(0, Params.getMapProduct().get(localDataSet.get(position)));
                    OrderCart.getLsProduct().get(0).setQuantity(String.valueOf(quantity));
                    localDataSet.remove(position);
                    notifyItemRemoved(position);
                    // Add the product to the cart
                }
            });
        }

        public LayoutProductBinding getBindingLayout() {
            return bindingLayout;
        }

    }

    public ProductListAdapter(ArrayList<String> dataSet, Context context, ActivityProductListBinding parent_binding) {
        this.localDataSet = dataSet;
        this.context = context;
        this.parent_binding = parent_binding;
    }

    public void setLocalDataSet(ArrayList<String> dataSet){
        this.localDataSet = dataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = Params.getMapProduct().get(localDataSet.get(position));
        holder.getBindingLayout().txtProductName.setText(product.getProductName());
        holder.getBindingLayout().txtProductPrice.setText("Price: ₹" + product.getPrice());
        Glide.with(context).load(product.getImg()).into(holder.getBindingLayout().imgProduct);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}