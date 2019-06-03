package app.threads;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ThreadManager {
	@Autowired
	List<AbstractPlatformThread> threads;

	@Nullable
	public AbstractPlatformThread getThread(Platform platform) {
		Assert.notNull(platform, "platform is null");

		return threads.stream()
				.filter(t -> platform.equals(t.getPlatform()))
				.findFirst()
				.orElse(null);
	}
}
