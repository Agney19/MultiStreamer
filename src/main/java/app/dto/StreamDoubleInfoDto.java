package app.dto;

import app.threads.Platform;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class StreamDoubleInfoDto extends StreamInfoDto {

    private final String secInputUrl;

    @JsonCreator
    public StreamDoubleInfoDto(@JsonProperty("inputUrl") String inputUrl,
                               @JsonProperty("secInputUrl") String secInputUrl,
                               @JsonProperty("outputUrl") String outputUrl,
                               @JsonProperty("platform") Platform platform) {
        super(inputUrl, outputUrl, platform);
        this.secInputUrl = secInputUrl;
    }
}
