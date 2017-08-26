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
import static com.groceriescoach.core.domain.pack.LengthPack.Unit.METRE;
import static com.groceriescoach.core.domain.pack.LengthPack.Unit.MILLIMETRE;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class LengthPack extends Pack {

    private static final long serialVersionUID = 5037618998842138161L;

    private static final Logger logger = LoggerFactory.getLogger(LengthPack.class);

    private Unit unit;
    
    @Override
    boolean hasUnitPrice() {
        return true;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public static LengthPack createPackage(String productName, Double price) {
        Double priceInCents = price * 100;
        LengthPack lengthPack = null;

        String nameWorkingCopy = StringUtils.trimToEmpty(productName).toLowerCase();

        nameWorkingCopy = nameWorkingCopy.replaceAll("-", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("mms", "mm");
        nameWorkingCopy = nameWorkingCopy.replaceAll("mm", " mm");
        nameWorkingCopy = nameWorkingCopy.replaceAll("meter", "metre");
        nameWorkingCopy = nameWorkingCopy.replaceAll("metres", "metre");
        nameWorkingCopy = nameWorkingCopy.replaceAll("metre", "m");
        nameWorkingCopy = nameWorkingCopy.replaceAll("(\\d)\\s*x\\s*(\\d)", "$1*$2");

        if (containsIgnoreCase(nameWorkingCopy, "mm")) {
            final String[] tokens = StringUtils.split(nameWorkingCopy);
            for (int i = 0; i < tokens.length - 1; i++) {
                if (StringUtils.equalsIgnoreCase("mm", tokens[i + 1])) {
                    try {
                        Expression expression = new Expression(tokens[i]);
                        BigDecimal result = expression.eval();

                        int packageSizeInt = result.intValue();
                        String packageSize = packageSizeInt + " mm";

                        lengthPack = new LengthPack();
                        lengthPack.unit = MILLIMETRE;
                        lengthPack.setPackSize(packageSize);
                        lengthPack.setPackSizeInt(packageSizeInt);
                        int packageSizeInMms = packageSizeInt;
                        lengthPack.setUnitPrice(priceInCents / packageSizeInMms);
                        lengthPack.setUnitSize("mm");
                        break;
                    } catch (Exception e) {
                        logger.debug("Unable to extract length pack: {}", productName);
                    }
                }
            }
        }

        if (lengthPack == null) {

            nameWorkingCopy = nameWorkingCopy.replaceAll("m", " m");
            if (containsIgnoreCase(nameWorkingCopy, "m")) {
                final String[] tokens = StringUtils.split(nameWorkingCopy);
                for (int i = 0; i < tokens.length - 1; i++) {
                    if (StringUtils.equalsIgnoreCase("m", tokens[i + 1])) {
                        try {
                            Expression expression = new Expression(tokens[i]);
                            BigDecimal result = expression.eval();

                            int packageSizeInt = result.intValue();
                            String packageSize = packageSizeInt + " m";

                            lengthPack = new LengthPack();
                            lengthPack.unit = METRE;
                            lengthPack.setPackSize(packageSize);
                            lengthPack.setPackSizeInt(packageSizeInt);

                            lengthPack.setUnitPrice(priceInCents / (packageSizeInt * 1000));
                            lengthPack.setUnitSize("l");
                            lengthPack.updateUnitPriceStr();
                            break;
                        } catch (Exception e) {
                            logger.debug("Unable to extract length pack: {}", productName);
                        }
                    }
                }
            }
        }

        if (lengthPack != null) {
            lengthPack.updateUnitPriceStr();
        }

        return lengthPack;
    }

    static LengthPack createPackage(String packageSize, String unitPriceStr, Double price) {
        LengthPack lengthPack = null;
        LengthPack.Unit unit = null;
        unitPriceStr = StringUtils.trimToEmpty(unitPriceStr).toLowerCase();
        List<String> unitPriceElements = new ArrayList<>();

        Double unitCostInCents = 0D;
        Double unitSizeInMilliLitres = 0D;

        if (containsIgnoreCase(unitPriceStr, "mm") || containsIgnoreCase(unitPriceStr, "m")) {
            unitPriceElements.addAll(Arrays.asList(StringUtils.split(unitPriceStr)));
            if (CollectionUtils.isNotEmpty(unitPriceElements) && unitPriceElements.size() == 3) {
                unitPriceElements.remove(1);
                String unitCostStr = StringUtils.trimToEmpty(unitPriceElements.get(0)).toLowerCase();

                if (unitCostStr.contains("$")) {
                    unitCostInCents = Double.parseDouble(removeCurrencySymbols(unitCostStr)) * 100;
                }

                String unitSize = StringUtils.trimToEmpty(unitPriceElements.get(1)).toLowerCase();
                if (unitSize.endsWith("mm")) {
                    unitSizeInMilliLitres = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll("mm", "")));
                    unit = MILLIMETRE;
                } else if (unitSize.endsWith("m")) {
                    unitSizeInMilliLitres = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll("m", ""))) * 1000;
                    unit = METRE;
                }
            }

            lengthPack = new LengthPack();
            lengthPack.setUnit(unit);
            lengthPack.setUnitPrice(unitCostInCents / unitSizeInMilliLitres);
            lengthPack.setPackSize(packageSize);
            lengthPack.updateUnitPriceStr();
        }

        return lengthPack;
    }

    private void updateUnitPriceStr() {
        switch (unit) {
            case MILLIMETRE:
                if (getUnitPrice() < 100) {
                    setUnitPriceStr(formatCurrencyAmount(getUnitPrice() * 100) + " per 100mm");
                } else {
                    setUnitPriceStr(formatCurrencyAmount(getUnitPrice()) + " per mm");
                }
                break;
            case METRE:
                setUnitPriceStr(formatCurrencyAmount(getUnitPrice() * 1000) + " per metre");
                break;
        }
    }

    enum Unit {
        MILLIMETRE, METRE
    }
}
