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
import java.util.ArrayList;
import java.util.List;

@Getter
public class YoutubeDoubleThread extends AbstractPlatformThread {

    private String secInputUrl;

    public YoutubeDoubleThread(StreamDoubleInfoDto dto) {
        super(new StreamInfoDto(dto.getInputUrl(), dto.getOutputUrl(), dto.getPlatform()));
        this.secInputUrl = dto.getSecInputUrl();
    }

    @Override
    public void run() {
        try {
            FFmpeg ffmpeg = new FFmpeg();
            FFprobe ffprobe = new FFprobe();
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(getInputUrl())
                    .addInput(getSecInputUrl())
                    .setComplexFilter("[0:v][1:v]hstack=inputs=2[v]; " +
                            "[0:a][1:a]amerge[a]")
                    .addOutput(getOutputUrl())
                    .setAudioChannels(2)
                    .setFormat("flv")
                    .addExtraArgs("-map", "[v]")
                    .addExtraArgs("-map", "[a]")
                    .done();
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            FFmpegJob job = executor.createJob(builder);
            job.run();

//            List<String> cmds = new ArrayList<>();
//            cmds.add("ffmpeg");
//            cmds.add("-y");
//            cmds.add("-v");
//            cmds.add("error");
//            cmds.add("-i");
//            cmds.add(getInputUrl());
//            cmds.add("-i");
//            cmds.add(getSecInputUrl());
//            cmds.add("-filter_complex");
//            cmds.add("[0:v][1:v]hstack=inputs=2[v]; [0:a][1:a]amerge[a]");
//            cmds.add("-map");
//            cmds.add("[v]");
//            cmds.add("-map");
//            cmds.add("[a]");
//            cmds.add("-ac");
//            cmds.add("2");
//            cmds.add("-f");
//            cmds.add("flv");
//            cmds.add(getOutputUrl());
//            System.out.println(cmds.stream().reduce((c1, c2) -> c1 + " " + c2).orElse(null));
//            ProcessBuilder pb = new ProcessBuilder(cmds);
//            pb.start().waitFor();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Platform getPlatform() {
        return Platform.YT;
    }
}
