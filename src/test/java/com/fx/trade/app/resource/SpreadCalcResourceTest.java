package com.fx.trade.app.resource;

import com.fx.trade.app.FxObjectMapperProvider;
import com.fx.trade.app.exception.ResourceException;
import com.fx.trade.app.model.MidFxRates;
import com.fx.trade.app.model.PricingTier;
import com.fx.trade.app.model.UserRates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

public class SpreadCalcResourceTest {

    @InjectMocks
    private SpreadCalcResource spreadCalcResource;


    @Test
    public void getUserRatesA() throws ResourceException, IOException, InterruptedException {
        HttpResource httpResource = Mockito.mock(HttpResource.class);
        spreadCalcResource = new SpreadCalcResource(httpResource, FxObjectMapperProvider.getObjectMapper());
        final MidFxRates midFxRates = new MidFxRates();
        midFxRates.setBaseCurrency("blah");
        final Map<String, BigDecimal> map = new HashMap<>();
        map.put("HKD",BigDecimal.ONE);
        map.put("USD",BigDecimal.TEN);
        map.put("MYR",new BigDecimal("0.5"));
        midFxRates.setFxRateMap(map);
        Mockito.when(httpResource.getMidFxRates(any(String.class))).thenReturn(midFxRates);
        final UserRates userRates = spreadCalcResource.getUserRates(PricingTier.A);
        Assert.assertNotNull(userRates);
        Assert.assertEquals(3,userRates.getCurrencyUserRates().size());
        Assert.assertTrue(checkAskPrice(userRates, "USD", "10.150"));
        Assert.assertTrue(checkAskPrice(userRates, "HKD", "1.015"));
        Assert.assertTrue(checkAskPrice(userRates, "MYR", "0.507"));

        Assert.assertTrue(checkBidPrice(userRates, "USD", "9.850"));
        Assert.assertTrue(checkBidPrice(userRates, "HKD", "0.985"));
        Assert.assertTrue(checkBidPrice(userRates, "MYR", "0.492"));
    }

    @Test
    public void getUserRatesB() throws ResourceException, IOException, InterruptedException {
        HttpResource httpResource = Mockito.mock(HttpResource.class);
        spreadCalcResource = new SpreadCalcResource(httpResource, FxObjectMapperProvider.getObjectMapper());
        final MidFxRates midFxRates = new MidFxRates();
        midFxRates.setBaseCurrency("blah");
        final Map<String, BigDecimal> map = new HashMap<>();
        map.put("HKD",BigDecimal.ONE);
        map.put("USD",BigDecimal.TEN);
        map.put("MYR",new BigDecimal("0.5"));
        midFxRates.setFxRateMap(map);
        Mockito.when(httpResource.getMidFxRates(any(String.class))).thenReturn(midFxRates);
        final UserRates userRates = spreadCalcResource.getUserRates(PricingTier.B);
        Assert.assertNotNull(userRates);
        Assert.assertEquals(3,userRates.getCurrencyUserRates().size());
        Assert.assertTrue(checkAskPrice(userRates, "USD", "10.350"));
        Assert.assertTrue(checkAskPrice(userRates, "HKD", "1.035"));
        Assert.assertTrue(checkAskPrice(userRates, "MYR", "0.517"));

        Assert.assertTrue(checkBidPrice(userRates, "USD", "9.650"));
        Assert.assertTrue(checkBidPrice(userRates, "HKD", "0.965"));
        Assert.assertTrue(checkBidPrice(userRates, "MYR", "0.482"));
    }

    @Test
    public void getUserRatesC() throws ResourceException, IOException, InterruptedException {
        HttpResource httpResource = Mockito.mock(HttpResource.class);
        spreadCalcResource = new SpreadCalcResource(httpResource, FxObjectMapperProvider.getObjectMapper());
        final MidFxRates midFxRates = new MidFxRates();
        midFxRates.setBaseCurrency("blah");
        final Map<String, BigDecimal> map = new HashMap<>();
        map.put("HKD",BigDecimal.ONE);
        map.put("USD",BigDecimal.TEN);
        map.put("MYR",new BigDecimal("0.5"));
        midFxRates.setFxRateMap(map);
        Mockito.when(httpResource.getMidFxRates(any(String.class))).thenReturn(midFxRates);
        final UserRates userRates = spreadCalcResource.getUserRates(PricingTier.C);
        Assert.assertNotNull(userRates);
        Assert.assertEquals(3,userRates.getCurrencyUserRates().size());
        Assert.assertTrue(checkAskPrice(userRates, "USD", "10.400"));
        Assert.assertTrue(checkAskPrice(userRates, "HKD", "1.040"));
        Assert.assertTrue(checkAskPrice(userRates, "MYR", "0.520"));

        Assert.assertTrue(checkBidPrice(userRates, "USD", "9.600"));
        Assert.assertTrue(checkBidPrice(userRates, "HKD", "0.960"));
        Assert.assertTrue(checkBidPrice(userRates, "MYR", "0.480"));
    }


    @Test(expected = ResourceException.class)
    public void getUserRatesException() throws ResourceException, IOException, InterruptedException {
        HttpResource httpResource = Mockito.mock(HttpResource.class);
        spreadCalcResource = new SpreadCalcResource(httpResource, FxObjectMapperProvider.getObjectMapper());
        Mockito.when(httpResource.getMidFxRates(any(String.class))).thenThrow(new IOException());
        final UserRates userRates = spreadCalcResource.getUserRates(PricingTier.A);
    }

    private boolean checkAskPrice(UserRates userRates, String currency, String val) {
        return userRates.getCurrencyUserRates().get(currency).getAskPrice().setScale(3, RoundingMode.HALF_DOWN).compareTo(new BigDecimal(val))==0;
    }

    private boolean checkBidPrice(UserRates userRates, String currency, String val) {
        return userRates.getCurrencyUserRates().get(currency).getBidPrice().setScale(3, RoundingMode.HALF_DOWN).compareTo(new BigDecimal(val))==0;
    }
}