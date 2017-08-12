package com.groceriescoach.core.domain.pack;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.udojava.evalex.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils.formatCurrencyAmount;

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
        nameWorkingCopy = nameWorkingCopy.replaceAll("\\s*x\\s*", "*");


        if (!StringUtils.endsWith(StringUtils.trimToEmpty(nameWorkingCopy), "pack")) {
            nameWorkingCopy = StringUtils.trimToEmpty(nameWorkingCopy) + " pack";
        }

        if (StringUtils.containsIgnoreCase(nameWorkingCopy, "pack")) {

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
                        logger.error("Unable to extract multipack: {}", productName, e);
                    }
                }
            }
        }

        return null;
    }

}
