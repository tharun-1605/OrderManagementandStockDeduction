package com.sece;

public class InternationalSupplier extends Supplier {
    private String country;
    private String importLicense;

    public InternationalSupplier(int supplierId, String name, String contactInfo, String supplierType, String country, String importLicense) {
        super(supplierId, name, contactInfo, supplierType);
        this.country = country;
        this.importLicense = importLicense;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImportLicense() {
        return importLicense;
    }

    public void setImportLicense(String importLicense) {
        this.importLicense = importLicense;
    }

    @Override
    public String toString() {
        return "InternationalSupplier{" +
                "country='" + country + '\'' +
                ", importLicense='" + importLicense + '\'' +
                ", supplierId=" + getSupplierId() +
                ", name='" + getName() + '\'' +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", supplierType='" + getSupplierType() + '\'' +
                '}';
    }
}
