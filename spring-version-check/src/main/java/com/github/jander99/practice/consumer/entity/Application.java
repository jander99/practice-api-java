package com.github.jander99.practice.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    public String name;

    public List<Version> versions;

    public String action;
}

