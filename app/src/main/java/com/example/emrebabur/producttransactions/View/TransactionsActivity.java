package com.example.emrebabur.producttransactions.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emrebabur.producttransactions.Pojo.Product;
import com.example.emrebabur.producttransactions.R;
import com.example.emrebabur.producttransactions.View.TransactionAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionsActivity extends AppCompatActivity {

    private Product mProduct;
    private TransactionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        ButterKnife.bind(this);
        mProduct = (Product) getIntent().getSerializableExtra("product");
        productName.setText(getResources().getString(R.string.product_label) + " " + mProduct.sku);
        totalTransaction.setText(getResources().getString(R.string.total_transaction_label) + " " + String.valueOf(mProduct.totalTransactionAmount));
        mAdapter = new TransactionAdapter(getApplicationContext(), mProduct.transactions);
        transactionList.setAdapter(mAdapter);
    }

    @Bind(R.id.ProductName)
    TextView productName;

    @Bind(R.id.TotalTransaction)
    TextView totalTransaction;

    @Bind(R.id.TransactionList)
    ListView transactionList;

}
