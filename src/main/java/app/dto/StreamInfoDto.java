package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public final class StreamInfoDto {
    private String inputUrl;
    private String outputUrl;
}
