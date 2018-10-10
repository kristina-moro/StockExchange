package org.stockexchange.model.deal;

import org.stockexchange.model.Client;
import org.stockexchange.service.matching.OrderParams;

/**
 * Заявка на совершение сделки
 */

public class Order {
    private Client client;      // Клиент
    private DealType dealType;  // Тип сделки: "s" - продажа, "b" - покупка
    private OrderParams params; // Параметры сделки

    public Order(Client client, DealType dealType, OrderParams params) {
        this.client = client;
        this.dealType = dealType;
        this.params = params;
    }

    public Client getClient() {
        return client;
    }

    public DealType getDealType() {
        return dealType;
    }

    public OrderParams getParams() {
        return params;
    }
}
