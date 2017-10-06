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
import static com.groceriescoach.core.domain.pack.LengthPack.Unit.METRE;
import static com.groceriescoach.core.domain.pack.LengthPack.Unit.MILLIMETRE;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class LengthPack extends Pack {

    private static final long serialVersionUID = 5037618998842138161L;

    private Unit unit;
    private Double packageSizeInMms;

    private static final Logger logger = LoggerFactory.getLogger(LengthPack.class);

    private LengthPack() {
    }

    private LengthPack(LengthPack lengthPack) {
        super(lengthPack);
        this.unit = lengthPack.getUnit();
        this.packageSizeInMms = lengthPack.getPackageSizeInMms();
    }

    @Override
    boolean hasUnitPrice() {
        return true;
    }

    @Override
    protected void updateUnitPricesInQuantityPrice(GroceriesCoachProduct.QuantityPrice quantityPrice) {
        LengthPack lengthPack = new LengthPack(this);
        lengthPack.setPackageSizeInMms(lengthPack.packageSizeInMms * quantityPrice.getQuantity());
        lengthPack.setPriceInCents(quantityPrice.getPrice() * 100);
        lengthPack.calculateUnitPrice();
        lengthPack.unit.updateUnitValuesInPack(lengthPack);
        quantityPrice.setUnitPriceStr(lengthPack.getUnitPriceStr());
        quantityPrice.setUnitPrice(lengthPack.getUnitPrice());
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    static LengthPack createPackage(String productName, Double price) {
        LengthPack lengthPack = createBasicPackage(productName);
        if (lengthPack != null) {
            lengthPack.setPriceInCents(price * 100);
            lengthPack.unit.updatePackageSize(lengthPack);
            lengthPack.unit.updatePackageSizeInMms(lengthPack);
            lengthPack.calculateUnitPrice();
            lengthPack.unit.updateUnitValuesInPack(lengthPack);
        }
        return lengthPack;
    }

    private static LengthPack createBasicPackage(String packageInfo) {
        LengthPack lengthPack = null;
        Unit selectedUnit = null;
        Double packageSize = null;

        String nameWorkingCopy = StringUtils.trimToEmpty(packageInfo).toLowerCase();

        nameWorkingCopy = nameWorkingCopy.replaceAll("-", "");
        nameWorkingCopy = nameWorkingCopy.replaceAll("mms", "mm");
        nameWorkingCopy = nameWorkingCopy.replaceAll("meter", "metre");
        nameWorkingCopy = nameWorkingCopy.replaceAll("metres", "metre");
        nameWorkingCopy = nameWorkingCopy.replaceAll("metre", "m");
        nameWorkingCopy = nameWorkingCopy.replaceAll("(\\d)\\s*x\\s*(\\d)", "$1*$2");

        List<Unit> units = Arrays.asList(MILLIMETRE, METRE);

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
                                logger.debug("Unable to extract length pack: {}", packageInfo);
                            }
                        }
                    }
                }
            }
        }

        if (packageSize != null && selectedUnit != null) {
            lengthPack = new LengthPack();
            lengthPack.setUnit(selectedUnit);
            lengthPack.setPackSize(packageSize);
            selectedUnit.updatePackageSizeInMms(lengthPack);
        }

        return lengthPack;
    }

    private void calculateUnitPrice() {
        setUnitPrice(getPriceInCents() / getPackageSizeInMms());
    }

    static LengthPack createPackage(String packageSize, String unitPriceStr, Double price) {
        LengthPack lengthPack = null;
        Unit unit = null;
        unitPriceStr = StringUtils.trimToEmpty(unitPriceStr).toLowerCase();
        List<String> unitPriceElements = new ArrayList<>();

        Double unitCostInCents = 0D;
        Double unitSizeInMillimetres = 0D;

        if (containsIgnoreCase(unitPriceStr, MILLIMETRE.symbol) || containsIgnoreCase(unitPriceStr, METRE.symbol)) {
            unitPriceElements.addAll(Arrays.asList(StringUtils.split(unitPriceStr)));
            if (CollectionUtils.isNotEmpty(unitPriceElements) && unitPriceElements.size() == 3) {
                unitPriceElements.remove(1);
                String unitCostStr = StringUtils.trimToEmpty(unitPriceElements.get(0)).toLowerCase();

                if (unitCostStr.contains("$")) {
                    unitCostInCents = Double.parseDouble(removeCurrencySymbols(unitCostStr)) * 100;
                }

                String unitSize = StringUtils.trimToEmpty(unitPriceElements.get(1)).toLowerCase();
                if (unitSize.endsWith(MILLIMETRE.symbol)) {
                    unitSizeInMillimetres = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll(MILLIMETRE.symbol, "")));
                    unit = MILLIMETRE;
                } else if (unitSize.endsWith(METRE.symbol)) {
                    unitSizeInMillimetres = Double.parseDouble(StringUtils.trimToEmpty(unitSize.replaceAll(METRE.symbol, ""))) * 1000;
                    unit = METRE;
                }
            }

            lengthPack = new LengthPack();
            lengthPack.setUnit(unit);
            lengthPack.setUnitPrice(unitCostInCents / unitSizeInMillimetres);
            lengthPack.setPackSizeStr(packageSize);
            lengthPack.setPriceInCents(price * 100);

            final LengthPack basicPackage = createBasicPackage(packageSize);
            if (basicPackage != null) {
                lengthPack.setPackSize(basicPackage.getPackSize());
                lengthPack.setPackageSizeInMms(basicPackage.getPackageSizeInMms());
            }
            unit.updateUnitValuesInPack(lengthPack);
        }

        return lengthPack;
    }

    private Double getPackageSizeInMms() {
        return packageSizeInMms;
    }

    private void setPackageSizeInMms(Double packageSizeInMms) {
        this.packageSizeInMms = packageSizeInMms;
    }

    enum Unit {
        MILLIMETRE("mm") {
            @Override
            void updateUnitValuesInPack(LengthPack lengthPack) {
                if (lengthPack.getUnitPrice() < 100) {
                    lengthPack.setUnitPriceStr(formatCurrencyAmount(lengthPack.getUnitPrice() * 100) + " per 100mm");
                } else {
                    lengthPack.setUnitPriceStr(formatCurrencyAmount(lengthPack.getUnitPrice()) + " per mm");
                }
                lengthPack.setUnitSize(getSymbol());
            }

            @Override
            void updatePackageSizeInMms(LengthPack lengthPack) {
                lengthPack.setPackageSizeInMms(lengthPack.getPackSize());
            }

            @Override
            void updatePackageSize(LengthPack lengthPack) {
                lengthPack.setPackSizeStr(lengthPack.getPackSize() + getSymbol());
            }
        }, METRE("m") {
            @Override
            void updateUnitValuesInPack(LengthPack lengthPack) {
                lengthPack.setUnitPriceStr(formatCurrencyAmount(lengthPack.getUnitPrice() * 1000) + " per metre");
                lengthPack.setUnitSize(getSymbol());
            }

            @Override
            void updatePackageSizeInMms(LengthPack lengthPack) {
                lengthPack.setPackageSizeInMms(lengthPack.getPackSize() * 1000);
            }

            @Override
            void updatePackageSize(LengthPack lengthPack) {
                lengthPack.setPackSizeStr(lengthPack.getPackSize() + getSymbol());
            }
        };

        private String symbol;

        public String getSymbol() {
            return symbol;
        }

        Unit(String symbol) {
            this.symbol = symbol;
        }

        abstract void updateUnitValuesInPack(LengthPack lengthPack);

        abstract void updatePackageSizeInMms(LengthPack lengthPack);

        abstract void updatePackageSize(LengthPack lengthPack);
    }
}
