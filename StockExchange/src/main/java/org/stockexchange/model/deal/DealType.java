package org.stockexchange.model.deal;

public enum DealType {

    SELL,  // продажа
    BUY,   // покупка
    UNKNOWN;

    public static DealType from(String value) {
        return value.equals("s") ? SELL : value.equals("b") ? BUY : UNKNOWN;
    }

}
