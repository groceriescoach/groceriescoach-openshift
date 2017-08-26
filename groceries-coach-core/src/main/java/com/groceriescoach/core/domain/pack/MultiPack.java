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
import static org.apache.commons.lang3.StringUtils.containsAny;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class MultiPack extends Pack {
    private static final long serialVersionUID = -8078907127512825078L;

    private static final Logger logger = LoggerFactory.getLogger(MultiPack.class);

    @Override
    public boolean hasUnitPrice() {
        return true;
    }


    public static MultiPack createPackage(String productName, Double price) {
        Double priceInCents = price * 100;
        String nameWorkingCopy = StringUtils.trimToEmpty(productName).toLowerCase();

        nameWorkingCopy = nameWorkingCopy.replaceAll("pk", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("pcs", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("pieces", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("piece", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("wipes", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("sheets", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("refill", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("tub", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("'s", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("’s", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("-", " ");
        nameWorkingCopy = nameWorkingCopy.replaceAll("mega", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("bulk", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("bundle", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("box", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("convenience", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("value", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("\\.\\.\\.", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("\\(", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("\\)", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("travel", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("pack", " pack ");
        nameWorkingCopy = nameWorkingCopy.replaceAll("(\\d)\\s*x\\s*(\\d)", "$1*$2");

        if (!StringUtils.endsWith(StringUtils.trimToEmpty(nameWorkingCopy), "pack")) {
            nameWorkingCopy = StringUtils.trimToEmpty(nameWorkingCopy) + " pack";
        }

        if (containsIgnoreCase(nameWorkingCopy, "pack")) {

            final String[] tokens = StringUtils.split(nameWorkingCopy);
            for (int i = 0; i < tokens.length - 1; i++) {
                if (StringUtils.equalsIgnoreCase("pack", tokens[i + 1])) {
                    try {
                        Expression expression = new Expression(tokens[i]);
                        BigDecimal result = expression.eval();

                        int packageSizeInt = result.intValue();
                        String packageSize = packageSizeInt + " Pack";

                        MultiPack multiPack = new MultiPack();
                        multiPack.setPackSize(packageSize);
                        multiPack.setPackSizeInt(packageSizeInt);

                        multiPack.setUnitPrice(priceInCents / packageSizeInt);
                        multiPack.setUnitPriceStr(formatCurrencyAmount(multiPack.getUnitPrice()) + " each");
                        multiPack.setUnitSize("Each");
                        return multiPack;
                    } catch (Exception e) {
                        logger.debug("Unable to extract multipack: {}", productName, e);
                    }
                }
            }
        }

        return null;
    }

    public static MultiPack createPackage(String packageSize, String unitPriceStr, Double price) {
        MultiPack multiPack = null;
        unitPriceStr = StringUtils.trimToEmpty(unitPriceStr).toLowerCase();
        List<String> unitPriceElements = new ArrayList<>();
        //  "$0.48 per 100Ea"

        Double unitCostInCents = 0D;
        int unitSize = 0;
        String unitType = "";

        if (containsAny(unitPriceStr, "ea", "ss")) {
            unitPriceElements.addAll(Arrays.asList(StringUtils.split(unitPriceStr)));
            if (CollectionUtils.isNotEmpty(unitPriceElements) && unitPriceElements.size() == 3) {
                unitPriceElements.remove(1);
                String unitCostStr = StringUtils.trimToEmpty(unitPriceElements.get(0)).toLowerCase();

                if (unitCostStr.contains("$")) {
                    unitCostInCents = Double.parseDouble(removeCurrencySymbols(unitCostStr)) * 100;
                }

                String unitSizeStr = StringUtils.trimToEmpty(unitPriceElements.get(1));
                if (unitSizeStr.endsWith("ea")) {
                    unitSizeStr = StringUtils.trimToEmpty(unitSizeStr.replaceAll("ea", ""));
                } else if (unitSizeStr.endsWith("ss")) {
                    unitType = "sheets";
                    unitSizeStr = StringUtils.trimToEmpty(unitSizeStr.replaceAll("ss", ""));
                }

                unitSize = Integer.parseInt(unitSizeStr);

                multiPack = new MultiPack();
                multiPack.setUnitPrice(unitCostInCents / unitSize);

                String multiPackUnitPriceStr = formatCurrencyAmount(unitCostInCents) + "";
                if (unitType.equalsIgnoreCase("sheets")) {
                    if (unitSize == 1) {
                        multiPackUnitPriceStr += " per sheet";
                    } else {
                        multiPackUnitPriceStr += " per " + unitSize + " sheets";
                    }
                } else {
                    if (unitSize == 1) {
                        multiPackUnitPriceStr += " each";
                    } else {
                        multiPackUnitPriceStr += " per " + unitSize;
                    }
                }

                multiPack.setUnitPriceStr(multiPackUnitPriceStr);
                multiPack.setUnitSize(unitSizeStr);
                multiPack.setPackSize(packageSize);
            }
        }

        return multiPack;
    }
}
