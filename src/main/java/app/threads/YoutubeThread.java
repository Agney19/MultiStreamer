package app.threads;

import app.dto.StreamInfoDto;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class YoutubeThread extends Thread {

	private final String inputUrl;
	private final String outputUrl;

	public YoutubeThread(StreamInfoDto dto) {
		inputUrl = dto.getInputUrl();
		outputUrl = dto.getOutputUrl();
	}

	@Override
	public void run() {
		try {
			FFmpeg ffmpeg = new FFmpeg();
			FFprobe ffprobe = new FFprobe();
			FFmpegBuilder builder = new FFmpegBuilder()
					.setInput(inputUrl)
					.readAtNativeFrameRate()
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
