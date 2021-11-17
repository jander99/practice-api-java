package com.github.jander99.practice.producer;

import com.vdurmont.semver4j.Semver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class ProducerHandler {

    private int MAJOR_VERSION_MAX = 2;
    private int MINOR_VERSION_MAX = 5;
    private int PATCH_VERSION_MAX = 9;

    private List<String> validEnvironments;

    public ProducerHandler(@Value("${application.environments}") List<String> validEnvironments) {
        this.validEnvironments = validEnvironments;
    }

    public Mono<ServerResponse> version(ServerRequest request) {
        var environment = request.pathVariable("env");

        if (validEnvironments.stream()
                .anyMatch(s -> s.equalsIgnoreCase(environment))) {
            log.info("Environment is valid.");
            var isSnapshot = !environment.equalsIgnoreCase("PROD");
            var appName = request.pathVariable("appName");
            var version = generateVersion(isSnapshot);

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ApplicationVersion(appName, environment, version))
                    .switchIfEmpty(ServerResponse.notFound().build());
        } else {
            log.error("Environment Invalid.");
            return ServerResponse.notFound().build();
        }

    }

    private String generateVersion(boolean isSnapshot) {

        var rng = new Random();
        var majorVersion = rng.nextInt(MAJOR_VERSION_MAX)+1;
        var minorVersion = rng.nextInt(MINOR_VERSION_MAX)+1;
        var patchVersion = rng.nextInt(PATCH_VERSION_MAX)+1;

        var snapshot = isSnapshot ? "-SNAPSHOT": "";

        Semver version = new Semver(String.format("%s.%s.%s%s",
                majorVersion, minorVersion, patchVersion, snapshot), Semver.SemverType.STRICT);

        log.info("Version {}", version);
        return version.toString();
    }

}
