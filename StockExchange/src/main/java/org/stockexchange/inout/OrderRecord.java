package org.stockexchange.inout;

import org.stockexchange.model.deal.DealType;

public class OrderRecord {

    private String client;
    private DealType dealType;
    private String security;
    private Integer price;
    private Integer quantity;

    public OrderRecord(String client, DealType dealType, String security, Integer price, Integer quantity) {
        this.client = client;
        this.dealType = dealType;
        this.security = security;
        this.price = price;
        this.quantity = quantity;
    }

    public String getClient() {
        return client;
    }

    public DealType getDealType() {
        return dealType;
    }

    public String getSecurity() {
        return security;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderRecord{");
        sb.append("client='").append(client).append('\'');
        sb.append(", dealType=").append(dealType);
        sb.append(", security='").append(security).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
