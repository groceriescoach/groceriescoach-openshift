package com.groceriescoach.core.domain.pack;

import org.junit.Test;

import static com.groceriescoach.core.domain.pack.SolidPack.Unit.GRAMS;
import static com.groceriescoach.core.domain.pack.SolidPack.Unit.KILOGRAMS;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SolidPackTest {



    @Test
    public void solidPackCreationShouldExtractKgWeightCorrectly() {
        SolidPack solidPack = SolidPack.createPackage("Fab Frangipani Laundry Powder 15kg", 40.00);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getPackSize(), is("15 kg"));
    }


    @Test
    public void solidPackCreationShouldExtractGramWeightCorrectly() {
        SolidPack solidPack = SolidPack.createPackage("Ajax Cleansing Powder 595g", 2.99);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getPackSize(), is("595 g"));
    }


    @Test
    public void solidPackUnitShouldBeGramsForPacksLessThanAKilo() {
        SolidPack solidPack = SolidPack.createPackage("Ajax Cleansing Powder 595g", 2.99);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getUnit(), is(GRAMS));
    }


    @Test
    public void solidPackUnitShouldBeGramsForAOneKiloPack() {
        SolidPack solidPack = SolidPack.createPackage("Ajax Cleansing Powder 1 kg", 2.99);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getUnit(), is(KILOGRAMS));
    }


    @Test
    public void solidPackUnitShouldBeKiloGramsForPacksGreaterThanOneKilo() {
        SolidPack solidPack = SolidPack.createPackage("Ajax Cleansing Powder 1.5 kg", 2.99);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getUnit(), is(KILOGRAMS));
    }

    @Test
    public void unitPriceOfSolidPackGreaterThanOneKiloShouldBeCentsPerGram() {
        SolidPack solidPack = SolidPack.createPackage("Ajax Cleansing Powder 10 kg", 100.00);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getUnitPrice(), is(1.0));
    }

    @Test
    public void unitPriceOfSolidPackLessThanOneKiloShouldBeCentsPerGram() {
        SolidPack solidPack = SolidPack.createPackage("Ajax Cleansing Powder 500g", 50.00);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getUnitPrice(), is(10.0));
    }

    @Test
    public void unitPriceStringOfSolidPackLessThanOneKiloShouldBeCentsPerGram() {
        SolidPack solidPack = SolidPack.createPackage("Ajax Cleansing Powder 500g", 50.00);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getUnitPriceStr(), is("10.00 cents per gram"));
    }


    @Test
    public void unitPriceStringOfSolidGreaterThanOneKiloShouldBeCentsPerKiloGram() {
        SolidPack solidPack = SolidPack.createPackage("Ajax Cleansing Powder 10 kg", 100.00);
        assertThat(solidPack, is(notNullValue()));
        assertThat(solidPack.getUnitPriceStr(), is("$10.00 per kg"));
    }

}
