package subway.control;

import subway.domain.Route;
import subway.domain.RouteRepository;
import subway.enums.initializer.InitialLines;
import subway.enums.initializer.InitialStations;
import subway.enums.menu.MainMenu;
import subway.enums.menu.RouteSearchMenu;
import subway.service.DistanceNavigator;
import subway.service.TimeNavigator;
import subway.view.MainView;
import subway.view.RouteView;

import java.util.List;
import java.util.Scanner;

public class RouteControlCenter {

    public String startRouteControl(Scanner scanner) {
        RouteView.printRouteMenu();
        MainView.askInputMenu();
        String command = MainControlCenter.inputCommand(scanner);
        String menu = selectRouteMenu(command, scanner);
        return MainMenu.SEARCH_ROUTE.getCommand();
    }

    private String selectRouteMenu(String command, Scanner scanner) {
        if (command.equals(RouteSearchMenu.SHORTEST_DISTANCE.getCommand()))
            return searchShortestDistance(scanner);
        if (command.equals(RouteSearchMenu.MINIMUM_TIME.getCommand()))
            return searchMinimumTime(scanner);
        if (command.equalsIgnoreCase(RouteSearchMenu.BACK.getCommand()))
            return RouteSearchMenu.BACK.getCommand();
        return "";
    }

    private String searchShortestDistance(Scanner scanner) {
        String departure = inputDeparture(scanner);
        String arrival = inputArriaval(scanner);
        List<String> shortestPath = DistanceNavigator.getShortestPath(departure, arrival);
        int shortestDistance = getShortestDistance(departure, arrival);
        int time = getTime(shortestPath);
        RouteView.printResult(shortestDistance, time, shortestPath);
        return RouteSearchMenu.SHORTEST_DISTANCE.getCommand();
    }

    private String searchMinimumTime(Scanner scanner) {
        String departure = inputDeparture(scanner);
        String arrival = inputArriaval(scanner);
        List<String> minimumPath = TimeNavigator.getShortestPath(departure, arrival);
        int minimumTime = getMinimumTime(departure, arrival);
        int distance = getDistance(minimumPath);
        RouteView.printResult(distance, minimumTime, minimumPath);
        return RouteSearchMenu.SHORTEST_DISTANCE.getCommand();
    }

    private String inputDeparture(Scanner scanner) {
        RouteView.askInputDeparture();
        return MainControlCenter.inputCommand(scanner);
    }

    private String inputArriaval(Scanner scanner) {
        RouteView.askInputArrival();
        return MainControlCenter.inputCommand(scanner);
    }

    private int getShortestDistance(String departure, String arrival) {
        List<String> shortestPath = DistanceNavigator.getShortestPath(departure, arrival);
        Route route = RouteRepository.getRouteByLineName(InitialLines.LINE_2.getName());
        List<InitialStations> stations = route.getStations();
        List<Integer> distances = route.getDistanceToNextStation();
        int distance = 0;
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(shortestPath.get(shortestPath.size() - 1)) || i == stations.size() - 1) continue;
            distance += distances.get(i);
        }
        return distance;
    }

    private int getDistance(List<String> shortestPath) {
        Route route = RouteRepository.getRouteByLineName(InitialLines.LINE_2.getName());
        List<InitialStations> stations = route.getStations();
        List<Integer> distances = route.getDistanceToNextStation();
        int distance = 0;
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(shortestPath.get(shortestPath.size() - 1)) || i == stations.size() - 1) continue;
            distance += distances.get(i);
        }
        return distance;
    }

    private int getMinimumTime(String departure, String arrival) {
        List<String> minimumPath = TimeNavigator.getShortestPath(departure, arrival);
        Route route = RouteRepository.getRouteByLineName(InitialLines.LINE_2.getName());
        List<InitialStations> stations = route.getStations();
        List<Integer> times = route.getTimeToNextStation();
        int time = 0;
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(minimumPath.get(minimumPath.size() - 1)) || i == stations.size() - 1) continue;
            time += times.get(i);
        }
        return time;
    }

    private int getTime(List<String> minimumPath) {
        Route route = RouteRepository.getRouteByLineName(InitialLines.LINE_2.getName());
        List<InitialStations> stations = route.getStations();
        List<Integer> times = route.getTimeToNextStation();
        int time = 0;
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(minimumPath.get(minimumPath.size() - 1)) || i == stations.size() - 1) continue;
            time += times.get(i);
        }
        return time;
    }
}
