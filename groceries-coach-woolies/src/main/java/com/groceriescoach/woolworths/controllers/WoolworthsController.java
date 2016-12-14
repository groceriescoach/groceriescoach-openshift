package com.groceriescoach.woolworths.controllers;

import com.groceriescoach.woolworths.domain.WoolworthsSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class WoolworthsController {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WoolworthsController.class);

    @Autowired
    public WoolworthsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = ApiUrls.Woolworths.SEARCH, method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<WoolworthsSearchResult> search(@RequestParam(value = ApiUrls.Woolworths.KEYWORDS) String keywords) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://www2.woolworthsonline.com.au/apis/ui/Search/products")
                .queryParam("IsSpecial", "false")
                .queryParam("PageNumber", "1")
                .queryParam("PageSize", "20")
                .queryParam("SearchTerm", keywords)
                .queryParam("SortType", "CUPAsc");

        WoolworthsSearchResult searchResult = restTemplate.getForObject(builder.build().encode().toUri(), WoolworthsSearchResult.class);

        return ResponseEntity.ok(searchResult);
    }

}
