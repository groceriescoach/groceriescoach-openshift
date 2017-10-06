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
import static com.groceriescoach.core.domain.pack.LiquidPack.Unit.LITRE;
import static com.groceriescoach.core.domain.pack.LiquidPack.Unit.MILLILITRE;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class LiquidPack extends Pack {

    private static final long serialVersionUID = 925625131255694259L;

    private Unit unit;
    private Double packageSizeInMls;

    private static final Logger logger = LoggerFactory.getLogger(LiquidPack.class);

    private LiquidPack() {
    }

    private LiquidPack(LiquidPack liquidPack) {
        super(liquidPack);
        this.unit = liquidPack.getUnit();
        this.packageSizeInMls = liquidPack.getPackageSizeInMls();
    }

    @Override
    public boolean hasUnitPrice() {
        return true;
    }

    @Override
    protected void updateUnitPricesInQuantityPrice(GroceriesCoachProduct.QuantityPrice quantityPrice) {
        LiquidPack liquidPack = new LiquidPack(this);
        liquidPack.setPackageSizeInMls(liquidPack.packageSizeInMls * quantityPrice.getQuantity());
        liquidPack.setPriceInCents(quantityPrice.getPrice() * 100);
        liquidPack.calculateUnitPrice();
        liquidPack.unit.updateUnitValuesInPack(liquidPack);
        quantityPrice.setUnitPriceStr(liquidPack.getUnitPriceStr());
        quantityPrice.setUnitPrice(liquidPack.getUnitPrice());

    }

    private void calculateUnitPrice() {
        setUnitPrice(getPriceInCents() / getPackageSizeInMls());
    }

    public static LiquidPack createPackage(String productName, Double price) {
        LiquidPack liquidPack = createBasicPackage(productName);
        if (liquidPack != null) {
            liquidPack.setPriceInCents(price * 100);
            liquidPack.unit.updatePackageSize(liquidPack);
            liquidPack.unit.updatePackageSizeInMls(liquidPack);
            liquidPack.calculateUnitPrice();
            liquidPack.unit.updateUnitValuesInPack(liquidPack);
        }
        return liquidPack;
    }

    private static LiquidPack createBasicPackage(String packageInfo) {
        LiquidPack liquidPack = null;
        Unit selectedUnit = null;
        Double packageSize = null;

        String nameWorkingCopy = StringUtils.trimToEmpty(packageInfo).toLowerCase();

        nameWorkingCopy = nameWorkingCopy.replaceAll("-", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("mls", "ml");
        nameWorkingCopy = nameWorkingCopy.replaceAll("ml", " ml");
        nameWorkingCopy = nameWorkingCopy.replaceAll("litres", "litre");
        nameWorkingCopy = nameWorkingCopy.replaceAll("litre", " l");
        nameWorkingCopy = nameWorkingCopy.replaceAll("(\\d)\\s*x\\s*(\\d)", "$1*$2");

        List<Unit> units = Arrays.asList(MILLILITRE, LITRE);

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
                                logger.debug("Unable to extract liquid pack: {}", packageInfo);
                            }
                        }
                    }
                }
            }
        }

        if (packageSize != null && selectedUnit != null) {
            liquidPack = new LiquidPack();
            liquidPack.setUnit(selectedUnit);
            liquidPack.setPackSize(packageSize);
            selectedUnit.updatePackageSizeInMls(liquidPack);
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

        if (containsIgnoreCase(unitPriceStr, MILLILITRE.symbol) || containsIgnoreCase(unitPriceStr, LITRE.symbol)) {
            unitPriceElements.addAll(Arrays.asList(StringUtils.split(unitPriceStr)));
            if (CollectionUtils.isNotEmpty(unitPriceElements) && unitPriceElements.size() == 3) {
                unitPriceElements.remove(1);
                String unitCostStr = StringUtils.trimToEmpty(unitPriceElements.get(0)).toLowerCase();

                if (unitCostStr.contains("$")) {
                    unitCostInCents = Double.parseDouble(removeCurrencySymbols(unitCostStr)) * 100;
                }

                String unitSize = StringUtils.trimToEmpty(unitPriceElements.get(1)).toLowerCase();
                if (unitSize.endsWith(MILLILITRE.symbol)) {
                    unitSizeInMilliLitres = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll("ml", "")));
                    unit = MILLILITRE;
                } else if (unitSize.endsWith(LITRE.symbol)) {
                    unitSizeInMilliLitres = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll("l", ""))) * 1000;
                    unit = LITRE;
                }
            }

            liquidPack = new LiquidPack();
            liquidPack.setUnit(unit);
            liquidPack.setUnitPrice(unitCostInCents / unitSizeInMilliLitres);
            liquidPack.setPackSizeStr(packageSize);
            liquidPack.setPriceInCents(price * 100);

            final LiquidPack basicPackage = createBasicPackage(packageSize);
            if (basicPackage != null) {
                liquidPack.setPackSize(basicPackage.getPackSize());
                liquidPack.setPackageSizeInMls(basicPackage.getPackageSizeInMls());
            }
            unit.updateUnitValuesInPack(liquidPack);
        }

        return liquidPack;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Double getPackageSizeInMls() {
        return packageSizeInMls;
    }

    public void setPackageSizeInMls(Double packageSizeInMls) {
        this.packageSizeInMls = packageSizeInMls;
    }

    enum Unit {
        MILLILITRE("ml") {
            @Override
            void updateUnitValuesInPack(LiquidPack liquidPack) {
                if (liquidPack.getUnitPrice() < 100) {
                    liquidPack.setUnitPriceStr(formatCurrencyAmount(liquidPack.getUnitPrice() * 100) + " per 100ml");
                } else {
                    liquidPack.setUnitPriceStr(formatCurrencyAmount(liquidPack.getUnitPrice()) + " per ml");
                }
                liquidPack.setUnitSize(getSymbol());
            }

            @Override
            void updatePackageSizeInMls(LiquidPack liquidPack) {
                liquidPack.setPackageSizeInMls(liquidPack.getPackSize());
            }

            @Override
            void updatePackageSize(LiquidPack liquidPack) {
                liquidPack.setPackSizeStr(liquidPack.getPackSize() + getSymbol());
            }
        }, LITRE("l") {
            @Override
            void updateUnitValuesInPack(LiquidPack liquidPack) {
                liquidPack.setUnitPriceStr(formatCurrencyAmount(liquidPack.getUnitPrice() * 1000) + " per litre");
            }

            @Override
            void updatePackageSizeInMls(LiquidPack liquidPack) {
                liquidPack.setPackageSizeInMls(liquidPack.getPackSize() * 1000);
            }

            @Override
            void updatePackageSize(LiquidPack liquidPack) {
                liquidPack.setPackSizeStr(liquidPack.getPackSize() + getSymbol());
            }
        };

        private String symbol;

        public String getSymbol() {
            return symbol;
        }

        Unit(String symbol) {
            this.symbol = symbol;
        }

        abstract void updateUnitValuesInPack(LiquidPack liquidPack);

        abstract void updatePackageSizeInMls(LiquidPack liquidPack);

        abstract void updatePackageSize(LiquidPack liquidPack);

    }

}
