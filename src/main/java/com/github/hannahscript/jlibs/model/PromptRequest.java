package com.github.hannahscript.jlibs.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class PromptRequest {
    private int promptId;
    String description;

    public PromptRequest(int promptId, String description) {
        this.promptId = promptId;
        this.description = description;
    }
}
