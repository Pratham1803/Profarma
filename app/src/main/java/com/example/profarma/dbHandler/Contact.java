package com.example.profarma.dbHandler;

public class Contact {
    private int sNo;
    private String contactNum;
    private  String Name;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Contact(int sNo, String contactNum, String name) {
        this.sNo = sNo;
        this.contactNum = contactNum;
        this.Name = name;
    }

    public Contact() {

    }

    public Contact(String name, String contactNum) {
        this.contactNum = contactNum;
        Name = name;
    }
}
