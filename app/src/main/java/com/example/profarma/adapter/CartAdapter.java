package com.example.profarma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profarma.Params;
import com.example.profarma.R;
import com.example.profarma.databinding.LayoutCartProductBinding;
import com.example.profarma.model.OrderCart;
import com.example.profarma.model.OrderProduct;
import com.example.profarma.model.ProductModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder> {
    private final ArrayList<String> localDataSet;
    private Context context;
    private final OrderCart orderCart;

    public class CartAdapterViewHolder extends RecyclerView.ViewHolder {
        private final LayoutCartProductBinding bindingLayout;

        public CartAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingLayout = LayoutCartProductBinding.bind(itemView);
        }

        public void bind(int position, ProductModel product) {
            bindingLayout.txtProductName.setText(product.getProductName());
            bindingLayout.txtCategory.setText("Category: " + product.getCategory());
            bindingLayout.txtPrice.setText("Price: "+product.getPrice());

            bindingLayout.imgBtnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = Integer.parseInt(bindingLayout.txtQuantity.getText().toString());
                    if (quantity > 0) {
                        quantity--;
                        orderCart.getLsProduct().get(position).setProductQty(String.valueOf(quantity));
                        bindingLayout.txtQuantity.setText(String.valueOf(quantity));
                    }
                }
            });

            bindingLayout.imgBtnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = Integer.parseInt(bindingLayout.txtQuantity.getText().toString());
                    quantity++;
                    orderCart.getLsProduct().get(position).setProductQty(String.valueOf(quantity));
                    bindingLayout.txtQuantity.setText(String.valueOf(quantity));
                }
            });

            bindingLayout.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    localDataSet.remove(position);
                    notifyItemRemoved(position);
                    Params.getDbReference().child("cart").child(product.getProductId()).removeValue();
                }
            });
        }
    }

    public CartAdapter(ArrayList<String> dataSet, Context context, OrderCart orderCart) {
        this.localDataSet = dataSet;
        this.context = context;
        this.orderCart = orderCart;
    }

    @NonNull
    @Override
    public CartAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_cart_product, parent, false);
        return new CartAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapterViewHolder holder, int position) {
        ProductModel productModel = Params.getMapProduct().get(localDataSet.get(position));
        orderCart.getLsProduct().add(new OrderProduct(productModel.getProductId(), productModel.getProductName(), productModel.getPrice(), "1"));
        holder.bind(position, productModel);
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
