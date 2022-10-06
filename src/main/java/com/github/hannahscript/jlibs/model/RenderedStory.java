package com.github.hannahscript.jlibs.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RenderedStory {
    private String username;
    private String content;

    public RenderedStory(String username, String content) {
        this.username = username;
        this.content = content;
    }
}
