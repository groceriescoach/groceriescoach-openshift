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

import static com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils.formatCurrencyAmount;
import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeCurrencySymbols;
import static com.groceriescoach.core.domain.pack.SolidPack.Unit.GRAMS;
import static com.groceriescoach.core.domain.pack.SolidPack.Unit.KILOGRAMS;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class SolidPack extends Pack {

    private static final long serialVersionUID = -2168271643181032912L;

    private Unit unit;

    private Double packageSizeInGrams;

    private static final Logger logger = LoggerFactory.getLogger(SolidPack.class);

    private SolidPack() {
    }

    private SolidPack(SolidPack solidPack) {
        super(solidPack);
        this.unit = solidPack.getUnit();
        this.packageSizeInGrams = solidPack.getPackageSizeInGrams();
    }

    @Override
    public boolean hasUnitPrice() {
        return true;
    }

    private static SolidPack createBasicPackage(String packageInfo) {
        SolidPack solidPack = null;
        Unit selectedUnit = null;
        Double packageSize = null;

        String nameWorkingCopy = StringUtils.trimToEmpty(packageInfo).toLowerCase();

        nameWorkingCopy = nameWorkingCopy.replaceAll("-", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("g\\.", "g");
        nameWorkingCopy = nameWorkingCopy.replaceAll("(\\d)\\s*x\\s*(\\d)", "$1*$2");

        List<Unit> units = Arrays.asList(KILOGRAMS, GRAMS);

        for (Unit unit : units) {
            if (packageSize == null) {
                if (containsIgnoreCase(nameWorkingCopy, unit.getSymbol())) {
                    nameWorkingCopy = nameWorkingCopy.replaceAll(unit.getSymbol(), " " + unit.getSymbol());
                    final String[] tokens = StringUtils.split(nameWorkingCopy);
                    for (int i = 0; i < tokens.length - 1; i++) {
                        if (StringUtils.equalsIgnoreCase(unit.getSymbol(), tokens[i + 1])) {
                            try {
                                Expression expression = new Expression(tokens[i]);
                                BigDecimal result = expression.eval();
                                selectedUnit = unit;
                                packageSize = result.doubleValue();
                                break;
                            } catch (Exception e) {
                                logger.debug("Unable to extract solid pack: {}", packageInfo);
                            }
                        }
                    }
                }
            }
        }

        if (packageSize != null && selectedUnit != null) {
            solidPack = new SolidPack();
            solidPack.setUnit(selectedUnit);
            solidPack.setPackSize(packageSize);
            selectedUnit.updatePackageSizeInGrams(solidPack);
        }

        return solidPack;
    }

    @Override
    protected void updateUnitPricesInQuantityPrice(GroceriesCoachProduct.QuantityPrice quantityPrice) {
        SolidPack solidPack = new SolidPack(this);
        solidPack.setPackageSizeInGrams(solidPack.packageSizeInGrams * quantityPrice.getQuantity());
        solidPack.setPriceInCents(quantityPrice.getPrice() * 100);
        solidPack.calculateUnitPrice();
        solidPack.unit.updateUnitValuesInPack(solidPack);

        quantityPrice.setUnitPriceStr(solidPack.getUnitPriceStr());
        quantityPrice.setUnitPrice(solidPack.getUnitPrice());
    }

    private void calculateUnitPrice() {
        setUnitPrice(getPriceInCents() / getPackageSizeInGrams());
    }

    static SolidPack createPackage(String productName, Double price) {
        SolidPack solidPack = createBasicPackage(productName);
        if (solidPack != null) {
            solidPack.setPriceInCents(price * 100);
            solidPack.unit.updatePackageSize(solidPack);
            solidPack.unit.updatePackageSizeInGrams(solidPack);
            solidPack.calculateUnitPrice();
            solidPack.unit.updateUnitValuesInPack(solidPack);
        }
        return solidPack;
    }


    static SolidPack createPackage(String packageSize, String unitPriceStr, Double price) {

        SolidPack solidPack = null;
        Unit unit = null;
        unitPriceStr = StringUtils.trimToEmpty(unitPriceStr).toLowerCase();
        List<String> unitPriceElements = new ArrayList<>();

        Double unitCostInCents = 0D;
        Double unitSizeInGrams = 0D;

        if (containsIgnoreCase(unitPriceStr, GRAMS.symbol) || containsIgnoreCase(unitPriceStr, KILOGRAMS.symbol)) {
            unitPriceElements.addAll(Arrays.asList(StringUtils.split(unitPriceStr)));
            if (CollectionUtils.isNotEmpty(unitPriceElements) && unitPriceElements.size() == 3) {
                unitPriceElements.remove(1);
                String unitCostStr = StringUtils.trimToEmpty(unitPriceElements.get(0)).toLowerCase();

                if (unitCostStr.contains("$")) {
                    unitCostInCents = Double.parseDouble(removeCurrencySymbols(unitCostStr)) * 100;
                }

                String unitSize = StringUtils.trimToEmpty(unitPriceElements.get(1)).toLowerCase();
                if (unitSize.endsWith(KILOGRAMS.symbol)) {
                    unitSizeInGrams = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll(KILOGRAMS.symbol, ""))) * 1000;
                    unit = KILOGRAMS;
                } else if (unitSize.endsWith(GRAMS.symbol)) {
                    unitSizeInGrams = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll(GRAMS.symbol, "")));
                    unit = GRAMS;
                }
            }

            solidPack = new SolidPack();
            solidPack.setUnit(unit);
            solidPack.setUnitPrice(unitCostInCents / unitSizeInGrams);
            solidPack.setPackSizeStr(packageSize);
            solidPack.setPriceInCents(price * 100);
            final SolidPack basicPackage = createBasicPackage(packageSize);
            if (basicPackage != null) {
                solidPack.setPackSize(basicPackage.getPackSize());
                solidPack.setPackageSizeInGrams(basicPackage.getPackageSizeInGrams());
            }
            unit.updateUnitValuesInPack(solidPack);
        }

        return solidPack;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    private Double getPackageSizeInGrams() {
        return packageSizeInGrams;
    }

    private void setPackageSizeInGrams(Double packageSizeInGrams) {
        this.packageSizeInGrams = packageSizeInGrams;
    }


    enum Unit {
        GRAMS("g") {
            @Override
            void updateUnitValuesInPack(SolidPack solidPack) {
                if (solidPack.getUnitPrice() < 100) {
                    solidPack.setUnitPriceStr(formatCurrencyAmount(solidPack.getUnitPrice() * 100) + " per 100g");
                } else {
                    solidPack.setUnitPriceStr(formatCurrencyAmount(solidPack.getUnitPrice()) + " per gram");
                }
                solidPack.setUnitSize(getSymbol());
            }

            @Override
            void updatePackageSizeInGrams(SolidPack solidPack) {
                solidPack.setPackageSizeInGrams(solidPack.getPackSize());
            }

            @Override
            void updatePackageSize(SolidPack solidPack) {
                solidPack.setPackSizeStr(solidPack.getPackSize() + getSymbol());
            }

        }, KILOGRAMS("kg") {
            @Override
            void updateUnitValuesInPack(SolidPack solidPack) {
                solidPack.setUnitPriceStr(formatCurrencyAmount(solidPack.getUnitPrice() * 1000) + " per kg");
                solidPack.setUnitSize(getSymbol());
            }

            @Override
            void updatePackageSizeInGrams(SolidPack solidPack) {
                solidPack.setPackageSizeInGrams(solidPack.getPackSize() * 1000);
            }

            @Override
            void updatePackageSize(SolidPack solidPack) {
                solidPack.setPackSizeStr(solidPack.getPackSize() + getSymbol());
            }
        };

        private String symbol;

        Unit(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        abstract void updatePackageSizeInGrams(SolidPack solidPack);

        abstract void updateUnitValuesInPack(SolidPack solidPack);

        abstract void updatePackageSize(SolidPack solidPack);

    }
}
