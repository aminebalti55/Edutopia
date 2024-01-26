package com.example.edutopia_res.configuration;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TwilioConfiguration {
    private String twilioAccountSid ="azezae";

    private String twilioAuthToken ="sqdqsdqs";

    /**
     * The credentials Account SID and Auth Token from the Twilio Console should be
     * there in the environment, hence the @PostConstruct init() ensures that the credentials
     * are there in application context.
     */
    @PostConstruct
    public void init() {
        Twilio.init(
                getTwilioAccountSid(),
                getTwilioAuthToken()
        );
    }

    public String getTwilioAccountSid() {
        return twilioAccountSid;
    }

    public String getTwilioAuthToken() {
        return twilioAuthToken;
    }
}
