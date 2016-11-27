package com.rt7mobilereward.app;

/**
 * Created by MANIKANDAN_SETHURAJ on 2016-11-24.
 */

public interface PaymentForm {
    public String getCardNumber();
    public String getCvc();
    public int getExpMonth();
    public int getExpYear();
    public String getCurrency();
}
