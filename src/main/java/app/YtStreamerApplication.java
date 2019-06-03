package app;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YtStreamerApplication {

    public static void main(String[] args) {
        SpringApplication.run(YtStreamerApplication.class, args);

//        try {
//            FFmpeg ffmpeg = new FFmpeg();
//            FFprobe ffprobe = new FFprobe();
//            FFmpegBuilder builder = new FFmpegBuilder()
//                    .setInput("rtmp://127.0.0.1:1935/live")
//                    .addOutput("rtmp://a.rtmp.youtube.com/live2/7ave-fwdq-md2p-18c2")
//                    .setFormat("flv")
//                    .setAudioCodec("copy")
//                    .setVideoCodec("copy")
//                    .done();
//            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
//            FFmpegJob job = executor.createJob(builder);
//            job.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
