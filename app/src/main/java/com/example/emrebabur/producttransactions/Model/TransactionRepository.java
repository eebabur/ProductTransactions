package com.example.emrebabur.producttransactions.Model;

import android.content.Context;

import com.example.emrebabur.producttransactions.Pojo.Constants;
import com.example.emrebabur.producttransactions.Pojo.TransactionList;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class TransactionRepository {
    private Context mContext;
    private Gson gson;
    private TransactionList mTransactions;

    private static TransactionRepository ourInstance = new TransactionRepository();

    public static TransactionRepository getInstance() {
        return ourInstance;
    }

    private TransactionRepository() {
        gson = new Gson();
    }

    public TransactionList getTransactions(Context context) {
        mContext = context;
        if(mTransactions == null) {
            mTransactions = gson.fromJson(loadJSONFromAsset(Constants.TRANSACTIONS_FILE_PATH), TransactionList.class);
            return mTransactions;
        }
        else {
            return mTransactions;
        }
    }

    private String loadJSONFromAsset(String path) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
