package com.example.theory.gratuity;

import java.math.BigDecimal;
import java.text.NumberFormat;

class Calculator {
    private BigDecimal gratuityBase = new BigDecimal(0);
    private BigDecimal total = new BigDecimal(0);

    public BigDecimal getGratuity() {
        return this.total.multiply(gratuityBase);
    }

    public BigDecimal getSubtotal() {
        return total.add(getGratuity());
    }

    public void setTotal(String total) {
        this.total = new BigDecimal(total);
    }

    public void setTotal(double total) {
        this.total = new BigDecimal(total);
    }

    public void setGratuityBase(double base) {
        this.gratuityBase = new BigDecimal(base);
    }

    public String getGratuityString() {
        return format(getGratuity());
    }

    public String getSubtotalString() {
        return format(getSubtotal());
    }

    public String format(BigDecimal bd) {
        return NumberFormat.getCurrencyInstance().format(bd);
    }
}