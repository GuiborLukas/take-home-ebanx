package com.gmail.lgsc92.repository;

import com.gmail.lgsc92.model.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepository {

    private final Map<String, Account> accountStore = new ConcurrentHashMap<>();

    public Map<String, Account> getAccountStore() {
        return accountStore;
    }

    public void reset() {
        accountStore.clear();
    }
}
