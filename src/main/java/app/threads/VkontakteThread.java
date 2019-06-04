package app.threads;

import app.dto.StreamInfoDto;

public class VkontakteThread extends AbstractPlatformThread {

	public VkontakteThread(StreamInfoDto dto) {
		super(dto);
	}

	@Override
	public Platform getPlatform() {
		return Platform.VK;
	}
}
