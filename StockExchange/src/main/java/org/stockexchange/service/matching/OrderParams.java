package org.stockexchange.service.matching;

import org.stockexchange.model.fininstrument.FinancialInstrument;

/**
 * Параметры заявки. Используется как ключ при матчинге заявок
 */

public class OrderParams {
    private FinancialInstrument financialInstrument;  // Финансовый инструмент (ценная бумага)
    private Integer price;                            // Цена
    private Integer quantity;                         // Количество

    public OrderParams(FinancialInstrument financialInstrument, Integer price, Integer quantity) {
        this.financialInstrument = financialInstrument;
        this.price = price;
        this.quantity = quantity;
    }

    public FinancialInstrument getFinancialInstrument() {
        return financialInstrument;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderParams orderParams = (OrderParams) o;
        if (!financialInstrument.equals(orderParams.financialInstrument)) return false;
        if (!price.equals(orderParams.price)) return false;
        return quantity.equals(orderParams.quantity);

    }

    @Override
    public int hashCode() {
        int result = financialInstrument.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + quantity.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderParams{");
        sb.append("financialInstrument=").append(financialInstrument);
        sb.append(", price=").append(price);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}