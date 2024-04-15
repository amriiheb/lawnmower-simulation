package com.mowitnow.mower.batch;

import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class ConsoleItemWriter implements ItemWriter<String> {

	private List<String> mowerPositions;

	public ConsoleItemWriter() {
		mowerPositions = new ArrayList<>();
	}

	@Override
	public void write(List<? extends String> items) throws Exception {
		mowerPositions.addAll(items);
		System.out.println("*********************************HERE IS THE OUTPUT OF THE BATCH*********************************");
		for (String position : items) {
			System.out.println(position);
		}
		System.out.println("**********************************************END************************************************");
	}

	public List<String> getMowerPositions() {
		return mowerPositions;
	}

}
