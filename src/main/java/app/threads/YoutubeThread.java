package app.threads;

import app.dto.StreamInfoDto;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class YoutubeThread extends AbstractPlatformThread {

	public YoutubeThread(StreamInfoDto dto) {
		super(dto);
	}

	@Override
	public Platform getPlatform() {
		return Platform.YT;
	}
}
