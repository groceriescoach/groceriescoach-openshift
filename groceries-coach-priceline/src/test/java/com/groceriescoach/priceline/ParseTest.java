package com.groceriescoach.priceline;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.priceline.service.PricelineOfflineService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;

public class ParseTest {

    private static final Logger logger = LoggerFactory.getLogger(ParseTest.class);


    private static PricelineOfflineService pricelineService = new PricelineOfflineService();


    @Test
    public void parsePricelineHtml() throws IOException, InterruptedException, TimeoutException, ExecutionException {
        Future<List<Product>> listFuture = pricelineService.search("ibuprofen", GroceriesCoachSortType.Price);
        List<Product> products = listFuture.get(10, TimeUnit.SECONDS);
        assertNotNull(products);
        listFuture = pricelineService.search("olay moisturising cream", GroceriesCoachSortType.Price);
        products = listFuture.get(10, TimeUnit.SECONDS);
        assertNotNull(products);

    }



    @Test
    public void parse() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        PricelineOfflineService pricelineOfflineService = new PricelineOfflineService();
        Future<List<Product>> listFuture = pricelineOfflineService.search("ibuprofen", GroceriesCoachSortType.ProductName);
        List<Product> products = listFuture.get(10, TimeUnit.SECONDS);
        assertNotNull(products);
        logger.debug("{}", products);
    }

}
