package com.groceriescoach;


import com.groceriescoach.amcal.config.AmcalConfig;
import com.groceriescoach.babiesrus.config.BabiesRUsConfig;
import com.groceriescoach.babyandtoddlertown.BabyAndToddlerTownConfig;
import com.groceriescoach.babybounce.BabyBounceConfig;
import com.groceriescoach.babybunting.BabyBuntingConfig;
import com.groceriescoach.babykingdom.BabyKingdomConfig;
import com.groceriescoach.bigw.BigWConfig;
import com.groceriescoach.chemistwarehouse.ChemistWarehouseConfig;
import com.groceriescoach.coles.config.ColesConfig;
import com.groceriescoach.config.GroceriesCoachConfig;
import com.groceriescoach.kmart.KmartConfig;
import com.groceriescoach.priceline.PricelineConfig;
import com.groceriescoach.target.TargetConfig;
import com.groceriescoach.woolworths.config.WoolworthsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Import({
        GroceriesCoachConfig.class, AmcalConfig.class, BabiesRUsConfig.class, BabyAndToddlerTownConfig.class,
        BabyBounceConfig.class, BabyBuntingConfig.class, BabyKingdomConfig.class, BigWConfig.class,
        ChemistWarehouseConfig.class, ColesConfig.class, KmartConfig.class, PricelineConfig.class, TargetConfig.class,
        WoolworthsConfig.class
})
@SpringBootApplication
@EnableAsync
public class GroceriesCoachApplication {

    private static final Logger logger = LoggerFactory.getLogger(GroceriesCoachApplication.class);

    public static void main(String[] args) {

        SpringApplication groceriesCoachApp = new SpringApplication(GroceriesCoachApplication.class);

        if (applicationIsOnline()) {
            logger.info("Application is online.");
            groceriesCoachApp.setAdditionalProfiles("online");
        } else {
            logger.info("Application is offline.");
            groceriesCoachApp.setAdditionalProfiles("offline");
        }

        groceriesCoachApp.run(args);
    }


    private static boolean applicationIsOnline() {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("google.com", 80);
        try {
            socket.connect(address, 3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {

            }
        }
    }


}
