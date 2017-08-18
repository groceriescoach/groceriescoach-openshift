package com.groceriescoach.woolworths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groceriescoach.woolworths.domain.WoolworthsProduct;
import com.groceriescoach.woolworths.domain.WoolworthsSearchResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonMappingTest {

    private static final Logger logger = LoggerFactory.getLogger(JsonMappingTest.class);


    @Test
    public void testMapping() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File file = new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/cadbury.json");
        WoolworthsSearchResult searchResult = mapper.reader().forType(WoolworthsSearchResult.class).readValue(file);
        logger.debug("{}", searchResult);
    }


    @Test
    public void parseOneFile() throws IOException {

        File files[] = new File[]{
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/appletiser.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/cadburys.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/asian-tonight.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/bicarb-soda.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/applaws.json")
        };
        ObjectMapper mapper = new ObjectMapper();

        for (File file : files) {
            WoolworthsSearchResult searchResult = mapper.reader().forType(WoolworthsSearchResult.class).readValue(file);
            List<WoolworthsProduct> products = searchResult.toProducts("test");
            logger.debug("{}", products);
        }
    }

    @Test
    public void testParsing() throws IOException {

        File files[] = new File[]{
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/bicarb-soda.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/blueberry.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/chips.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/coke.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/deli-chips.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/iodised-salt.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/laundry-powder.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/redrock-deli.json"),
                new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/wrigleys-mints.json")
        };
        ObjectMapper mapper = new ObjectMapper();

        for (File file : files) {
            WoolworthsSearchResult searchResult = mapper.reader().forType(WoolworthsSearchResult.class).readValue(file);
            List<WoolworthsProduct> products = searchResult.toProducts("test");
            logger.debug("{}", products);
        }
    }
}
