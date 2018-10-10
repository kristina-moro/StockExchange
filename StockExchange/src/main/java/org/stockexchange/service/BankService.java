package org.stockexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stockexchange.inout.ClientRecord;
import org.stockexchange.inout.reader.IFileReader;
import org.stockexchange.inout.rowmapper.FinInstrumentRowMapper;
import org.stockexchange.inout.rowmapper.IClientRowMapper;
import org.stockexchange.model.Account;
import org.stockexchange.model.Bank;
import org.stockexchange.model.Client;
import org.stockexchange.model.fininstrument.FinancialInstrument;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BankService {
    @Autowired
    private IFileReader fileReader;
    @Autowired
    private FinInstrumentRowMapper finInstrumentRowMapper;
    @Autowired
    private IClientRowMapper clientRowMapper;
    @Autowired
    private Bank bank;

    public void setup(Path finInstrumentPath, Path clientPath) throws Exception {

        Map<FinancialInstrument, Integer> financialInstrumentPositionMap =
                finInstrumentRowMapper.from(fileReader.readAllLines(finInstrumentPath));

        Stream<ClientRecord> clientStream =
                fileReader.readLines(clientPath)
                        .map(line -> clientRowMapper.from(line, financialInstrumentPositionMap));

        bank.setup(clientStream);
    }

    public List<String> getAccountList() {
        Map<Client, Account> map = bank.getClientAccountMap();
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getValue().getInfo())
                .collect(Collectors.toList());
    }
}
