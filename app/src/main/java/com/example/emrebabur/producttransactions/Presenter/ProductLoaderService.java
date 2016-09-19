package com.example.emrebabur.producttransactions.Presenter;

import android.app.IntentService;
import android.content.Intent;

import com.example.emrebabur.producttransactions.Model.RateRepository;
import com.example.emrebabur.producttransactions.Model.TransactionRepository;
import com.example.emrebabur.producttransactions.Pojo.Product;
import com.example.emrebabur.producttransactions.Pojo.ProductList;
import com.example.emrebabur.producttransactions.Pojo.RateList;
import com.example.emrebabur.producttransactions.Pojo.Transaction;
import com.example.emrebabur.producttransactions.Pojo.TransactionList;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ProductLoaderService extends IntentService {

    private CurrencyConversionTable currencyConversionTable;
    public ProductLoaderService() {
        super("ProductLoaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Gson gson = new Gson();
            RateList rates = RateRepository.getInstance().getRates(this);
            currencyConversionTable = createCurrencyConversionTable(rates);

            TransactionList transactions = TransactionRepository.getInstance().getTransactions(this);
            ProductList products = initializeProducts(transactions);
            EventBus.getDefault().post(products);
        }
        else
            throw new IllegalArgumentException("Service intent is null!");
    }



    private ProductList initializeProducts(TransactionList transactions)
    {
        Map<String, TransactionList> productTransactions = new HashMap<String, TransactionList>();
        Map<String, BigDecimal> productTransactionTotals = new HashMap<String, BigDecimal>();
        for(int i=0; i<transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            transaction.baseAmount = currencyConversionTable.convertCurrency(transaction.currency, transaction.amount);
            if(productTransactions.containsKey(transaction.sku)) {
                productTransactions.get(transaction.sku).add(transaction);
                productTransactionTotals.put(transaction.sku, productTransactionTotals.get(transaction.sku).add(transaction.amount));
            }
            else {
                productTransactions.put(transaction.sku, new TransactionList());
                productTransactions.get(transaction.sku).add(transaction);
                productTransactionTotals.put(transaction.sku, transaction.amount);
            }
        }

        ProductList products = new ProductList();
        for (Map.Entry<String, TransactionList> entry : productTransactions.entrySet()) {
            String sku = entry.getKey();
            products.add(new Product(sku, entry.getValue(), productTransactionTotals.get(sku)));
        }
        return products;
    }

    private CurrencyConversionTable createCurrencyConversionTable(RateList rates) {
        CurrencyConversionTable currencyConversionTable = new CurrencyConversionTable(rates);
        return currencyConversionTable;
    }
}
