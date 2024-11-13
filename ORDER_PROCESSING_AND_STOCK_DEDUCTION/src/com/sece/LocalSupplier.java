package com.sece;

public class LocalSupplier extends Supplier {
    private String region;

    public LocalSupplier(int supplierId, String name, String contactInfo, String supplierType, String region) {
        super(supplierId, name, contactInfo, supplierType);
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "LocalSupplier{" +
                "region='" + region + '\'' +
                ", supplierId=" + getSupplierId() +
                ", name='" + getName() + '\'' +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", supplierType='" + getSupplierType() + '\'' +
                '}';
    }
}
