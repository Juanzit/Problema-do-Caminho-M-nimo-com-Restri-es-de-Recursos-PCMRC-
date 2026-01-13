import java.io.File;
import java.util.Scanner;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        String[] files = {
            "instances/inst_pequena.txt",
            "instances/inst_media.txt",
            "instances/inst_grande.txt",
            "instances/inst_muito_grande.txt"
        };

        System.out.println("===================================================================================");
        System.out.printf("%-20s | %-10s | %-10s | %-10s | %-10s%n", 
                          "Instância", "Nós", "Custo", "Recurso", "Tempo(ms)");
        System.out.println("===================================================================================");

        for (String filePath : files) {
            runTest(filePath);
        }
    }

    public static void runTest(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                System.out.println("Arquivo não encontrado: " + filePath);
                return;
            }
            Scanner sc = new Scanner(f);

            int n = sc.nextInt();
            int m = sc.nextInt();
            int source = sc.nextInt();
            int target = sc.nextInt();
            double R = sc.nextDouble();

            Graph g = new Graph(n);
            for (int i = 0; i < m; i++) {
                if (sc.hasNextInt()) {
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    double cost = sc.nextDouble();
                    double resource = sc.nextDouble();
                    g.addEdge(u, v, cost, resource);
                }
            }
            sc.close();

            long start = System.currentTimeMillis();
            
            // Setup do Simulated Annealing
            // Extrair parâmetros (Temp, Cooling) para config externa se for rodar bateria de testes.
            SimulatedAnnealing sa = new SimulatedAnnealing(g, source, target, R);
            Solution best = sa.run();
            
            long end = System.currentTimeMillis();

            System.out.printf("%-20s | %-10d | %-10.2f | %-10.2f | %-10d%n", 
                              f.getName(), 
                              n, 
                              best.cost, 
                              best.resource, 
                              (end - start)); // Wall-clock time ignorando I/O

        } catch (Exception e) {
            System.out.println("Erro ao processar " + filePath + ": " + e.getMessage());
        }
    }
}