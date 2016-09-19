package com.example.emrebabur.producttransactions.Pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class Product implements Comparable<Product>, Serializable {
    public String sku;
    public List<Transaction> transactions;
    public BigDecimal totalTransactionAmount;

    public Product(String sku, List<Transaction> transactions, BigDecimal totalTransactionAmount) {
        this.sku = sku;
        this.transactions = transactions;
        this.totalTransactionAmount = totalTransactionAmount;
    }

    public int getTransactionCount() {
        return transactions.size();
    }

    public int compareTo(Product other) {
        return sku.compareTo(other.sku);
    }
}
