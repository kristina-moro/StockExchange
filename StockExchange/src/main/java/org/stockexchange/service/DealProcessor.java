package org.stockexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stockexchange.service.matching.Deal;
import org.stockexchange.service.matching.OrderParams;
import org.stockexchange.model.*;
import org.stockexchange.model.fininstrument.FinancialInstrument;
import org.stockexchange.model.fininstrument.IFinancialInstrumentType;

/**
 * Обработчик сделок (сматченных пар заявок)
 */

@Service
public class DealProcessor {
    @Autowired
    private IFinancialInstrumentType financialInstrumentType;
    @Autowired
    private Bank bank;

    public void processDeal(Deal deal) {
        FinancialInstrument usd = financialInstrumentType.of("USD");
        OrderParams orderParams = deal.getOrderParams();
        changeBalance(deal.getBuyer(), orderParams.getFinancialInstrument(), orderParams.getQuantity());
        changeBalance(deal.getBuyer(), usd, -1 * orderParams.getQuantity() * orderParams.getPrice());
        changeBalance(deal.getSeller(), orderParams.getFinancialInstrument(), -1 * orderParams.getQuantity());
        changeBalance(deal.getSeller(), usd, orderParams.getQuantity() * orderParams.getPrice());
    }

    private void changeBalance(Client client, FinancialInstrument financialInstrument, Integer value) {
        Account account = bank.getClientAccount(client);
        account.add(financialInstrument, value);
    }

}
