package com.steel.product.application.dto.delivery;

import java.util.Date;
import java.util.List;

public class DeliveryDto {

    private List<DeliveryItemDetails> deliveryItemDetails;

    private String vehicleNo;

    private Integer deliveryId;

    private String customerInvoiceNo;

    private Date customerInvoiceDate;

    public List<DeliveryItemDetails> getDeliveryItemDetails() {
        return deliveryItemDetails;
    }

    public void setDeliveryItemDetails(List<DeliveryItemDetails> deliveryItemDetails) {
        this.deliveryItemDetails = deliveryItemDetails;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getCustomerInvoiceNo() {
        return customerInvoiceNo;
    }

    public void setCustomerInvoiceNo(String customerInvoiceNo) {
        this.customerInvoiceNo = customerInvoiceNo;
    }

    public Date getCustomerInvoiceDate() {
        return customerInvoiceDate;
    }

    public void setCustomerInvoiceDate(Date customerInvoiceDate) {
        this.customerInvoiceDate = customerInvoiceDate;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }
}
