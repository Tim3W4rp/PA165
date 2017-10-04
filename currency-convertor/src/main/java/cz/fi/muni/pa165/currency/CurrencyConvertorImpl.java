package cz.fi.muni.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);
    private final ExchangeRateTable exchangeRateTable;
    //private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace(String.format("Calling convert(%s, %s, %s)"),sourceCurrency, targetCurrency, sourceAmount);

        if (sourceCurrency == null) {
            throw new IllegalArgumentException("Source currency is not set.");
        }

        if (targetCurrency == null) {
            throw new IllegalArgumentException("Target currency is not set.");
        }

        if (sourceAmount == null) {
            throw new IllegalArgumentException("Source amount is not set.");
        }

        try {
            BigDecimal exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency,targetCurrency);
            if (exchangeRate == null) {
                logger.warn(String.format("Unknown exchange rate with currencies: %s, %s."), sourceCurrency, targetCurrency);
                throw new UnknownExchangeRateException("You cannot convert these two currencies.");
            }

            return sourceAmount.multiply(new BigDecimal(exchangeRate.toString()));
        } catch (ExternalServiceFailureException e) {
            logger.error("Error connecting to exchange rate table - ", e);
            throw new RuntimeException("Some error in external service.");
        }
    }

}
