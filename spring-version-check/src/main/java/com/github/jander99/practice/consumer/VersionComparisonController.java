package com.github.jander99.practice.consumer;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class VersionComparisonController {

    @RequestMapping(path="/versions", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getAllVersionInformation() {
        return "{\"Placeholder\":\"Value\"}";
    }

}
