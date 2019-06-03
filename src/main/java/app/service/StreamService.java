package app.service;

import app.dto.BroadcastInfoDto;
import app.dto.StreamInfoDto;
import app.manager.BroadcastManager;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class StreamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StreamService.class);
	@Autowired
	private BroadcastManager broadcastManager;

	private YoutubeThread youtubeThread;

	public void createBroadcast(BroadcastInfoDto dto) {
		broadcastManager.createBroadcast(dto);
		LOGGER.info("Broadcast created");
	}

	public void startStream(StreamInfoDto dto) {
		if (youtubeThread == null || !youtubeThread.isAlive()) {
			youtubeThread = new YoutubeThread(dto);
			youtubeThread.start();
			LOGGER.info("Stream started");
		} else {
			LOGGER.warn("Only one broadcast is allowed! Wait for the end of its execution or finish it intentionally");
		}
	}

	public void finishStream(StreamInfoDto dto) {
		PlatformThread
		if (youtubeThread == null || !youtubeThread.isAlive()) {
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
			youtubeThread.stop();
		} catch (RuntimeException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}