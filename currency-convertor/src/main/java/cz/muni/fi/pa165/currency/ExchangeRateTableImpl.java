/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;

/**
 *
 * @author xjordan2
 */
public class ExchangeRateTableImpl implements ExchangeRateTable {

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency == Currency.getInstance("EUR") && targetCurrency == Currency.getInstance("CZK")) {
            return new BigDecimal(27);
        }
        return null;
    }
    
    
}
