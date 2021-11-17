package com.github.jander99.practice.consumer.service;

import com.github.jander99.practice.consumer.entity.Application;
import com.github.jander99.practice.consumer.entity.RemoteAppVersion;
import com.github.jander99.practice.consumer.entity.Version;
import com.vdurmont.semver4j.Semver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class VersionService {

    private List<String> appNames;

    private List<String> envs;

    private RestTemplate restTemplate;

    private String versionServiceUrl;

    public VersionService(@Value("${application.names}") List<String> appNames,
                          @Value("${application.environments}") List<String> envs,
                          @Value("${application.version.serviceUrl}") String versionServiceUrl,
                          RestTemplate restTemplate) {
        this.appNames = appNames;
        this.envs = envs;
        this.versionServiceUrl = versionServiceUrl;
        this.restTemplate = restTemplate;
    }

    public List<Application> getAllAppVersions() {
        List<Application> allAppVersions = new ArrayList<>();

        for (String env : envs) {
            for (String appName : appNames)
                try {
                    RemoteAppVersion remoteAppVersion = getApplicationVersion(appName, env);
                    Application app = allAppVersions.stream()
                        .filter(a -> remoteAppVersion.getApplicationName().equals(a.getName()))
                        .findAny()
                        .orElse(null);

                    Version version = new Version(remoteAppVersion.getEnvironment(), remoteAppVersion.getVersion());
                    if(app == null) {
                        List<Version> versions = new ArrayList<>();
                        versions.add(version);
                        app = new Application(remoteAppVersion.getApplicationName(), versions, null);
                    } else {
                        app.getVersions().add(version);
                    }

                    allAppVersions.add(app);
                } catch (RestClientException rce) {
                    log.error("Unable to get Application Version for {} in {}.", appName, env, rce);
                }

        }

        return allAppVersions;
    }

    private RemoteAppVersion getApplicationVersion(String appName, String environment) throws RestClientException {

        log.info("Retreiving version for application {} in environment {}", appName, environment);
        HttpEntity request = new HttpEntity(getHttpHeaders());
        ResponseEntity<RemoteAppVersion> response = restTemplate
            .exchange(versionServiceUrl, HttpMethod.GET, request, RemoteAppVersion.class, environment, appName);
        return response.getBody();

    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private String calculateApplicationAction(Application application) {

        Map<String, Semver> versionMap = new HashMap<>();
        application.getVersions().forEach(v -> versionMap.putIfAbsent(v.getEnvironment(), new Semver(v.getVersion())));

        if (versionMap.containsKey("dev") && versionMap.containsKey("prod")) {
            Semver dev = versionMap.get("dev");
            Semver prod = versionMap.get("prod");
            if (dev.isGreaterThan(prod)) {

            }
        }

        return "Upgrade";
    }

}
