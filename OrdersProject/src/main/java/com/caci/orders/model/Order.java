package com.caci.orders.model;

import java.util.UUID;

public class Order {
    private UUID orderReference;
    private int bricksNumber;

    private String type;

    private boolean isDispatched = false;

    private UUID updatedOrderReference;

    public UUID getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(UUID orderReference) {
        this.orderReference = orderReference;
    }

    public int getBricksNumber() {
        return bricksNumber;
    }

    public void setBricksNumber(int bricksNumber) {
        this.bricksNumber = bricksNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getUpdatedOrderReference() {
        return updatedOrderReference;
    }

    public void setUpdatedOrderReference(UUID updatedOrderReference) {
        this.updatedOrderReference = updatedOrderReference;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }
}
