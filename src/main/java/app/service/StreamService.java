package app.service;

import app.dto.BroadcastInfoDto;
import app.dto.StreamInfoDto;
import app.manager.BroadcastManager;
import app.threads.AbstractPlatformThread;
import app.threads.ThreadManager;
import app.threads.YoutubeThread;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class StreamService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamService.class);

    @Autowired
    private BroadcastManager broadcastManager;
    @Autowired
    private ThreadManager threadManager;

    public void createBroadcast(BroadcastInfoDto dto) {
        broadcastManager.createBroadcast(dto);
        LOGGER.info("Broadcast created");
    }

    public void startStream(StreamInfoDto dto) {
        AbstractPlatformThread thread = threadManager.getThread(dto, "create");
        if (thread != null) {
            thread.start();
            LOGGER.info("Stream started on {}", dto.getPlatform());
        }
    }

    public void finishStream(StreamInfoDto dto) {
        AbstractPlatformThread thread = threadManager.getThread(dto, "delete");
        if (thread == null) {
            LOGGER.warn("Unable to finish inactive stream");
            return;
        }
        try {
            final String[] command = {"/bin/sh",
                    "-c",
                    String.format("ps -x | grep ffmpeg | grep %s | awk '{print $1}'", dto.getOutputUrl())
            };
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String processId;
            while ((processId = input.readLine()) != null) {
                final String killCommand = String.format("kill %s", processId);
                try {
                    Runtime.getRuntime().exec(killCommand).waitFor();
                    LOGGER.info("Process [PID = {}] killed successfully", processId);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
            input.close();
            thread.stop();
        } catch (RuntimeException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}