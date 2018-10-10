package org.stockexchange.model.fininstrument;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Справочник финансовых инструментов
 */

@Component
public class FinancialInstrumentType implements IFinancialInstrumentType {

    private Map<String, FinancialInstrument> finInstrument = new HashMap<>();

    @PostConstruct
    public void populate() {
        finInstrument.put("USD", new FinancialInstrument("USD"));
        finInstrument.put("A", new FinancialInstrument("A"));
        finInstrument.put("B", new FinancialInstrument("B"));
        finInstrument.put("C", new FinancialInstrument("C"));
        finInstrument.put("D", new FinancialInstrument("D"));
    }

    public FinancialInstrument of(String value) {
        return finInstrument.get(value);
    }

    public Map<String, FinancialInstrument> getFinancialInstruments() {
        return Collections.unmodifiableMap(finInstrument);
    }
}
