package nd.fmnc.pushapns;

import nd.fmnc.pushapns.entities.Push;
import nd.fmnc.pushapns.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dscottnull on 7/23/18.
 */

@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PushService pushService;

    @Value("${apns_batch_size:20}")
    private int apnsBatchSize;

    @Scheduled(fixedDelayString = "${apns_interval_time_ms:2000}")
    public void doMainLoop() {
        logger.info("FMNC LOOP IS WORKING ");

        pushService.sendTestPush("test push to FMNC Phone app thru sandbox"); // TBRemoved

//        List<Push> pushes = new ArrayList<>();
//        // Grab a batch of records that haven't been sent
//        pushes = pushService.getSomePushesNotSentYet(apnsBatchSize);
//        // Send the APNS pushes
//        for (Push push : pushes) {
//            pushService.sendAndUpdatePush(push);
//        }
    }
}