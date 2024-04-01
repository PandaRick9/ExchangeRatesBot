package com.example.exchange_rates_bot.service;

import com.example.exchange_rates_bot.exception.ServiceException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



public interface ExchangeRatesService {

    String getUSDExchangeRate() throws ServiceException;

    String getEURExchangeRate() throws ServiceException;

}