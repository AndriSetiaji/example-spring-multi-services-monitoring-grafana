package com.ans.beta.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value= "gammaClient", url = "${svc.gamma.endpoint}")
public interface GammaClient {
    @GetMapping("${svc.gamma.api.users}")
    String getUsersClient();

    @GetMapping("${svc.gamma.api.exception}")
    String getGammaExceptionClient();
}