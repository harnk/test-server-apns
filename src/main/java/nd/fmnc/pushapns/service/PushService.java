package nd.fmnc.pushapns.service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import nd.fmnc.pushapns.PushRepository;
import nd.fmnc.pushapns.entities.Push;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dscottnull on 7/24/18.
 */
@Service
public class PushService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    ApnsService service = null;

    @Autowired
    private PushRepository pushRepository;

    @Value("${apns_test_token}")
    private String apnsTestToken;

    @Value("${apns_test_payload}")
    private String apnsTestPayload;

    public List<Push> getSomePushesNotSentYet(int howMany) {
        List<Push> pushes = new ArrayList<>();
        pushes = pushRepository.findAllByTimeSentIsNull(new PageRequest(0,howMany));
        return pushes;
    }

    public void sendTestPush(String junkStr){
        // Do APNS push
        ApnsService service = APNS.newService()
                .withCert("fmnc-pushapns.p12", "pushapns")
//                .withAppleDestination(true)
                .withSandboxDestination()
                .build();

        String payload = apnsTestPayload;
        String token = apnsTestToken;


        logger.info("payload:"+payload);
        service.push(token, payload);
        logger.info("the message was hopefully sent: "+payload);
    }

    public boolean sendAndUpdatePush(Push push){
        // Do APNS push
        logger.info("TBD - do APNS service.push to token: " + push.getDeviceToken()+ " with payload: "+push.getPayload());

        // If Successful, set time_sent
        long now = ZonedDateTime.now().toInstant().toEpochMilli();
//        logger.info("// If Successful, set time sent: "+now);
        push.setTimeSent(now);
        updatePush(push.getId(),push);
        return true;
    }

    public Push getPush(String id) {
        return pushRepository.findOne(id);
    }

    public void addPush(Push push){
        pushRepository.save(push);
    }

    public void updatePush(Long id, Push push) {
        pushRepository.save(push);
    }

    public void deletePush(String id) {
        pushRepository.delete(id);
    }


}
