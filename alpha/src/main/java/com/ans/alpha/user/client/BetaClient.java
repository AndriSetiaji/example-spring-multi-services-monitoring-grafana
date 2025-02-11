package com.ans.alpha.user.client;

import com.ans.alpha.config.TestFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        value= "betaClient",
        url = "${svc.beta.endpoint}",
        configuration = TestFeignConfig.class
)
public interface BetaClient {

    @GetMapping("${svc.beta.api.users}")
    String getUsersClient();

    @GetMapping("${svc.beta.api.beta}")
    String getBetaClient();

    @GetMapping("${svc.beta.api.gamma}")
    String getGammaClient();
}
