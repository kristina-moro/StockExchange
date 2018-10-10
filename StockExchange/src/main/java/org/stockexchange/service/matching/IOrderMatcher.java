package org.stockexchange.service.matching;

import org.stockexchange.inout.OrderRecord;

import java.util.List;
import java.util.stream.Stream;

/**
 * Интерфейс для реализации матчингапоиска пар заявок
 */
public interface IOrderMatcher {

    List<Deal> processOrderStream(Stream<OrderRecord> orderRecordStream);

}
