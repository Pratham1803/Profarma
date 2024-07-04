package com.example.profarma.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profarma.R;
import com.example.profarma.databinding.LayoutCartProductBinding;
import com.example.profarma.model.Product;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder>{
    private ArrayList<Product> localDataSet;
    private Context context;

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

    public CartAdapter(ArrayList<Product> dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
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
        holder.getBindingLayout().txtProductName.setText(localDataSet.get(position).getProductName());

        holder.getBindingLayout().txtCategory.setText(localDataSet.get(position).getCategory()+" "+localDataSet.get(position).getSubCategory());

        holder.getBindingLayout().txtQuantity.setText("Quantity : "+localDataSet.get(position).getQuantity());

        holder.getBindingLayout().imgProduct.setImageResource(localDataSet.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
