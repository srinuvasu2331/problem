package com.problem1.model;

import com.problem1.model.BaggagePoint;
import com.problem1.model.ScheduledFlight;


public class StandardCheckedBag implements CheckedBag {

	private String id;
	private ScheduledFlight scheduledFlight;
	private BaggagePoint entryPoint;


	public StandardCheckedBag(String id, ScheduledFlight scheduledFlight, BaggagePoint entryPoint) {

		this.id = id;
		this.scheduledFlight = scheduledFlight;
		this.entryPoint = entryPoint;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ScheduledFlight getScheduledFlight() {
		return scheduledFlight;
	}

	@Override
	public BaggagePoint getEntryPoint() {
		return entryPoint;
	}
}