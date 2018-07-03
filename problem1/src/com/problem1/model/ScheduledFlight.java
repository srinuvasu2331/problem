package com.problem1.model;

import java.util.Date;


public interface ScheduledFlight extends Flight {


	Gate getDepartureGate();


	void setDepartureGate(Gate departureGate);


	Gate getArrivalGate();


	void setArrivalGate(Gate arrivalGate);


	Date getDepartureTime();


	void setDepartureTime(Date departureTime);


	Date getArrivalTime();


	void setArrivalTime(Date arrivalTime);
}