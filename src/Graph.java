import java.util.*;

public class Graph {
    int n;
    // Lista de Adjacência
    List<List<Edge>> adj;

    public Graph(int n) {
        this.n = n;
        adj = new ArrayList<>();
        // Inicializa slots para evitar NullPointer depois
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, double cost, double resource) {
        // Grafo Direcionado (u -> v). Se a instância for não-direcionada, precisa duplicar a aresta (v -> u).
        adj.get(u).add(new Edge(v, cost, resource));
    }

    public List<Edge> getNeighbors(int u) {
        return adj.get(u);
    }
}