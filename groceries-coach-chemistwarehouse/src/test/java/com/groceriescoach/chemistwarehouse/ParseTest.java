package com.groceriescoach.chemistwarehouse;


import com.groceriescoach.chemistwarehouse.service.ChemistWarehouseOfflineService;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;

public class ParseTest {

    private static final Logger logger = LoggerFactory.getLogger(ParseTest.class);


    private static ChemistWarehouseOfflineService chemistWarehouseService = new ChemistWarehouseOfflineService();


    @Test
    public void parseChemistWarehouseHtml() throws IOException, InterruptedException, ExecutionException, TimeoutException {

        List<Product> products =
                chemistWarehouseService
                        .search("iodised salt", GroceriesCoachSortType.Price)
                        .get(10, TimeUnit.SECONDS);

        assertNotNull(products);
    }



    @Test
    public void parse() throws IOException, InterruptedException, ExecutionException, TimeoutException {

        ChemistWarehouseOfflineService chemistWarehouseOfflineService = new ChemistWarehouseOfflineService();
        List<Product> chemistWarehouseProducts =
                chemistWarehouseOfflineService
                        .search("laundry powder", GroceriesCoachSortType.ProductName)
                        .get(10, TimeUnit.SECONDS);
        assertNotNull(chemistWarehouseProducts);
        logger.debug("{}", chemistWarehouseProducts);
    }

}
