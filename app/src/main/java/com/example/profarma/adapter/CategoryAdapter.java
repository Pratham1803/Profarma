package com.example.profarma.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.profarma.ProductList;
import com.example.profarma.R;
import com.example.profarma.databinding.LayoutCategoryBinding;
import com.example.profarma.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<Category> localDataSet;
    private Context context;

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LayoutCategoryBinding bindingLayout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingLayout = LayoutCategoryBinding.bind(itemView);
            bindingLayout.layoutItem.setOnClickListener(this);
            bindingLayout.layoutLarge.setOnClickListener(this);
            bindingLayout.layoutMedium.setOnClickListener(this);
            bindingLayout.layoutSmall.setOnClickListener(this);
        }

        public LayoutCategoryBinding getBindingLayout() {
            return bindingLayout;
        }

        @Override
        public void onClick(View v) {
            LinearLayout layout = (LinearLayout) v;

            Category category = localDataSet.get(getAdapterPosition());
            if (layout.getId() == R.id.layoutItem) {
                if (bindingLayout.layoutSubCategory.getVisibility() == View.VISIBLE)
                    bindingLayout.layoutSubCategory.setVisibility(View.GONE);
                else if (bindingLayout.layoutSubCategory.getVisibility() == View.GONE)
                    bindingLayout.layoutSubCategory.setVisibility(View.VISIBLE);

                bindingLayout.imgArrow.setRotation(bindingLayout.layoutSubCategory.getVisibility() == View.VISIBLE ? 180 : 0);
                return;
            } else {
                if (layout.getId() == R.id.layoutLarge)
                    category.setSubCategory("Large");
                else if (layout.getId() == R.id.layoutMedium)
                    category.setSubCategory("Medium");
                else if (layout.getId() == R.id.layoutSmall)
                    category.setSubCategory("Small");

                bindingLayout.layoutSubCategory.setVisibility(View.GONE);
                bindingLayout.imgArrow.setRotation(0);

                Intent intent = new Intent(context, ProductList.class);
                intent.putExtra("Category", category);
                context.startActivity(intent);
            }
        }
    }

    public CategoryAdapter(ArrayList<Category> dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
    }

    public void setLocalDataSet(ArrayList<Category> localDataSet){
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
        holder.getBindingLayout().txtCategoryName.setText(localDataSet.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}