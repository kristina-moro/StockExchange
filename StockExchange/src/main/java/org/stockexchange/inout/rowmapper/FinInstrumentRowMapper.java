package org.stockexchange.inout.rowmapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stockexchange.model.fininstrument.FinancialInstrument;
import org.stockexchange.model.fininstrument.FinancialInstrumentType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
public class FinInstrumentRowMapper {
    @Autowired
    private FinancialInstrumentType financialInstrumentType;

    public Map<FinancialInstrument, Integer> from(List<String> lines) {
        Map<FinancialInstrument, Integer> map = new LinkedHashMap<>();
        for (int i=0; i< lines.size(); i++) {
            map.put(financialInstrumentType.of(lines.get(i)), i+1);
        }
        return map;
    }
}
