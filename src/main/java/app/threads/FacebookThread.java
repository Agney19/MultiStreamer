package app.threads;

import app.dto.StreamInfoDto;

public class FacebookThread extends AbstractPlatformThread {

	public FacebookThread(StreamInfoDto dto) {
		super(dto);
	}

	@Override
	public Platform getPlatform() {
		return Platform.FB;
	}
}
