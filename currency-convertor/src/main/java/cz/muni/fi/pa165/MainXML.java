/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import java.math.BigDecimal;
import java.util.Currency;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author xjordan2
 */
public class MainXML {
    public static void main(String [] args) {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        CurrencyConvertor convertor = (CurrencyConvertor) context.getBean("currencyConvertor");
        System.out.print(convertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.ONE));
        
        
    }
    
}
