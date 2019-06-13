package app.threads;

import app.dto.StreamDoubleInfoDto;
import app.dto.StreamInfoDto;
import lombok.Getter;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;

import java.io.IOException;

@Getter
public class YoutubeDoubleThread extends AbstractPlatformThread {

    private String secInputUrl;

    public YoutubeDoubleThread(StreamDoubleInfoDto dto) {
        super(new StreamInfoDto(dto.getInputUrl(), dto.getOutputUrl(), dto.getPlatform()));
        this.secInputUrl = dto.getSecInputUrl();
    }

    @Override
    public void run() {
//        try {
////            final Process p = new ProcessBuilder("cmd1", "arg1", "arg2").start();
////            final int retval = p.waitFor();
//            final String command = "ffmpeg " +
//                    "-i " + getInputUrl() + " " +
//                    "-i " + getSecInputUrl() + " " +
//                    "-filter_complex " +
//                    "\"[0:v][1:v]hstack=inputs=2[v]; " +
//                    "[0:a][1:a]amerge[a]\" " +
//                    "-map \"[v]\" -map \"[a]\" -ac 2 " +
//                    "-f flv " + getOutputUrl();
//            System.out.println(command);
//			Runtime.getRuntime().exec(command).waitFor();
//        } catch (RuntimeException | IOException | InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            FFmpeg ffmpeg = new FFmpeg();
            FFprobe ffprobe = new FFprobe();
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(getInputUrl())
                    .addInput(getSecInputUrl())
                    .setComplexFilter("[0:v][1:v]hstack=inputs=2[v]; " +
                            "[0:a][1:a]amerge[a]")
//                    .addExtraArgs("--map '[v]'")
//                    .addExtraArgs("--map '[a]'")
                    .addOutput(getOutputUrl())
                    .setAudioChannels(2)
                    .setFormat("flv")
//                    .addExtraArgs("-i rtmp://127.0.0.1:1935/live " +
//                            "-i rtmp://127.0.0.1:1935/live " +
//                            "-filter_complex \"[0:v][1:v]hstack=inputs=2[v]; [0:a][1:a]amerge[a]\" " +
//                            "-map \"[v]\" " +
//                            "-map \"[a]\" " +
//                            "-ac 2 " +
//                            "-f flv")
//                    .addOutput(getOutputUrl())
                    .done();
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            FFmpegJob job = executor.createJob(builder);
            job.run();


        } catch (RuntimeException | IOException ignored) {
        }
    }

    @Override
    public Platform getPlatform() {
        return Platform.YT;
    }
}
