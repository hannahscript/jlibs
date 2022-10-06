package com.github.hannahscript.jlibs.message.server;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessage {
    private ErrorId errorId;
    private String message;

    public ErrorMessage(ErrorId errorId, String message) {
        this.errorId = errorId;
        this.message = message;
    }

    public enum ErrorId {
        NAME_TAKEN(1);

        private final int errorId;

        ErrorId(int errorId) {
            this.errorId = errorId;
        }

        @JsonValue
        public int getErrorId() {
            return errorId;
        }
    }
}
