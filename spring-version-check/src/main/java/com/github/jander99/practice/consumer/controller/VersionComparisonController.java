package com.github.jander99.practice.consumer.controller;

import com.github.jander99.practice.consumer.entity.Application;
import com.github.jander99.practice.consumer.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class VersionComparisonController {

    private VersionService versionService;

    public VersionComparisonController(VersionService versionService) {
        this.versionService = versionService;
    }

    @RequestMapping(path="/versions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Application>> getAllVersionInformation() {

        List<Application> apps = versionService.getAllAppVersions();

        return ResponseEntity.ok(apps);
    }
}
