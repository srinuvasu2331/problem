package com.problem1.model;


public class StandardBaggagePoint implements BaggagePoint {

	private final String id;
	private final String name;


	public StandardBaggagePoint(String id, String name) {

		this.id = id;
		this.name = name;
	}


	public StandardBaggagePoint(String id) {
		this(id, "");
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
}