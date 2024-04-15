package com.mowitnow.mower.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;

public class SingleLineItemReader implements ItemReader<String> {
	private final Resource resource;
	private boolean read = false;

	public SingleLineItemReader(Resource resource) {
		this.resource = resource;
	}

	@Override
	public String read() throws Exception {
		if (!read) {
			read = true;
			return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
		}
		return null; // Return null to indicate the end of reading
	}
}