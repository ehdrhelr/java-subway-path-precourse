package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteRepository {
    private static final List<Route> routes = new ArrayList<>();

    public static List<Route> routes() {
        return Collections.unmodifiableList(routes);
    }

    public static void addRoute(Route route) {
        routes.add(route);
    }

    public static Route getRouteByLineName(String name) {
        return routes().stream()
                .filter(route -> route.getLine().getName().equals(name))
                .findFirst()
                .get();
    }

}
