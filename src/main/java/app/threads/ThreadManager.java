package app.threads;

import app.dto.StreamInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static app.threads.Platform.YT;

@Service
public class ThreadManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadManager.class);

	private List<AbstractPlatformThread> threadPool = new ArrayList<>();

	@Nullable
	public AbstractPlatformThread getThread(StreamInfoDto dto) {
		Assert.notNull(dto, "dto is null");

		if (threadPool.stream().anyMatch(t -> Objects.equals(dto.getPlatform(),t.getPlatform()) && t.isAlive())) {
			LOGGER.warn("Only one broadcast is allowed! Wait for the end of its execution or finish it intentionally");
		    return null;
		} else {
			AbstractPlatformThread thread = getCorrespondingThread(dto);
			threadPool.add(thread);
			return thread;
		}
	}

	private AbstractPlatformThread getCorrespondingThread(StreamInfoDto dto) {
		switch(dto.getPlatform()) {
			case YT:
				return new YoutubeThread(dto);
			case FB:
			    return new FacebookThread(dto);
            case VK:
                return new VkontakteThread(dto);
            default:
                throw new IllegalArgumentException("Unknown platform type supplied!");
		}
	}
}
