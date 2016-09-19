package com.example.emrebabur.producttransactions.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.emrebabur.producttransactions.Pojo.Constants;
import com.example.emrebabur.producttransactions.Pojo.Transaction;
import com.example.emrebabur.producttransactions.R;

import java.util.List;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class TransactionAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Transaction> mTransactions;
    private Context mContext;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        mInflater = LayoutInflater.from(context);
        mTransactions = transactions;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mTransactions.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_product, parent, false);
            holder.amount = (TextView) convertView.findViewById(R.id.Sku);
            holder.baseAmount = (TextView) convertView.findViewById(R.id.TransactionCount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Transaction transaction = mTransactions.get(position);
        String currencyLabel = "";
        if(Constants.currencySymbols.get(transaction.currency) == null)
            currencyLabel = transaction.currency;
        else
            currencyLabel = Constants.currencySymbols.get(transaction.currency);
        holder.amount.setText(currencyLabel+String.format( "%.2f", transaction.amount));
        holder.baseAmount.setText(mContext.getResources().getString(R.string.base_currency_label)+String.format( "%.2f", transaction.baseAmount));

        return convertView;
    }

    class ViewHolder {
        TextView amount;
        TextView baseAmount;

    }

}
