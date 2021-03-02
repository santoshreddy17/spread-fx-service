package com.fx.trade.app.resource;

import com.fx.trade.app.FxObjectMapperProvider;
import com.fx.trade.app.model.MidFxRates;
import com.fx.trade.app.resource.HttpResource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class HttpResourceTest {

    //To Test Api & Object mapping.
    @Test
    public void getMidFxRates() throws IOException, InterruptedException {
        HttpResource httpResource = new HttpResource(FxObjectMapperProvider.getObjectMapper());
        MidFxRates sgd = httpResource.getMidFxRates("SGD");
        Assert.assertNotNull(sgd);
        Assert.assertEquals("SGD",sgd.getBaseCurrency());
        Assert.assertFalse(sgd.getFxRateMap().isEmpty());
        Assert.assertNotNull(sgd.getLocalDate());
    }

}