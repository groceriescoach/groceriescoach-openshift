package com.groceriescoach.core.domain.pack;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.udojava.evalex.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils.formatCurrencyAmount;
import static com.groceriescoach.core.domain.pack.SolidPack.Unit.GRAMS;
import static com.groceriescoach.core.domain.pack.SolidPack.Unit.KILOGRAMS;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class SolidPack extends Pack {

    private static final long serialVersionUID = -2168271643181032912L;

    private static final Logger logger = LoggerFactory.getLogger(SolidPack.class);

    private Unit unit;

    @Override
    public boolean hasUnitPrice() {
        return true;
    }

    public static SolidPack createPackage(String productName, Double price) {
        Double priceInCents = price * 100;
        SolidPack solidPack = null;


        String nameWorkingCopy = StringUtils.trimToEmpty(productName).toLowerCase();

        nameWorkingCopy = nameWorkingCopy.replaceAll("-", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("kg", " kg");

        if (containsIgnoreCase(nameWorkingCopy, "kg")) {
            final String[] tokens = StringUtils.split(nameWorkingCopy);
            for (int i = 0; i < tokens.length - 1; i++) {
                if (StringUtils.equalsIgnoreCase("kg", tokens[i + 1])) {
                    try {
                        Expression expression = new Expression(tokens[i]);
                        BigDecimal result = expression.eval();

                        int packageSizeInt = result.intValue();
                        String packageSize = packageSizeInt + " kg";

                        solidPack = new SolidPack();
                        solidPack.unit = KILOGRAMS;
                        solidPack.setPackSize(packageSize);
                        solidPack.setPackSizeInt(packageSizeInt);
                        int packageSizeInGrams = packageSizeInt * 1000;
                        solidPack.setUnitPrice(priceInCents / packageSizeInGrams);
                        solidPack.setUnitSize("grams");
                        break;
                    } catch (Exception e) {
                        logger.debug("Unable to extract solid pack: {}", productName);
                    }
                }
            }
        }

        if (solidPack == null) {

            nameWorkingCopy = nameWorkingCopy.replaceAll("g", " g");
            if (containsIgnoreCase(nameWorkingCopy, "g")) {
                final String[] tokens = StringUtils.split(nameWorkingCopy);
                for (int i = 0; i < tokens.length - 1; i++) {
                    if (StringUtils.equalsIgnoreCase("g", tokens[i + 1])) {
                        try {
                            Expression expression = new Expression(tokens[i]);
                            BigDecimal result = expression.eval();

                            int packageSizeInt = result.intValue();
                            String packageSize = packageSizeInt + " g";

                            solidPack = new SolidPack();
                            solidPack.unit = GRAMS;
                            solidPack.setPackSize(packageSize);
                            solidPack.setPackSizeInt(packageSizeInt);

                            solidPack.setUnitPrice(priceInCents / packageSizeInt);
                            solidPack.setUnitSize("grams");
                            solidPack.setUnitPriceStr(formatCurrencyAmount(solidPack.getUnitPrice()));
                            break;
                        } catch (Exception e) {
                            logger.debug("Unable to extract solid pack: {}", productName);
                        }
                    }
                }
            }
        }

        if (solidPack != null) {
            solidPack.updateUnitPriceStr();
        }

        return solidPack;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    private void updateUnitPriceStr() {
        switch (getUnit()) {
            case GRAMS:
                setUnitPriceStr(formatCurrencyAmount(getUnitPrice()) + " per gram");
                break;
            case KILOGRAMS:
                setUnitPriceStr(formatCurrencyAmount(getUnitPrice() * 1000) + " per kg");
                break;
        }
    }


    enum Unit {
        GRAMS, KILOGRAMS
    }
}
