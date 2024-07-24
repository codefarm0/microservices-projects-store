package com.codefarm.idmaster.controller;

import com.codefarm.idmaster.model.IdWithTimestamp;
import com.codefarm.idmaster.service.UniqueIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ids")
public class UniqueIdController {

    @Autowired
    private UniqueIdService uniqueIdService;

    @GetMapping("/uuidv7/newId")
    public IdWithTimestamp generateUUIDv7() {
        return uniqueIdService.generateUUIDv7();
    }










    @GetMapping("/sequential/newId")
    public IdWithTimestamp generateSequentialId() {
        return uniqueIdService.generateSequentialId();
    }

    @GetMapping("/snowflake/newId")
    public IdWithTimestamp generateSnowflakeId() {
        return uniqueIdService.generateSnowflakeId();
    }

    @GetMapping("/uuidv7/last5Ids")
    public List<IdWithTimestamp> getUuidV7Last5Ids() {
        return uniqueIdService.getLast5Ids("uuidv7");
    }

    @GetMapping("/snowflake/last5Ids")
    public List<IdWithTimestamp> getSnowflakeLast5Ids() {
        return uniqueIdService.getLast5Ids("snowflake");
    }
}
