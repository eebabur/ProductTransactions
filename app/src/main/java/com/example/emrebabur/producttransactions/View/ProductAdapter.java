package com.example.emrebabur.producttransactions.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.emrebabur.producttransactions.Pojo.Product;
import com.example.emrebabur.producttransactions.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class ProductAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Product> mProducts;
    private Context mContext;

    public ProductAdapter(Context context, List<Product> products) {
        mInflater = LayoutInflater.from(context);
        mProducts = products;
        Collections.sort(products);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
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
            holder.sku = (TextView) convertView.findViewById(R.id.Sku);
            holder.transactionCount = (TextView) convertView.findViewById(R.id.TransactionCount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.sku.setText(mProducts.get(position).sku);
        holder.transactionCount.setText(String.valueOf(mProducts.get(position).getTransactionCount()) + " " + mContext.getResources().getString(R.string.transactions));

        return convertView;
    }

    class ViewHolder {
        TextView sku;
        TextView transactionCount;

    }

}
