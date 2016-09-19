package com.example.emrebabur.producttransactions.Presenter;

import com.example.emrebabur.producttransactions.Pojo.Constants;
import com.example.emrebabur.producttransactions.Pojo.Rate;
import com.example.emrebabur.producttransactions.Pojo.RateList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class CurrencyConversionTable {

    private RateList mRates;
    private Map<String, Neighbours> currencyGraph;
    private Map<String, BigDecimal> conversionTable;
    public CurrencyConversionTable(RateList rates) {
        mRates = rates;
        currencyGraph = new HashMap<String, Neighbours>();
        conversionTable = new HashMap<String, BigDecimal>();
        initializeCurrencyGraph();
    }

    private void initializeCurrencyGraph() {
        for(Rate rate : mRates) {
            addEdge(rate.from, rate.to, rate.rate);
        }
}

    public BigDecimal convertCurrency(String currency, BigDecimal amount) {
        BigDecimal conversionRate = new BigDecimal(1);
        if(conversionTable.containsKey(currency)) {
            conversionRate = conversionTable.get(currency);
        } else {
            Set<CurrencyNode> visited = new HashSet<CurrencyNode>();
            Queue<CurrencyNode> queue = new LinkedList<CurrencyNode>();
            queue.add(new CurrencyNode(currency, conversionRate));
            while(!queue.isEmpty()) {
                CurrencyNode node = queue.poll();
                if(!visited.contains(node)) {
                    if (node.currency.equals(Constants.BASE_CURRENCY)) {
                        conversionRate = conversionRate.multiply(node.conversionDistance);
                        break;
                    } else {
                        visited.add(node);
                        Neighbours neighbours = currencyGraph.get(node.currency);
                        for(CurrencyNode neighbour : neighbours) {
                            if(!visited.contains(neighbour)) {
                                neighbour.conversionDistance = neighbour.conversionDistance.multiply(node.conversionDistance);
                                queue.add(neighbour);
                            }
                        }
                    }
                }
            }
            conversionTable.put(currency, conversionRate);
        }
        return amount.multiply(conversionRate);
    }

    private void addEdge(String from, String to, BigDecimal rate) {
        if(currencyGraph.containsKey(from)) {
            currencyGraph.get(from).add(new CurrencyNode(to, rate));
        }
        else {
            Neighbours neighbours = new Neighbours();
            neighbours.add(new CurrencyNode(to, rate));
            currencyGraph.put(from, neighbours);
        }
    }

    class Neighbours extends ArrayList<CurrencyNode>{}

    class CurrencyNode {
        public String currency;
        public BigDecimal rate;
        public BigDecimal conversionDistance;
        public CurrencyNode(String currency, BigDecimal rate) {
            this.currency = currency;
            this.rate = rate;
            this.conversionDistance = rate;
        }
    }
}
