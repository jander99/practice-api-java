package com.github.jander99.practice.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteAppVersion {

    public String applicationName;

    public String environment;

    public String version;

}
