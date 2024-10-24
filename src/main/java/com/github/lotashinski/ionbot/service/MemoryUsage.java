package com.github.lotashinski.ionbot.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MemoryUsage {
	
	private long totalSpace;
	
	private long usedSpace;
	
	
	public double usedCoefficient() {
		return 1.0 * usedSpace / totalSpace;
	}
	
	public double usedPercents() {
		return 100.0 * usedCoefficient();
	}
	
}
