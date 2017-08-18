package com.groceriescoach.coles.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ProductJsonTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductJsonTest.class);

    @Test
    public void testMapping() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File file = new File("C:/projects/groceries-coach/groceries-coach-coles/src/test/resources/coles-product.json");
        Product product = mapper.reader().forType(Product.class).readValue(file);
        logger.debug("{}", product);
    }
}
