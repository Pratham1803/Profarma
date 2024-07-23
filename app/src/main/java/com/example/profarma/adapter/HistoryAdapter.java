package com.example.profarma.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profarma.Billing;
import com.example.profarma.R;
import com.example.profarma.databinding.LayoutHistoryBinding;
import com.example.profarma.model.OrderCart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder> {
    private ArrayList<OrderCart> localDataSet;
    private Context context;

    public class HistoryAdapterViewHolder extends RecyclerView.ViewHolder {
        private final LayoutHistoryBinding bindingLayout;

        public HistoryAdapterViewHolder( View itemView) {
            super(itemView);
            bindingLayout = LayoutHistoryBinding.bind(itemView);
        }

        public void bind(int position) {
            OrderCart orderCart = localDataSet.get(position);

            bindingLayout.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(context, Billing.class);
                intent.putExtra("orderCart", orderCart);
                context.startActivity(intent);
            });

            bindingLayout.txtOrderId.setText("Order Id : "+ orderCart.getOrderId());
            bindingLayout.txtCompanyName.setText("Company Name : " + orderCart.getCompanyName());
            bindingLayout.txtTotal.setText("Total Amount : £" + orderCart.getTotalAmt());

            Date date = new Date(Long.parseLong(orderCart.getOrderId()));
            // Format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = "Date : " + dateFormat.format(date);
            bindingLayout.txtDate.setText(formattedDate);
        }
    }

    public HistoryAdapter(ArrayList<OrderCart> dataSet, Context context) {
        this.localDataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history, parent, false);
        return new HistoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setLocalDataSet(ArrayList<OrderCart> localDataSet) {
        this.localDataSet = localDataSet;
        notifyDataSetChanged();
    }

}