import java.io.*;
import java.util.*;

public class InstanceGenerator {

    public static void main(String[] args) throws IOException {
        Object[][] configs = {
            {20, 50, "inst_pequena.txt"},
            {50, 200, "inst_media.txt"},
            {100, 800, "inst_grande.txt"},
            {200, 2000, "inst_muito_grande.txt"}
        };

        for (Object[] cfg : configs) {
            int n = (int) cfg[0];
            int m = (int) cfg[1];
            String filename = "instances/" + (String) cfg[2];
            
            new File("instances").mkdirs();
            
            generate(n, m, filename);
            System.out.println("Gerado: " + filename);
        }
    }

    public static void generate(int n, int m, String filename) throws IOException {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        int source = 0;
        int target = n - 1;
        
        // Heurística para R: Valor alto o suficiente para permitir soluções,
        // mas baixo o suficiente para não trivializar o problema (virar Shortest Path comum).
        double R = (n * 5.0); 

        // Header spec: N M Source Target R
        sb.append(n).append(" ").append(m).append(" ")
          .append(source).append(" ").append(target).append(" ")
          .append(String.format(Locale.US, "%.2f", R)).append("\n");

        Set<String> existingEdges = new HashSet<>();
        List<String> edgesLines = new ArrayList<>();

        // Garante viabilidade: Cria um caminho "backbone" (0 -> 1 -> ... -> Target).
        int current = source;
        for (int i = 1; i < n; i++) {
            addEdge(current, i, rand, edgesLines, existingEdges);
            current = i;
        }

        // Adiciona as arestas restantes aleatoriamente para criar complexidade.
        int edgesToGenerate = m - (n - 1);
        for (int i = 0; i < edgesToGenerate; i++) {
            int u = rand.nextInt(n);
            int v = rand.nextInt(n);
            
            // Grafo simples
            if (u != v && !existingEdges.contains(u + "-" + v)) {
                addEdge(u, v, rand, edgesLines, existingEdges);
            } else {
                i--; 
            }
        }

        for (String line : edgesLines) {
            sb.append(line).append("\n");
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.print(sb.toString());
        }
    }

    private static void addEdge(int u, int v, Random rand, List<String> lines, Set<String> exist) {
        // Ranges arbitrários: Cost [1, 21), Resource [1, 11)
        double cost = 1.0 + rand.nextDouble() * 20.0; 
        double resource = 1.0 + rand.nextDouble() * 10.0; 
        
        lines.add(u + " " + v + " " + 
                  String.format(Locale.US, "%.2f", cost) + " " + 
                  String.format(Locale.US, "%.2f", resource));
        exist.add(u + "-" + v);
    }
}