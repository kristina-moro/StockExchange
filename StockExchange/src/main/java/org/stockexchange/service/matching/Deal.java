package org.stockexchange.service.matching;

import org.stockexchange.model.Client;

/**
 * Сделка. Создается при матчинге заявок
 */

public class Deal {

    private OrderParams orderParams;  // Параметры заявки
    private Client buyer;             // Покупатель
    private Client seller;            // Продавец

    public Deal(OrderParams orderParams, Client buyer, Client seller) {
        this.orderParams = orderParams;
        this.buyer = buyer;
        this.seller = seller;
    }

    public OrderParams getOrderParams() {
        return orderParams;
    }

    public Client getBuyer() {
        return buyer;
    }

    public Client getSeller() {
        return seller;
    }

    public String getInfo() {
        final StringBuilder sb = new StringBuilder(seller.getName());
        sb.append(" -> ").append(buyer.getName()).append(" : ");
        sb.append(orderParams.toString());
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderParams{");
        sb.append("orderParams=").append(orderParams);
        sb.append(", buyer=").append(buyer);
        sb.append(", seller=").append(seller);
        sb.append('}');
        return sb.toString();
    }
}
