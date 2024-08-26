package com.file_manager.feign_client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "user-manager", url = "http://192.168.3.187:8083")
public interface UserClient {
    @GetMapping("/api/v1/user/checkAll")
    Set<Long> findNonExistingUserIds(@RequestParam("responsibles") Set<Long> responsibles);
}

