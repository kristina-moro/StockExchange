package org.stockexchange.inout.rowmapper;

import org.stockexchange.inout.OrderRecord;

public interface IOrderRowMapper {

    OrderRecord from(String line);

}
