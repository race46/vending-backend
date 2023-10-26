package com.aselsan.vending.algorithm;

import com.aselsan.vending.entity.Money;
import com.aselsan.vending.entity.Product;
import com.aselsan.vending.request.BuyProduct;

import java.util.Collections;
import java.util.List;

public class BuyExchangeCalculator {
    private int counter(int moneys[], int target) {
        int total = 0;
        for (Integer i : moneys) {
            if (target == i) ++total;
        }
        return total;
    }

    private int[] push(int arr[], int value) {
        int res[] = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++)
            res[i] = arr[i];

        res[arr.length] = value;

        return res;
    }

    private int[] calculator(List<Money> moneys, int current[], int remain, int index) {
        if (remain < 0) return null;
        if (remain == 0) return current;

        int res[] = null;
        for (int i = index; i < moneys.size(); i++) {
            Money m = moneys.get(i);
            if (m.getCount() > 0) {
                m.setCount(m.getCount() - 1);
                if (res == null)
                    res = calculator(moneys, push(current, m.getUnit()), remain - m.getUnit(), i);
                m.setCount(m.getCount() + 1);
            }
        }

        return res;
    }

    public int[] calculateExchanges(List<Money> moneys, BuyProduct buyProduct, Product product, int total) {
        Collections.reverse(moneys);
        moneys.forEach(money -> money.setCount(money.getCount() + counter(buyProduct.getMoneys(), money.getUnit())));
        int exchanges[] = calculator(moneys,new int[0], total - product.getPrice(), 0);

        if(exchanges != null){
            moneys.forEach(m -> m.setCount(m.getCount() - counter(exchanges, m.getUnit())));
        }
        return exchanges;
    }
}
