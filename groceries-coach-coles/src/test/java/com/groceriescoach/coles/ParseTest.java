package com.groceriescoach.coles;


import com.groceriescoach.coles.domain.ColesProduct;
import com.groceriescoach.coles.service.ColesService;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ParseTest {

    private static final Logger logger = LoggerFactory.getLogger(ParseTest.class);

    @Test
    public void parseColesHtml() throws IOException {
//        File colesHtml = new File("C:/projects/groceries-coach/groceries-coach-coles/src/test/resources/laundry-powder.html");
        File colesHtml = new File("C:/projects/groceries-coach/groceries-coach-coles/src/test/resources/crinkle-cut.html");
        assertTrue(colesHtml.exists());

        Document doc = Jsoup.parse(colesHtml, "UTF-8");

        Elements searchResults = doc.select(".wrapper.clearfix");

        for (Element productElement : searchResults) {
            ColesProduct colesProduct = ColesProduct.fromProductElement(productElement);
            logger.debug("ColesProduct = {}", colesProduct);
            Product product = colesProduct.toGroceriesCoachProduct();
            logger.debug("GroceriesCoachProduct = {}", product);
        }

        assertNotNull(doc);
    }



    @Test
    public void parse() throws IOException, InterruptedException, ExecutionException, TimeoutException {

        ColesService colesService = new ColesService();
        List<Product> colesProducts = colesService.search("milk", GroceriesCoachSortType.ProductName).get(10, TimeUnit.SECONDS);
        assertNotNull(colesProducts);
        logger.debug("{}", colesProducts);
    }

}
