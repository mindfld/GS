package com.goldstardiana.gs.model;

import com.google.common.base.MoreObjects;

public class EmailData {

    private String clientEmailAddress;
    private String selectedProduct;
    private String orderId;
    private String price;

    public EmailData() {
    }

    public EmailData(String clientEmailAddress, String selectedProduct, String orderId, String price) {
        this.clientEmailAddress = clientEmailAddress;
        this.selectedProduct = selectedProduct;
        this.orderId = orderId;
        this.price = price;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress(String clientEmailAddress) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(String selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clientEmailAddress", clientEmailAddress)
                .add("selectedProduct", selectedProduct)
                .add("orderId", orderId)
                .add("price", price)
                .toString();
    }
}
