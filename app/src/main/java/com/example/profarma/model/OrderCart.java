package com.example.profarma.model;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderCart implements Serializable {
    private String orderId;
    private String orderDate;
    private String totalAmt;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private ArrayList<OrderProduct> lsProduct = new ArrayList<>();

    public OrderCart() {}

    public ArrayList<OrderProduct> getLsProduct() {
        return lsProduct;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public void setLsProduct(ArrayList<OrderProduct> lsProduct) {
        this.lsProduct = lsProduct;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
}