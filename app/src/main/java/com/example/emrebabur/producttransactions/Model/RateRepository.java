package com.example.emrebabur.producttransactions.Model;

import android.content.Context;

import com.example.emrebabur.producttransactions.Pojo.Constants;
import com.example.emrebabur.producttransactions.Pojo.RateList;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class RateRepository {
    private Context mContext;
    private Gson gson;
    private RateList mRates;

    private static RateRepository ourInstance = new RateRepository();

    public static RateRepository getInstance() {
        return ourInstance;
    }

    private RateRepository() {
        gson = new Gson();
    }

    public RateList getRates(Context context) {
        mContext = context;
        if(mRates == null) {
            mRates = gson.fromJson(loadJSONFromAsset(Constants.RATES_FILE_PATH), RateList.class);
            return mRates;
        }
        else {
            return mRates;
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
