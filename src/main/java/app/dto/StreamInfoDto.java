package app.dto;

import app.threads.Platform;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StreamInfoDto {

    private final String inputUrl;
    private final String outputUrl;
	private final Platform platform;

    @JsonCreator
    public StreamInfoDto(@JsonProperty("inputUrl") String inputUrl,
                         @JsonProperty("outputUrl") String outputUrl,
						 @JsonProperty("platform") Platform platform) {
        this.inputUrl = inputUrl;
        this.outputUrl = outputUrl;
        this.platform = platform;
    }
}
