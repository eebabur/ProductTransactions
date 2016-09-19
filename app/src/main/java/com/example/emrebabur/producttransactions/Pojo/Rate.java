package com.example.emrebabur.producttransactions.Pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class Rate implements Serializable {
    @SerializedName("from")
    public String from;

    @SerializedName("rate")
    public BigDecimal rate;

    @SerializedName("to")
    public String to;
}
