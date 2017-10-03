package cz.fi.muni.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    @Mock
    private ExchangeRateTable exchangeRateTable;

    @InjectMocks
    private CurrencyConvertorImpl currencyConvertor;

    @Test
    public void testConvert() throws ExternalServiceFailureException{
        when(exchangeRateTable.getExchangeRate(any(Currency.class), any(Currency.class))).thenReturn(new BigDecimal("25"));
        assertEquals(new BigDecimal("25"),currencyConvertor.convert(Currency.getInstance(Locale.US),Currency.getInstance("CZK"), BigDecimal.ONE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        currencyConvertor.convert(null, Currency.getInstance("EUR"), new BigDecimal("10.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {
        currencyConvertor.convert(Currency.getInstance("EUR"), null, new BigDecimal("10.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() {
        currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("EUR"), null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() {
        currencyConvertor.convert(Currency.getInstance(Locale.CHINA), Currency.getInstance(Locale.CANADA), new BigDecimal("10.00"));
    }

    @Test(expected = RuntimeException.class)
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(any(Currency.class), any(Currency.class))).thenThrow(ExternalServiceFailureException.class);
        currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("EUR"), new BigDecimal("1"));
    }

}
