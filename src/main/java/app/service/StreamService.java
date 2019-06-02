package app.service;

import app.dto.BroadcastInfoDto;
import app.dto.StreamInfoDto;
import app.manager.BroadcastManager;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamService {
    @Autowired
    private BroadcastManager broadcastManager;

    public void createBroadcast(BroadcastInfoDto dto) {
        broadcastManager.createBroadcast(dto);
    }

    public void startStream(StreamInfoDto dto) {
        try {
            FFmpeg ffmpeg = new FFmpeg();
            FFprobe ffprobe = new FFprobe();
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(dto.getInputUrl())
                    .addOutput(dto.getOutputUrl())
                    .setFormat("flv")
                    .setAudioCodec("copy")
                    .setVideoCodec("copy")
                    .done();
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            FFmpegJob job = executor.createJob(builder);
            job.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishStream() {
        final Thread thread = Thread.currentThread();
        if(!thread.isInterrupted()) {
            thread.interrupt();
        }
    }
}