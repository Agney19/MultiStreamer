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
	public AbstractPlatformThread getThread(StreamInfoDto dto, String operation) {
		Assert.notNull(dto, "dto is null");

        AbstractPlatformThread thread;
        thread = threadPool.stream()
                .filter(t -> Objects.equals(dto.getPlatform(), t.getPlatform()) && t.isAlive())
                .findFirst()
                .orElse(null);
        if (operation.equals("delete")) {
            if (thread == null) {
                LOGGER.warn("No corresponding thread exists!");
                return null;
            } else {
                threadPool.remove(thread);
                return thread;
            }
        } else if (operation.equals("create")) {
            if (thread != null) {
                LOGGER.warn("Only one broadcast is allowed! Wait for the end of its execution or finish it intentionally");
                return null;
            } else {
                thread = getCorrespondingThread(dto);
                threadPool.add(thread);
                return thread;
            }
        } else {
            throw new IllegalArgumentException("Unknown operation!");
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
