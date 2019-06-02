package app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
public final class BroadcastInfoDto {
    private String broadcastTitle;
    private String streamTitle;
    // new DateTime("2019-06-03T12:28:00.000Z")
    private String startDateTime;
    private String endDateTime;
}
