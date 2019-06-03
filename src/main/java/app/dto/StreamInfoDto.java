package app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class StreamInfoDto {
    private final String inputUrl;
    private final String outputUrl;

    @JsonCreator
    public StreamInfoDto(@JsonProperty("inputUrl") String inputUrl,
                         @JsonProperty("outputUrl") String outputUrl) {
        this.inputUrl = inputUrl;
        this.outputUrl = outputUrl;
    }
}
