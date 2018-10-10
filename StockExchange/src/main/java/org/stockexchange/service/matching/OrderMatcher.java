package org.stockexchange.service.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stockexchange.inout.OrderRecord;
import org.stockexchange.model.Bank;
import org.stockexchange.model.Client;
import org.stockexchange.model.deal.Order;
import org.stockexchange.model.fininstrument.FinancialInstrument;
import org.stockexchange.model.deal.DealType;
import org.stockexchange.model.fininstrument.FinancialInstrumentType;

import java.util.*;
import java.util.stream.Stream;

/**
 * Имплементация алгоритма для поиска пар заявок
 */

@Component
public class OrderMatcher implements IOrderMatcher {
    @Autowired
    private FinancialInstrumentType financialInstrument;
    @Autowired
    private Bank bank;

    private Map<OrderParams, List<Client>> ordersForBuy; // Несматченные заявки на покупку
    private Map<OrderParams, List<Client>> ordersForSell; // Несматченные заявки на продажу
    private List<Deal> dealList; // Сматченные заявки, превращаются в сделку

    public List<Deal> processOrderStream(Stream<OrderRecord> orderRecordStream) {
        ordersForBuy = new HashMap<>();
        ordersForSell = new HashMap<>();
        dealList = new ArrayList<>();
        orderRecordStream.forEach(orderRecord -> {
            // создаем заявку из входного потока
            Client client = bank.getClientByName(orderRecord.getClient());
            FinancialInstrument fi = financialInstrument.of(orderRecord.getSecurity());
            OrderParams orderParams = new OrderParams(fi, orderRecord.getPrice(), orderRecord.getQuantity());

            ordersForBuy.putIfAbsent(orderParams, new ArrayList<>());
            ordersForSell.putIfAbsent(orderParams, new ArrayList<>());

            Order order = new Order(client, orderRecord.getDealType(), orderParams);
            // и сразу же пытаемся сматчить
            matchOrder(order);
        });

        return dealList;
    }

    private void matchOrder(Order order) {
        if (order.getDealType() == DealType.BUY) {
            Optional<Client> seller = match(order.getClient(), order.getParams(), ordersForSell);
            if (seller.isPresent() && seller.get() != order.getClient()) {
                Deal deal = new Deal(order.getParams(), order.getClient(), seller.get());
                delete(order.getParams(), seller.get(), ordersForSell);
                dealList.add(deal);
            }
            else {
                ordersForBuy.get(order.getParams()).add(order.getClient());
            }
        }

        if (order.getDealType() == DealType.SELL) {
            Optional<Client> buyer = match(order.getClient(), order.getParams(), ordersForBuy);
            if (buyer.isPresent() && buyer.get() != order.getClient()) {
                Deal deal = new Deal(order.getParams(), buyer.get(), order.getClient());
                delete(order.getParams(), buyer.get(), ordersForBuy);
                dealList.add(deal);
            }
            else {
                ordersForSell.get(order.getParams()).add(order.getClient());
            }
        }
    }

    private Optional<Client> match(Client client, OrderParams orderParams, Map<OrderParams, List<Client>> searchMap) {
        return searchMap.get(orderParams).stream().filter(c -> !c.equals(client)).findFirst();
    }

    private void delete(OrderParams orderParams, Client client, Map<OrderParams, List<Client>> findMap) {
        findMap.get(orderParams).remove(client);
    }

}
