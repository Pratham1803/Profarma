package com.example.profarma.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.profarma.Params;
import com.example.profarma.R;
import com.example.profarma.databinding.LayoutCartProductBinding;
import com.example.profarma.model.OrderCart;
import com.example.profarma.model.OrderProduct;
import com.example.profarma.model.Product;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder>{
    private ArrayList<String> localDataSet;
    private Context context;
    private OrderCart orderCart;

    public class CartAdapterViewHolder extends RecyclerView.ViewHolder{
        private final LayoutCartProductBinding bindingLayout;

        public CartAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingLayout = LayoutCartProductBinding.bind(itemView);
            bindingLayout.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    localDataSet.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }

        public LayoutCartProductBinding getBindingLayout() {
            return bindingLayout;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_product, parent, false);
        return new CartAdapterViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapterViewHolder holder, int position) {
        Product product = Params.getMapProduct().get(localDataSet.get(position));
        holder.getBindingLayout().txtProductName.setText(product.getProductName());

        holder.getBindingLayout().txtCategory.setText(product.getCategory()+" "+product.getSubCategory());

        holder.getBindingLayout().txtQuantity.setText("Quantity : "+product.getQuantity());

        Glide.with(context).load(product.getImg()).into(holder.getBindingLayout().imgProduct);

        orderCart.getLsProduct().add(new OrderProduct(product.getProductId(), product.getProductName(), product.getPrice(), product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
