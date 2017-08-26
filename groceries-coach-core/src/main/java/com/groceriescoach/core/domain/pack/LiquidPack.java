package com.groceriescoach.core.domain.pack;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.udojava.evalex.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils.formatCurrencyAmount;
import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeCurrencySymbols;
import static com.groceriescoach.core.domain.pack.LiquidPack.Unit.LITRE;
import static com.groceriescoach.core.domain.pack.LiquidPack.Unit.MILLILITRE;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class LiquidPack extends Pack {

    private static final long serialVersionUID = 925625131255694259L;

    private static final Logger logger = LoggerFactory.getLogger(LiquidPack.class);

    private Unit unit;

    @Override
    public boolean hasUnitPrice() {
        return true;
    }

    public static LiquidPack createPackage(String productName, Double price) {
        Double priceInCents = price * 100;
        LiquidPack liquidPack = null;

        String nameWorkingCopy = StringUtils.trimToEmpty(productName).toLowerCase();

        nameWorkingCopy = nameWorkingCopy.replaceAll("-", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("mls", "ml");
        nameWorkingCopy = nameWorkingCopy.replaceAll("ml", " ml");
        nameWorkingCopy = nameWorkingCopy.replaceAll("litres", "litre");
        nameWorkingCopy = nameWorkingCopy.replaceAll("litre", " l");
        nameWorkingCopy = nameWorkingCopy.replaceAll("(\\d)\\s*x\\s*(\\d)", "$1*$2");

        if (containsIgnoreCase(nameWorkingCopy, "ml")) {
            final String[] tokens = StringUtils.split(nameWorkingCopy);
            for (int i = 0; i < tokens.length - 1; i++) {
                if (StringUtils.equalsIgnoreCase("ml", tokens[i + 1])) {
                    try {
                        Expression expression = new Expression(tokens[i]);
                        BigDecimal result = expression.eval();

                        int packageSizeInt = result.intValue();
                        String packageSize = packageSizeInt + " ml";

                        liquidPack = new LiquidPack();
                        liquidPack.unit = MILLILITRE;
                        liquidPack.setPackSize(packageSize);
                        liquidPack.setPackSizeInt(packageSizeInt);
                        int packageSizeInMls = packageSizeInt;
                        liquidPack.setUnitPrice(priceInCents / packageSizeInMls);
                        liquidPack.setUnitSize("ml");
                        break;
                    } catch (Exception e) {
                        logger.debug("Unable to extract liquid pack: {}", productName);
                    }
                }
            }
        }

        if (liquidPack == null) {

            nameWorkingCopy = nameWorkingCopy.replaceAll("l", " l");
            if (containsIgnoreCase(nameWorkingCopy, "l")) {
                final String[] tokens = StringUtils.split(nameWorkingCopy);
                for (int i = 0; i < tokens.length - 1; i++) {
                    if (StringUtils.equalsIgnoreCase("l", tokens[i + 1])) {
                        try {
                            Expression expression = new Expression(tokens[i]);
                            BigDecimal result = expression.eval();

                            int packageSizeInt = result.intValue();
                            String packageSize = packageSizeInt + " l";

                            liquidPack = new LiquidPack();
                            liquidPack.unit = LITRE;
                            liquidPack.setPackSize(packageSize);
                            liquidPack.setPackSizeInt(packageSizeInt);

                            liquidPack.setUnitPrice(priceInCents / (packageSizeInt * 1000));
                            liquidPack.setUnitSize("l");
                            liquidPack.updateUnitPriceStr();
                            break;
                        } catch (Exception e) {
                            logger.debug("Unable to extract solid pack: {}", productName);
                        }
                    }
                }
            }
        }

        if (liquidPack != null) {
            liquidPack.updateUnitPriceStr();
        }

        return liquidPack;
    }

    public static LiquidPack createPackage(String packageSize, String unitPriceStr, Double price) {
        LiquidPack liquidPack = null;
        LiquidPack.Unit unit = null;
        unitPriceStr = StringUtils.trimToEmpty(unitPriceStr).toLowerCase();
        List<String> unitPriceElements = new ArrayList<>();

        Double unitCostInCents = 0D;
        Double unitSizeInMilliLitres = 0D;

        unitPriceStr = unitPriceStr.replaceAll("mls", "ml");

        if (containsIgnoreCase(unitPriceStr, "ml") || containsIgnoreCase(unitPriceStr, "l")) {
            unitPriceElements.addAll(Arrays.asList(StringUtils.split(unitPriceStr)));
            if (CollectionUtils.isNotEmpty(unitPriceElements) && unitPriceElements.size() == 3) {
                unitPriceElements.remove(1);
                String unitCostStr = StringUtils.trimToEmpty(unitPriceElements.get(0)).toLowerCase();

                if (unitCostStr.contains("$")) {
                    unitCostInCents = Double.parseDouble(removeCurrencySymbols(unitCostStr)) * 100;
                }

                String unitSize = StringUtils.trimToEmpty(unitPriceElements.get(1)).toLowerCase();
                if (unitSize.endsWith("ml")) {
                    unitSizeInMilliLitres = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll("ml", "")));
                    unit = MILLILITRE;
                } else if (unitSize.endsWith("l")) {
                    unitSizeInMilliLitres = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll("l", ""))) * 1000;
                    unit = LITRE;
                }
            }

            liquidPack = new LiquidPack();
            liquidPack.setUnit(unit);
            liquidPack.setUnitPrice(unitCostInCents / unitSizeInMilliLitres);
            liquidPack.setPackSize(packageSize);
            liquidPack.updateUnitPriceStr();
        }

        return liquidPack;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    private void updateUnitPriceStr() {
        switch (unit) {
            case MILLILITRE:
                if (getUnitPrice() < 100) {
                    setUnitPriceStr(formatCurrencyAmount(getUnitPrice() * 100) + " per 100ml");
                } else {
                    setUnitPriceStr(formatCurrencyAmount(getUnitPrice()) + " per ml");
                }
                break;
            case LITRE:
                setUnitPriceStr(formatCurrencyAmount(getUnitPrice() * 1000) + " per litre");
                break;
        }
    }


    enum Unit {
        MILLILITRE, LITRE
    }

}
