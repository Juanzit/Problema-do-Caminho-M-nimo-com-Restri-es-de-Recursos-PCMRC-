import java.util.*;

public class Solution {
    List<Integer> path;
    double cost;
    double resource;
    double fitness;
    boolean reachesTarget; 

    public Solution(List<Integer> path) {
        this.path = new ArrayList<>(path);
    }

    public void evaluate(Graph g, double R, double penalty, int target) {
        cost = 0;
        resource = 0;
        reachesTarget = false;

        if (path.isEmpty()) {
            fitness = Double.MAX_VALUE;
            return;
        }
        if (path.get(path.size() - 1) == target) {
            reachesTarget = true;
        }

        for (int i = 0; i < path.size() - 1; i++) {
            int u = path.get(i);
            int v = path.get(i + 1);
            boolean edgeFound = false;

            for (Edge e : g.getNeighbors(u)) {
                if (e.to == v) {
                    cost += e.cost;
                    resource += e.resource;
                    edgeFound = true;
                    break;
                }
            }
            // Se o caminho tiver uma aresta inexistente (erro de lógica), penaliza
            if (!edgeFound) {
                fitness = Double.MAX_VALUE; 
                return;
            }
        }

        fitness = cost;

        // Penalidade por violação de recurso 
        if (resource > R) {
            fitness += penalty * (resource - R);
        }

        // Penalidade se não chegar no destino 
        if (!reachesTarget) {
            fitness += 1000000;
        }
    }
}