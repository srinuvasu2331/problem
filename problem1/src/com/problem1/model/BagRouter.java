package com.problem1.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.problem1.model.BaggagePoint;


public class BagRouter {

	private static final Logger LOGGER = Logger.getLogger(BagRouter.class.getName());

	private static final String OUTPUT_DISTANCE_FOR_UNCONNECTED_POINTS = "Not Connected";

	private Map<BaggagePoint, Map<BaggagePoint, ShortestRoute>> shortestPathsNetwork;

	private class ShortestRoute {
		Double distance;
		List<BaggagePoint> path;
		DecimalFormat decimalFormat;

		ShortestRoute(Double distance, List<BaggagePoint> path) {
			this.distance = distance;
			this.path = path;
			this.decimalFormat = new DecimalFormat("0.#");
		}

		@Override
		public String toString() {
			StringBuffer shortestPath = new StringBuffer();

			if (null != path) {
				for (BaggagePoint baggagePoint : path) {
					shortestPath.append(String.format("%s ", baggagePoint.getId()));
				}
				shortestPath.append(String.format(": %s", decimalFormat.format(distance)));
			}

			return shortestPath.toString();
		}
	}

	public BagRouter() {
		shortestPathsNetwork = new HashMap<BaggagePoint, Map<BaggagePoint, ShortestRoute>>();
	}

	public String route(BaggagePoint source, BaggagePoint destination) {

		if (!shortestPathsNetwork.containsKey(source)) {
			throw new IllegalArgumentException(
					String.format("Baggage point source %s cannot be found.", source.getId()));
		}

		if (!shortestPathsNetwork.containsKey(destination)) {
			throw new IllegalArgumentException(
					String.format("Baggage point destination %s cannot be found.", destination.getId()));
		}

		String shortestRoute;

		if (source.equals(destination)) {
			List<BaggagePoint> path = new ArrayList<BaggagePoint>();
			path.add(source);
			path.add(source);
			shortestRoute = new ShortestRoute(0d, path).toString();
		} else {
			if (shortestPathsNetwork.get(source).containsKey(destination)) {
				shortestRoute = shortestPathsNetwork.get(source).get(destination).toString();
			} else {
				shortestRoute = String.format("%s %s : %s", source.getId(), destination.getId(),
						OUTPUT_DISTANCE_FOR_UNCONNECTED_POINTS);
			}
		}

		return shortestRoute;
	}

	public void initialize(Map<BaggagePoint, Map<BaggagePoint, Double>> network) {

		// clear existing memoization
		shortestPathsNetwork.clear();

		// compute shortest paths
		for (Map.Entry<BaggagePoint, Map<BaggagePoint, Double>> shortestPathsForSource : network.entrySet()) {

			List<BaggagePoint> path = new ArrayList<BaggagePoint>();
			path.add(shortestPathsForSource.getKey());

			Set<BaggagePoint> visitedBaggagePoints = new HashSet<BaggagePoint>();
			visitedBaggagePoints.add(shortestPathsForSource.getKey());

			saveShortestPaths(network, path, visitedBaggagePoints, 0d);
		}
	}

	private void saveShortestPaths(Map<BaggagePoint, Map<BaggagePoint, Double>> network, List<BaggagePoint> path,
			Set<BaggagePoint> visitedBaggagePoints, double pathDistance) {

		BaggagePoint source = path.get(path.size() - 1);

		for (Map.Entry<BaggagePoint, Double> destinations : network.get(source).entrySet()) {
			BaggagePoint destination = destinations.getKey();

			if (!visitedBaggagePoints.contains(destination)) {

				List<BaggagePoint> nestedPath = new ArrayList<BaggagePoint>(path);
				nestedPath.add(destination);

				double nestedDistance = pathDistance + destinations.getValue();
				saveShortestPath(path.get(0), destination, nestedPath, nestedDistance);

				Set<BaggagePoint> nestedVisitedBaggagePoints = new HashSet<BaggagePoint>(visitedBaggagePoints);
				nestedVisitedBaggagePoints.add(destination);
				saveShortestPaths(network, nestedPath, nestedVisitedBaggagePoints, nestedDistance);
			}
		}
	}

	private void saveShortestPath(BaggagePoint source, BaggagePoint destination, List<BaggagePoint> path,
			double distance) {
		saveShortestPath(getSavedShortestPaths(source), destination, path, distance);

		List<BaggagePoint> reversePath = new ArrayList<BaggagePoint>(path);
		Collections.reverse(reversePath);
		saveShortestPath(getSavedShortestPaths(destination), source, reversePath, distance);
	}

	private void saveShortestPath(Map<BaggagePoint, ShortestRoute> savedShortestPaths, BaggagePoint destination,
			List<BaggagePoint> path, double distance) {

		if (savedShortestPaths.containsKey(destination)) {
			LOGGER.fine("Entry exists for shortest path to : " + destination.getId());
			ShortestRoute savedShortestPath = savedShortestPaths.get(destination);

			if (savedShortestPath.distance > distance) {
				savedShortestPath.distance = distance;
				savedShortestPath.path = path;
			}
		} else {
			LOGGER.fine("Adding entry for shortest path to : " + destination.getId());
			savedShortestPaths.put(destination, new ShortestRoute(distance, path));
		}
	}

	private Map<BaggagePoint, ShortestRoute> getSavedShortestPaths(BaggagePoint baggagePoint) {
		Map<BaggagePoint, ShortestRoute> savedShortestPaths;

		if (shortestPathsNetwork.containsKey(baggagePoint)) {
			savedShortestPaths = shortestPathsNetwork.get(baggagePoint);
			LOGGER.fine("Entry exists for shortest paths for : " + baggagePoint.getId());
		} else {
			savedShortestPaths = new HashMap<BaggagePoint, ShortestRoute>();
			shortestPathsNetwork.put(baggagePoint, savedShortestPaths);
			LOGGER.fine("Added entry for shortest path for : " + baggagePoint.getId());
		}

		return savedShortestPaths;
	}
}