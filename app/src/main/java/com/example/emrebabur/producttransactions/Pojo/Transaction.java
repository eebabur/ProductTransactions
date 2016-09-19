package com.example.emrebabur.producttransactions.Pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class Transaction implements Serializable {
    @SerializedName("amount")
    public BigDecimal amount;

    @SerializedName("sku")
    public String sku;

    @SerializedName("currency")
    public String currency;

    public BigDecimal baseAmount;
}
