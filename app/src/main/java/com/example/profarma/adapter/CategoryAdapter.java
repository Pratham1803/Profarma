package com.example.profarma.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.profarma.Params;
import com.example.profarma.ProductList;
import com.example.profarma.R;
import com.example.profarma.databinding.LayoutCategoryBinding;
import com.example.profarma.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<String> localDataSet;
    private Context context;

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LayoutCategoryBinding bindingLayout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingLayout = LayoutCategoryBinding.bind(itemView);
            bindingLayout.layoutCategoryParent.setOnClickListener(this);
        }

        public LayoutCategoryBinding getBindingLayout() {
            return bindingLayout;
        }

        @Override
        public void onClick(View v) {
            LinearLayout layout = (LinearLayout) v;

            String category = localDataSet.get(getAdapterPosition());

            Intent intent = new Intent(context, ProductList.class);
            intent.putExtra("Category", category);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

    }

    public CategoryAdapter(ArrayList<String> dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
    }

    public void setLocalDataSet(ArrayList<String> localDataSet) {
        this.localDataSet = localDataSet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.getBindingLayout().txtCategoryName.setText(localDataSet.get(position));
        holder.getBindingLayout().txtTotalItems.setText("Total Items : " + Params.getMapProductCategory().get(localDataSet.get(position)).size());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}