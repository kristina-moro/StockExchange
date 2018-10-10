package org.stockexchange.model.fininstrument;

/**
 * Финансовый инструмент: валюта, ценная бумага или металл
 */

public class FinancialInstrument {
    private String name;

    public FinancialInstrument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialInstrument that = (FinancialInstrument) o;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
