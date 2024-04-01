package com.example.exchange_rates_bot.service.impl;


import com.example.exchange_rates_bot.client.CbrClient;
import com.example.exchange_rates_bot.exception.ServiceException;
import com.example.exchange_rates_bot.service.ExchangeRatesService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;


@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final String USD_XPATH = "/rates/department/currency[10]/sale";
    private static final String EUR_XPATH = "/rates/department/currency[2]/sale";

    private final CbrClient client;

    public ExchangeRatesServiceImpl(CbrClient client) {
        this.client = client;
    }

    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, USD_XPATH);
    }

    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, EUR_XPATH);
    }

    @Nullable
    private static String extractCurrencyValueFromXML(String xml, String xpathExpression)
            throws ServiceException {
        var source = new InputSource(new StringReader(xml));
        try {
            var xpath = XPathFactory.newInstance().newXPath();
            Document document = (Document) xpath.evaluate("/", source, XPathConstants.NODE);

            return xpath.evaluate(xpathExpression, document);
        } catch (XPathExpressionException e) {
            throw new ServiceException("Не удалось распарсить XML", e);
        }
    }
}
