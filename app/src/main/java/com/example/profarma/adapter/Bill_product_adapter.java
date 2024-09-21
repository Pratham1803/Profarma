package com.example.profarma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profarma.R;
import com.example.profarma.model.OrderProduct;

import java.util.List;

public class Bill_product_adapter extends RecyclerView.Adapter<Bill_product_adapter.ViewHolder> {
    private final Context context; // context
    private final List<OrderProduct> lsProduct;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtHsnNum; // hsn number
        private final TextView txtProductName; // product name
        private final TextView txtQuantity; // quantity
        private final TextView txtRate; // rate
        private final TextView txtTotal; // total

        public ViewHolder(@NonNull View currentItemView) {
            super(currentItemView);
            txtHsnNum = currentItemView.findViewById(R.id.txtHSNShow); // get the reference of the view objects
            txtProductName = currentItemView.findViewById(R.id.txtProductNameShow); // get the reference of the view objects
            txtQuantity = currentItemView.findViewById(R.id.txtQtyShow); // get the reference of the view objects
            txtRate = currentItemView.findViewById(R.id.txtRateShow); // get the reference of the view objects
            txtTotal = currentItemView.findViewById(R.id.txtValueShow); // get the reference of the view objects
        }

        // getter methods for the view objects
        public TextView getTxtHsnNum() {
            return txtHsnNum;
        }

        public TextView getTxtProductName() {
            return txtProductName;
        }

        public TextView getTxtQuantity() {
            return txtQuantity;
        }

        public TextView getTxtRate() {
            return txtRate;
        }

        public TextView getTxtTotal() {
            return txtTotal;
        }
    }

    // constructor of adapter class
    public Bill_product_adapter(Context context, List<OrderProduct> lsProduct) {
        this.context = context;
        this.lsProduct = lsProduct;
    }

    @NonNull
    @Override
    public Bill_product_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_listview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        holder.getTxtHsnNum().setText(String.valueOf(position + 1));
        holder.getTxtProductName().setText(lsProduct.get(position).getProductName());
        float quantity = Float.parseFloat(lsProduct.get(position).getProductQty());
        float rate = Float.parseFloat(lsProduct.get(position).getProductPrice());
        float total = quantity * rate;
        holder.getTxtQuantity().setText(String.valueOf(quantity));
        holder.getTxtRate().setText(String.valueOf(rate));

        holder.getTxtTotal().setText(String.format("%.2f", total));
    }

    @Override
    public int getItemCount() {
        return lsProduct.size();
    }
}