package com.groceriescoach;


import com.groceriescoach.amcal.config.AmcalConfig;
import com.groceriescoach.babyandtoddlertown.config.BabyAndToddlerTownConfig;
import com.groceriescoach.babybounce.config.BabyBounceConfig;
import com.groceriescoach.babybunting.config.BabyBuntingConfig;
import com.groceriescoach.babykingdom.config.BabyKingdomConfig;
import com.groceriescoach.babysavings.config.BabySavingsConfig;
import com.groceriescoach.babyvillage.config.BabyVillageConfig;
import com.groceriescoach.bigw.config.BigWConfig;
import com.groceriescoach.chemistwarehouse.config.ChemistWarehouseConfig;
import com.groceriescoach.cincottachemist.config.CincottaChemistConfig;
import com.groceriescoach.coles.config.ColesConfig;
import com.groceriescoach.config.GroceriesCoachConfig;
import com.groceriescoach.mrvitamins.config.MrVitaminsConfig;
import com.groceriescoach.mychemist.config.MyChemistConfig;
import com.groceriescoach.nursingangel.config.NursingAngelConfig;
import com.groceriescoach.pharmacy4less.config.Pharmacy4LessConfig;
import com.groceriescoach.priceline.config.PricelineConfig;
import com.groceriescoach.royyoung.config.RoyYoungConfig;
import com.groceriescoach.target.config.TargetConfig;
import com.groceriescoach.terrywhite.config.TerryWhiteConfig;
import com.groceriescoach.thepharmacy.config.ThePharmacyConfig;
import com.groceriescoach.woolworths.config.WoolworthsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@Import({
        GroceriesCoachConfig.class, AmcalConfig.class, BabyAndToddlerTownConfig.class,
        BabyBounceConfig.class, BabyBuntingConfig.class, BabyKingdomConfig.class, BabySavingsConfig.class,
        BabyVillageConfig.class, BigWConfig.class, ChemistWarehouseConfig.class, CincottaChemistConfig.class,
        ColesConfig.class, MrVitaminsConfig.class, MyChemistConfig.class, NursingAngelConfig.class,
        Pharmacy4LessConfig.class, PricelineConfig.class, RoyYoungConfig.class, TargetConfig.class,
        ThePharmacyConfig.class, TerryWhiteConfig.class, WoolworthsConfig.class
})
@SpringBootApplication
@EnableAsync
public class GroceriesCoachApplication {

    private static final Logger logger = LoggerFactory.getLogger(GroceriesCoachApplication.class);

    public static void main(String[] args) {
        SpringApplication groceriesCoachApp = new SpringApplication(GroceriesCoachApplication.class);
        groceriesCoachApp.run(args);
    }
}
