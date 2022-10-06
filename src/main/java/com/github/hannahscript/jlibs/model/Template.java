package com.github.hannahscript.jlibs.model;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Template {
    private int id;
    private String rawTemplate;
    private List<String> promptDescriptions = new ArrayList<>();
}
