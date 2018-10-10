package org.stockexchange.inout.rowmapper;

import org.stockexchange.inout.ClientRecord;
import org.stockexchange.model.fininstrument.FinancialInstrument;

import java.util.Map;

public interface IClientRowMapper {

    ClientRecord from(String line, Map<FinancialInstrument, Integer> financialInstrumentPositionMap);

}
