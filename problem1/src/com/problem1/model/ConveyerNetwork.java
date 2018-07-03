package com.problem1.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.problem1.model.BaggagePoint;
import com.problem1.model.CheckedBag;
import com.problem1.model.ScheduledFlight;
import com.problem1.model.StandardBaggagePoint;
import com.problem1.model.StandardCheckedBag;


public class ConveyerNetwork {

	private static final Logger LOGGER = Logger.getLogger(ConveyerNetwork.class.getName());

	private Map<String, CheckedBag> bags;
	private Map<String, BaggagePoint> baggagePoints;


	private Map<BaggagePoint, Map<BaggagePoint, Double>> network;


	public ConveyerNetwork() {
		bags = new LinkedHashMap<String, CheckedBag>();
		baggagePoints = new HashMap<String, BaggagePoint>();
		network = new HashMap<BaggagePoint, Map<BaggagePoint, Double>>();
	}


	public void addBag(String bagId, BaggagePoint entryPoint, ScheduledFlight scheduledFlight) {

		if (bags.containsKey(bagId)) {
			LOGGER.info("Updating scheduled flight and entry point for bag: " + bagId);
		} else {
			LOGGER.info("Adding a new bag: " + bagId);
		}

		bags.put(bagId, new StandardCheckedBag(bagId, scheduledFlight, entryPoint));
	}


	public void addConnection(String baggagePointId, String connectedBaggagePointId, double distance) {


		if (baggagePointId.equals(connectedBaggagePointId)) {
			LOGGER.info("Ignoring connection between same source and destination point: " + baggagePointId);
		} else {
			BaggagePoint baggagePoint = addBaggagePoint(baggagePointId);
			BaggagePoint connectedBaggagePoint = addBaggagePoint(connectedBaggagePointId);

			addConnection(baggagePoint, connectedBaggagePoint, distance);
		}
	}


	public BaggagePoint getBaggagePoint(String baggagePointId) {

		if (!baggagePoints.containsKey(baggagePointId)) {
			LOGGER.severe("Could not find a baggage point with id: " + baggagePointId);
		}

		return baggagePoints.get(baggagePointId);
	}


	public Collection<CheckedBag> getBags() {
		return Collections.unmodifiableCollection(bags.values());
	}


	public Map<BaggagePoint, Map<BaggagePoint, Double>> getNetwork() {
		Map<BaggagePoint, Map<BaggagePoint, Double>> unmodifiableNetwork = new HashMap<BaggagePoint, Map<BaggagePoint, Double>>();

		for (Map.Entry<BaggagePoint, Map<BaggagePoint, Double>> entry : network.entrySet()) {
			unmodifiableNetwork.put(entry.getKey(), Collections.unmodifiableMap(entry.getValue()));
			LOGGER.info("Creating unmodifiable map for: " + entry.getKey().getId());
		}

		return Collections.unmodifiableMap(unmodifiableNetwork);
	}

	private void addConnection(BaggagePoint baggagePoint, BaggagePoint connectedBaggagePoint, double distance) {

		Map<BaggagePoint, Double> adjacencyListForBaggagePoint = getAdjacencyListForBaggagePoint(baggagePoint);
		Map<BaggagePoint, Double> adjacencyListForConnectedBaggagePoint = getAdjacencyListForBaggagePoint(
				connectedBaggagePoint);

		addConnection(adjacencyListForBaggagePoint, connectedBaggagePoint, distance);
		addConnection(adjacencyListForConnectedBaggagePoint, baggagePoint, distance);
	}

	private void addConnection(Map<BaggagePoint, Double> adjacencyListForBaggagePoint,
			BaggagePoint connectedBaggagePoint, double distance) {
		if (adjacencyListForBaggagePoint.containsKey(connectedBaggagePoint)) {
			LOGGER.fine("Updating distance in existing connection for : " + connectedBaggagePoint.getId());
		} else {
			LOGGER.info("Adding connection for : " + connectedBaggagePoint.getId());
		}

		adjacencyListForBaggagePoint.put(connectedBaggagePoint, distance);
	}

	private Map<BaggagePoint, Double> getAdjacencyListForBaggagePoint(BaggagePoint baggagePoint) {

		Map<BaggagePoint, Double> adjacencyListForBaggagePoint;

		if (!network.containsKey(baggagePoint)) {
			adjacencyListForBaggagePoint = new HashMap<BaggagePoint, Double>();
			network.put(baggagePoint, adjacencyListForBaggagePoint);
		} else {
			adjacencyListForBaggagePoint = network.get(baggagePoint);
		}

		return adjacencyListForBaggagePoint;
	}

	private BaggagePoint addBaggagePoint(String baggagePointId) {
		BaggagePoint baggagePoint;

		if (baggagePoints.containsKey(baggagePointId)) {
			baggagePoint = baggagePoints.get(baggagePointId);
		} else {
			LOGGER.info("Adding a new baggagePoint: " + baggagePointId);
			baggagePoint = new StandardBaggagePoint(baggagePointId);
			baggagePoints.put(baggagePointId, baggagePoint);
		}

		return baggagePoint;
	}
}