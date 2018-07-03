package com.problem1.model;

import java.util.Date;

import com.problem1.model.Airport;
import com.problem1.model.Gate;



public class StandardScheduledFlight implements ScheduledFlight {

	private final String id;
	private final Airport source;
	private final Airport destination;
	private Gate departureGate;
	private Gate arrivalGate;
	private Date departureTime;
	private Date arrivalTime;


	public StandardScheduledFlight(String id, Airport source, Airport destination, Gate departureGate, Gate arrivalGate,
			Date departureTime, Date arrivalTime) {

		this.id = id;
		this.source = source;
		this.destination = destination;
		this.departureGate = departureGate;
		this.arrivalGate = arrivalGate;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}


	public StandardScheduledFlight(String id, Airport source, Airport destination) {
		this(id, source, destination, null, null, null, null);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Airport getSource() {
		return source;
	}

	@Override
	public Airport getDestination() {
		return destination;
	}

	@Override
	public Gate getDepartureGate() {
		return departureGate;
	}

	@Override
	public void setDepartureGate(Gate departureGate) {
		this.departureGate = departureGate;
	}

	@Override
	public Gate getArrivalGate() {
		return arrivalGate;
	}

	@Override
	public void setArrivalGate(Gate arrivalGate) {
		this.arrivalGate = arrivalGate;
	}

	@Override
	public Date getDepartureTime() {
		Date date = null;

		if (null != departureTime) {
			date = new Date(departureTime.getTime());
		}

		return date;
	}

	@Override
	public void setDepartureTime(Date departureTime) {
		Date date = null;

		if (null != departureTime) {
			date = new Date(departureTime.getTime());
		}

		this.departureTime = date;
	}

	@Override
	public Date getArrivalTime() {
		Date date = null;

		if (null != arrivalTime) {
			date = new Date(arrivalTime.getTime());
		}

		return date;
	}

	@Override
	public void setArrivalTime(Date arrivalTime) {
		Date date = null;

		if (null != arrivalTime) {
			date = new Date(arrivalTime.getTime());
		}

		this.arrivalTime = date;
	}
}