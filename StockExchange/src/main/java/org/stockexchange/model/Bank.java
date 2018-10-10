package org.stockexchange.model;

import org.springframework.stereotype.Component;
import org.stockexchange.inout.ClientRecord;

import java.util.*;
import java.util.stream.Stream;

/**
 * Банк (депозитарий)
 */

@Component
public class Bank {
    private Map<String, Client> clientMap;
    private Map<Client, Account> clientAccountMap;

    public void setup(Stream<ClientRecord> clientRecordStream) {
        clientMap = new HashMap<>();
        clientAccountMap = new HashMap<>();
        clientRecordStream.forEach(clientRecord -> {
            Client client = new Client(clientRecord.getName());
            clientMap.put(clientRecord.getName(), client);
            Account account = new Account(client);
            clientRecord.getAcc().forEach(account::add);
            clientAccountMap.put(client, account);
        });
    }

    public Map<Client, Account> getClientAccountMap() {
        return Collections.unmodifiableMap(clientAccountMap);
    }

    public Client getClientByName(String clientName) {
        return clientMap.get(clientName);
    }

    public Account getClientAccount(Client client) {
        return clientAccountMap.get(client);
    }

}
