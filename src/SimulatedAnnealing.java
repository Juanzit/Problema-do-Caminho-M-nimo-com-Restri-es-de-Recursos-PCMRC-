import java.util.*;

public class SimulatedAnnealing {
    Graph g;
    int source, target;
    double R;

    double penalty = 100.0; 
    double temp = 1000.0;   
    double cooling = 0.99;  
    double tempMin = 0.01;  
    int maxIter = 50000;    
    public int iterationsDone = 0;

    Random rand = new Random();

    public SimulatedAnnealing(Graph g, int source, int target, double R) {
        this.g = g;
        this.source = source;
        this.target = target;
        this.R = R;
    }

    public Solution initialSolution() {
        for(int i=0; i<1000; i++) { 
             List<Integer> path = generateRandomPath();
             if(path != null && !path.isEmpty() && path.get(path.size()-1) == target) {
                 Solution s = new Solution(path);
                 s.evaluate(g, R, penalty, target);
                 return s;
             }
        }
        
        // retorna start node isolado (fitness horrível) se falhar, só pra não crashar o pipeline.
        System.out.println("Aviso: Solução inicial válida não encontrada.");
        Solution s = new Solution(new ArrayList<>(Collections.singletonList(source)));
        s.evaluate(g, R, penalty, target);
        return s;
    }

    private List<Integer> generateRandomPath() {
        List<Integer> path = new ArrayList<>();
        path.add(source);
        int current = source;
        boolean[] visited = new boolean[g.n];
        visited[source] = true;

        while (current != target) {
            List<Edge> neighbors = g.getNeighbors(current);
            if (neighbors.isEmpty()) return null; 

            List<Integer> validNeighbors = new ArrayList<>();
            for(Edge e : neighbors) {
                if(!visited[e.to]) validNeighbors.add(e.to);
            }

            if(validNeighbors.isEmpty()) return null; 

            int next = validNeighbors.get(rand.nextInt(validNeighbors.size()));
            visited[next] = true;
            path.add(next);
            current = next;
        }
        return path;
    }

    // Estratégia de Vizinhança
    public Solution neighbor(Solution s) {
        if (s.path.size() < 3) return initialSolution(); 

        List<Integer> newPath = new ArrayList<>(s.path);

        int i = rand.nextInt(newPath.size() - 1);
        int j = rand.nextInt(newPath.size() - i) + i; 
        
        while(newPath.size() > i + 1) {
            newPath.remove(newPath.size()-1);
        }
        
        reconnectPath(newPath, target);

        Solution ns = new Solution(newPath);
        ns.evaluate(g, R, penalty, target);
        return ns;
    }

    private void reconnectPath(List<Integer> path, int subTarget) {
        int current = path.get(path.size()-1);
        int steps = 0;
        Set<Integer> visitedInPath = new HashSet<>(path);

        while(current != subTarget && steps < g.n) {
            List<Edge> neighbors = g.getNeighbors(current);
            if(neighbors.isEmpty()) break;

            List<Edge> candidates = new ArrayList<>();
            for(Edge e : neighbors) {
                if(!visitedInPath.contains(e.to)) candidates.add(e);
            }
            
            if(candidates.isEmpty()) break;

            Edge next = candidates.get(rand.nextInt(candidates.size()));
            path.add(next.to);
            visitedInPath.add(next.to);
            current = next.to;
            steps++;
        }
    }

    public Solution run() {
        Solution current = initialSolution();
        Solution best = current;
        
        if(!current.reachesTarget) {
             current = initialSolution();
             best = current;
        }

        int iter = 0;
        System.out.println("Iniciando SA... Temp Inicial: " + temp);

        while (temp > tempMin && iter < maxIter) {
            Solution next = neighbor(current);

            double delta = next.fitness - current.fitness;

            // Critério de Metropolis:
            // Delta < 0: Melhora (Aceita)
            // Delta > 0: Piora (Aceita com probabilidade e^(-delta/T))
            if (delta < 0) {
                current = next;
                // Keep track do Best Global apenas se for viável
                if (current.fitness < best.fitness && current.reachesTarget) {
                    best = current;
                    System.out.println("Nova melhor solução: " + best.fitness + " (Iter " + iter + ")");
                }
            } else {
                if (Math.exp(-delta / temp) > rand.nextDouble()) {
                    current = next;
                }
            }

            temp *= cooling;
            iter++;
        }
        this.iterationsDone = iter;
        return best;
    }
}