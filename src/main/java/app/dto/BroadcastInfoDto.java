package app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public final class BroadcastInfoDto {
    private final String broadcastTitle;
    private final String broadcastDesc;
    private final String streamTitle;
    // new DateTime("2019-06-03T12:28:00.000Z")
    private final String startDateTime;
    private final String endDateTime;

    @JsonCreator
    public BroadcastInfoDto(@JsonProperty("broadcastTitle") String broadcastTitle,
                            @JsonProperty("broadcastDesc") String broadcastDesc,
                            @JsonProperty("streamTitle") String streamTitle,
                            @JsonProperty("startDateTime") String startDateTime,
                            @JsonProperty("endDateTime") String endDateTime) {
        this.broadcastTitle = broadcastTitle;
        this.broadcastDesc = broadcastDesc;
        this.streamTitle = streamTitle;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
