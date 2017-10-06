package com.groceriescoach.core.domain.pack;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.udojava.evalex.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils.formatCurrencyAmount;
import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeCurrencySymbols;
import static org.apache.commons.lang3.StringUtils.containsAny;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class MultiPack extends Pack {

    private static final long serialVersionUID = -8078907127512825078L;

    private static final Logger logger = LoggerFactory.getLogger(MultiPack.class);

    private MultiPack() {
    }

    private MultiPack(MultiPack multiPack) {
        super(multiPack);
    }

    @Override
    public boolean hasUnitPrice() {
        return true;
    }

    @Override
    protected void updateUnitPricesInQuantityPrice(GroceriesCoachProduct.QuantityPrice quantityPrice) {
        MultiPack multiPack = new MultiPack(this);
        multiPack.setPackSize(multiPack.getPackSize() * quantityPrice.getQuantity());
        multiPack.setPriceInCents(quantityPrice.getPrice() * 100);
        multiPack.calculateUnitPrice();
        quantityPrice.setUnitPriceStr(multiPack.getUnitPriceStr());
        quantityPrice.setUnitPrice(multiPack.getUnitPrice());

    }

    static MultiPack createPackage(String productName, Double price) {
        String nameWorkingCopy = StringUtils.trimToEmpty(productName).toLowerCase();

        nameWorkingCopy =
                StringUtils.replaceAll(
                        nameWorkingCopy,
                        new String[]{"pk", "pcs", "pieces", "piece", "wipes", "’s", "sheets", "'s", "refill", "tub"},
                        "pack");

        nameWorkingCopy = nameWorkingCopy.replaceAll("-", " ");

        nameWorkingCopy = StringUtils.removeAll(
                nameWorkingCopy,
                new String[]{"mega", "bulk", "bundle", "box", "convenience", "value", "\\.\\.\\.", "\\(", "\\)", "travel",}
        );

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

                        Double packageSize = result.doubleValue();
                        String packageSizeStr = packageSize + " Pack";

                        MultiPack multiPack = new MultiPack();
                        multiPack.setPackSizeStr(packageSizeStr);
                        multiPack.setPackSize(packageSize);
                        multiPack.setPriceInCents(price * 100);
                        multiPack.calculateUnitPrice();
                        return multiPack;
                    } catch (Exception e) {
                        logger.debug("Unable to extract multipack: {}", productName, e);
                    }
                }
            }
        }

        return null;
    }

    private void calculateUnitPrice() {
        Objects.requireNonNull(getPriceInCents());
        Objects.requireNonNull(getPackSize());
        setUnitPrice(getPriceInCents() / getPackSize());
        setUnitPriceStr(formatCurrencyAmount(getUnitPrice()) + " each");
        setUnitSize("Each");
    }

    static MultiPack createPackage(String packageSize, String unitPriceStr, Double price) {
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
                multiPack.setPriceInCents(price * 100);
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
                multiPack.setPackSizeStr(packageSize);
            }
        }

        return multiPack;
    }

}
