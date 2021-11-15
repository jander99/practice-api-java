package com.github.jander99.practice.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.server.ServerResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationVersion {

    public String applicationName;

    public String environment;

    public String version;
}
