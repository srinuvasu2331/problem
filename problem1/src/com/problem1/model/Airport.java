package com.problem1.model;

import java.util.Date;
import java.util.Map;

import com.problem1.model.ScheduledFlight;


public interface Airport {

	String getId();

	String getName();

	Map<String, ScheduledFlight> getScheduledFlights();

	void addScheduledFlight(String flightId, String departureGateId, String destinationAirportId, Date departureTime);

	Gate getGate(String gateId);

	ScheduledFlight getScheduledFlight(String flightId);
}