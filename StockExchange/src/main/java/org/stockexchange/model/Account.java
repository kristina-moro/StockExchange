package org.stockexchange.model;

import org.stockexchange.model.fininstrument.FinancialInstrument;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Мультисчёт клиента
 */

public class Account {

    private final Client client;
    private final Map<FinancialInstrument, Integer> balance = new LinkedHashMap<>();

    public Account(Client client) {
        this.client = client;
    }

    public void add(FinancialInstrument financialInstrument, int value) {
        balance.putIfAbsent(financialInstrument, 0);
        balance.compute(financialInstrument, (k,v) -> v + value);
    }

    public Integer getFinInstrumentBalance(FinancialInstrument fi) {
        return balance.get(fi);
    }

    public String getInfo() {
        final StringBuilder sb = new StringBuilder(client.getName());
        balance.forEach((k,v) -> sb.append("\t").append(v.intValue()));
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append(balance);
        sb.append('}');
        return sb.toString();
    }
}
