package com.sece;

public class Supplier {
    private int supplierId;
    private String name;
    private String contactInfo;
    private String supplierType;

    // Constructor
    public Supplier(int supplierId, String name, String contactInfo, String supplierType) {
        this.supplierId = supplierId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.supplierType = supplierType;
    }

    // Getters and Setters
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", supplierType='" + supplierType + '\'' +
                '}';
    }
}
