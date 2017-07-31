package com.groceriescoach.core.domain.pack;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MultiPackTest {

    @Test
    public void createMultiPackForPackOf87Nappies() {
        String productName = "BabyLove Cosifit Jumbo Crawler Nappies 87 Pack";
        MultiPack multiPack = MultiPack.createPackage(productName, 23.99);
        assertThat(multiPack, is(notNullValue()));
        assertThat(multiPack.getPackSize(), is("87 Pack"));
    }

    @Test
    public void createMultiPackForPackOf80Wipes() {
        String productName = "Johnson's Baby Wipes Fragrance Free Refill - 80 Wipes";
        MultiPack multiPack = MultiPack.createPackage(productName, 23.99);
        assertThat(multiPack, is(notNullValue()));
        assertThat(multiPack.getPackSize(), is("80 Pack"));
    }

    @Test
    public void createMultiPackForPackOf80WipesWithWipesAppearingTwice() {
        String productName = "Gaia Bamboo Baby Wipes 80 Wipes";
        MultiPack multiPack = MultiPack.createPackage(productName, 23.99);
        assertThat(multiPack, is(notNullValue()));
        assertThat(multiPack.getPackSize(), is("80 Pack"));
    }

    @Test
    public void createMultiPackForPackOf240WipesByEvaluatingAnExpression() {
        String productName = "Huggies Fragrance Free 3x80 Wipes";
        MultiPack multiPack = MultiPack.createPackage(productName, 23.99);
        assertThat(multiPack, is(notNullValue()));
        assertThat(multiPack.getPackSize(), is("240 Pack"));
    }

    @Test
    public void createMultiPackForARefillPackOf80Wipes() {
        String productName = "Curash Baby Wipes Fragrance Free Refill 80";
        MultiPack multiPack = MultiPack.createPackage(productName, 23.99);
        assertThat(multiPack, is(notNullValue()));
        assertThat(multiPack.getPackSizeInt(), is(80));
    }


    @Test
    public void createMultiPackForPackOf240WipesByEvaluatingAnExpressionWithSpaces() {
        String productName = "Babylove Wipes 80 x 3";
        MultiPack multiPack = MultiPack.createPackage(productName, 23.99);
        assertThat(multiPack, is(notNullValue()));
        assertThat(multiPack.getPackSizeInt(), is(240));
    }

}
