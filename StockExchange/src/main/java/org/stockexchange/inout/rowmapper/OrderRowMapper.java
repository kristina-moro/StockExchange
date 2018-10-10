package org.stockexchange.inout.rowmapper;

import org.springframework.stereotype.Component;
import org.stockexchange.inout.OrderRecord;
import org.stockexchange.model.deal.DealType;

@Component
public class OrderRowMapper implements IOrderRowMapper {

    private final static String DELIMITER = "[\\t]+";

    public OrderRecord from(String line) {
        String[] tokens = line.split(DELIMITER);
        return new OrderRecord(tokens[0], DealType.from(tokens[1]), tokens[2], Integer.valueOf(tokens[3]), Integer.valueOf(tokens[4]));
    }

}
