package app.threads;

import app.dto.StreamInfoDto;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.springframework.util.Assert;

import java.io.IOException;

public class VkontakteThread extends AbstractPlatformThread {

	public VkontakteThread(StreamInfoDto dto) {
		super(dto);
	}

	@Override
	public Platform getPlatform() {
		return Platform.VK;
	}

	@Override
	public void run() {
//		try {
//			final Process p = new ProcessBuilder("cmd1", "arg1", "arg2").start();
//			final int retval = p.waitFor();
//			final String command = "ffmpeg -y -v error -i \"" + getInputUrl() + "\" -c:a copy -ac 1 -ar 44100 -b:a 128k -c:v libx264 " +
//					"-pix_fmt yuv420p -r 30 -g 60 -vb 2048k -minrate 500k -maxrate 2000k -bufsize 4096k " +
//					"-max_muxing_queue_size 1024 -f flv \"" + getOutputUrl() + "\"";
//			System.out.println(command);
////			Runtime.getRuntime().exec(command).waitFor();
//		} catch (RuntimeException | IOException | InterruptedException e) {
//			e.printStackTrace();
//		}
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
