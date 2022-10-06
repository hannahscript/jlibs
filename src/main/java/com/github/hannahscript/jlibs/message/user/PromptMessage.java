package com.github.hannahscript.jlibs.message.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class PromptMessage {
    private String answer;
    private int promptId;

    public PromptMessage(String answer, int promptId) {
        this.answer = answer;
        this.promptId = promptId;
    }
}
