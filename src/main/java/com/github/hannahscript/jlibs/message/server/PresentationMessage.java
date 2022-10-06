package com.github.hannahscript.jlibs.message.server;

import com.github.hannahscript.jlibs.model.RenderedStory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PresentationMessage {
    private Map<String, RenderedStory> stories;

    public PresentationMessage(Map<String, RenderedStory> stories) {
        this.stories = stories;
    }
}
