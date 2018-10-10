package org.stockexchange.inout;

import org.stockexchange.model.fininstrument.FinancialInstrument;

import java.util.Map;

public class ClientRecord {

    private String name;
    private Map<FinancialInstrument, Integer> acc;

    public ClientRecord(String name, Map<FinancialInstrument, Integer> acc) {
        this.name = name;
        this.acc = acc;
    }

    public String getName() {
        return name;
    }

    public Map<FinancialInstrument, Integer> getAcc() {
        return acc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientRecord{");
        sb.append("name='").append(name).append('\'');
        sb.append(", acc=").append(acc);
        sb.append('}');
        return sb.toString();
    }
}
