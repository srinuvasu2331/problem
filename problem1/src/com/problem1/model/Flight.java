package com.problem1.model;

import com.problem1.model.Airport;


public interface Flight {

	String getId();


	Airport getSource();


	Airport getDestination();
}