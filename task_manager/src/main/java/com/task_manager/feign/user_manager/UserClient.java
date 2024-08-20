package com.task_manager.feign.user_manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-manager", url = "http://192.168.3.187:8083")
public interface UserClient {
    @GetMapping("/api/v1/user/checkAll")
    Boolean findExistingUserIds(@RequestParam("responsibles") Long[] responsibles);
}
