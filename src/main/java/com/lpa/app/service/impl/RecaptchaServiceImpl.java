package com.lpa.app.service.impl;

import com.lpa.app.dto.response.RecaptchaResponse;
import com.lpa.app.service.RecaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    private static final String GOOGLE_RECAPTCHA="https://www.google.com/recaptcha/api/siteverify";
    private final String RECAPTCHA_SECRET="6LeXGOUmAAAAAH6mEdcyP3MgHjUBZcdaejAtZCAA";
    @Override
    public boolean validateRecaptcha(String captcha) {

        RestTemplate restTemplate= new RestTemplate();
        MultiValueMap<String, String> request= new LinkedMultiValueMap<>();
        request.add("secret",RECAPTCHA_SECRET);
        request.add("response",captcha);

        RecaptchaResponse apiResponse= restTemplate.postForObject(GOOGLE_RECAPTCHA, request,RecaptchaResponse.class);

        if (apiResponse == null) {
            return false;
        }

        return Boolean.TRUE.equals(apiResponse.getSuccess());
    }
}
