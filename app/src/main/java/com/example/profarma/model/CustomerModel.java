package com.example.profarma.model;

public class CustomerModel {
    private String name;
    private String contact;
    private String address;

    public CustomerModel() {
        // Required empty public constructor
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomerModel(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }
}
