package app.threads;

import app.dto.StreamInfoDto;
import lombok.Getter;
import lombok.Setter;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.springframework.util.Assert;

import java.io.IOException;

@Getter
public abstract class AbstractPlatformThread extends Thread implements PlatformSpecific {

	private String inputUrl;
	private String outputUrl;

	public AbstractPlatformThread(StreamInfoDto dto) {
		Assert.notNull(dto, "dto is null");

		inputUrl = dto.getInputUrl();
		outputUrl = dto.getOutputUrl();
	}

	@Override
	public void run() {
		Assert.notNull(inputUrl, "inputUrl is null");
		Assert.notNull(outputUrl, "outputUrl is null");
		try {
			FFmpeg ffmpeg = new FFmpeg();
			FFprobe ffprobe = new FFprobe();
			FFmpegBuilder builder = new FFmpegBuilder()
					.setInput(inputUrl)
//					.readAtNativeFrameRate()
					.addOutput(outputUrl)
					.setFormat("flv")
					.setAudioCodec("copy")
					.setVideoCodec("copy")
					.done();
			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
			FFmpegJob job = executor.createJob(builder);
			job.run();
		} catch (RuntimeException | IOException ignored) {
		}
	}
}
