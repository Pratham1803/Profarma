package com.example.profarma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profarma.R;
import com.example.profarma.databinding.ContactsLayoutBinding;
import com.example.profarma.model.CustomerModel;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private final ArrayList<CustomerModel> arrCustomer;
    private final Context context;

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contacts_layout, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        CustomerModel customer = arrCustomer.get(position);
        holder.bind(customer);
    }

    @Override
    public int getItemCount() {
        return arrCustomer.size();
    }

    public CustomerAdapter(ArrayList<CustomerModel> arrCustomer, Context context) {
        this.arrCustomer = arrCustomer;
        this.context = context;
    }

    public ArrayList<CustomerModel> getArrCustomer() {
        return arrCustomer;
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final ContactsLayoutBinding bindingLayout;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingLayout = ContactsLayoutBinding.bind(itemView);
        }

        public void bind(CustomerModel customer) {
            bindingLayout.txtContactName.setText(customer.getName());
            bindingLayout.txtContactNum.setText("Contact: " + customer.getContact());

            bindingLayout.txtUserProfile.setText(customer.getName().substring(0, 2).toUpperCase());
        }
    }
}