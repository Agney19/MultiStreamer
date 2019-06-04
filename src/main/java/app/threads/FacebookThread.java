package app.threads;

import app.dto.StreamInfoDto;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;

import java.io.IOException;

public class FacebookThread extends AbstractPlatformThread {

	public FacebookThread(StreamInfoDto dto) {
		super(dto);
	}

	@Override
	public Platform getPlatform() {
		return Platform.FB;
	}

	@Override
	public void run() {
		try {
			FFmpeg ffmpeg = new FFmpeg();
			FFprobe ffprobe = new FFprobe();
			FFmpegBuilder builder = new FFmpegBuilder()
					.setInput(getInputUrl())
//					.readAtNativeFrameRate()
					.addOutput(getOutputUrl())
					.setAudioSampleRate(44100)
					.setAudioChannels(1)
					.setAudioBitRate(128000)
					.setVideoPixelFormat("yuv420p")
					.setVideoFrameRate(30)
					.setVideoBitRate(2048000)
					.setFormat("flv")
					.setAudioCodec("copy")
					.setVideoCodec("libx264")
					.done();
			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
			FFmpegJob job = executor.createJob(builder);
			job.run();
		} catch (RuntimeException | IOException ignored) {
		}
	}
}
