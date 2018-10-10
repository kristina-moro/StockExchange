package org.stockexchange.inout.rowmapper;

import org.springframework.stereotype.Component;
import org.stockexchange.inout.ClientRecord;
import org.stockexchange.model.fininstrument.FinancialInstrument;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ClientRowMapper implements IClientRowMapper {

    private final static String DELIMITER = "[\\t]+";

    public ClientRecord from(String line, Map<FinancialInstrument, Integer> financialInstrumentPositionMap) {
        String[] tokens = line.split(DELIMITER);
        Map<FinancialInstrument, Integer> acc = new LinkedHashMap<>();
        financialInstrumentPositionMap.forEach((k, v) -> acc.put(k, Integer.valueOf(tokens[v])));
        return new ClientRecord(tokens[0], acc);
    }

}

