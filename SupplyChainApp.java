import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class SupplyChainApp {
    // Map to store the supply chain: key = entity, value = list of entities it connects to
    private Map<String, List<String>> supplyChain;

    // Constructor
    public SupplyChainApp() {
        supplyChain = new HashMap<>();
    }

    // Add an entity (supplier, manufacturer, etc.)
    public void addEntity(String entity) {
        if (!supplyChain.containsKey(entity)) {
            supplyChain.put(entity, new ArrayList<>());
            System.out.println(entity + " added to the supply chain.");
        } else {
            System.out.println(entity + " already exists in the supply chain.");
        }
    }

    // Add a flow from one entity to another (edge)
    public void addFlow(String from, String to) {
        if (supplyChain.containsKey(from) && supplyChain.containsKey(to)) {
            supplyChain.get(from).add(to);
            System.out.println("Flow added from " + from + " to " + to);
        } else {
            System.out.println("Invalid entities.");
        }
    }

    // Remove a flow between entities
    public void removeFlow(String from, String to) {
        if (supplyChain.containsKey(from)) {
            List<String> connections = supplyChain.get(from);
            if (connections.remove(to)) {
                System.out.println("Flow removed from " + from + " to " + to);
            } else {
                System.out.println("No flow exists from " + from + " to " + to);
            }
        } else {
            System.out.println("Invalid source entity.");
        }
    }

    // Remove an entity and all associated flows
    public void removeEntity(String entity) {
        if (supplyChain.containsKey(entity)) {
            supplyChain.remove(entity);
            // Remove this entity from any connections (inflows)
            for (List<String> connections : supplyChain.values()) {
                connections.remove(entity);
            }
            System.out.println(entity + " removed from the supply chain.");
        } else {
            System.out.println(entity + " not found in the supply chain.");
        }
    }

    // Display the entire supply chain (graph)
    public void displaySupplyChain() {
        if (supplyChain.isEmpty()) {
            System.out.println("The supply chain is empty.");
            return;
        }

        System.out.println("Supply Chain Network:");
        for (Map.Entry<String, List<String>> entry : supplyChain.entrySet()) {
            String entity = entry.getKey();
            List<String> connections = entry.getValue();
            System.out.println(entity + " flows to: " + connections);
        }
    }

    // Find the shortest path from a source entity to the destination using BFS
    public void findShortestPath(String start, String end) {
        if (!supplyChain.containsKey(start) || !supplyChain.containsKey(end)) {
            System.out.println("Invalid start or end entity.");
            return;
        }

        // BFS to find the shortest path
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        List<String> initialPath = new ArrayList<>();
        initialPath.add(start);
        queue.add(initialPath);
        visited.add(start);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String lastNode = path.get(path.size() - 1);

            if (lastNode.equals(end)) {
                System.out.println("Shortest path from " + start + " to " + end + ": " + path);
                return;
            }

            for (String neighbor : supplyChain.get(lastNode)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
        System.out.println("No path found from " + start + " to " + end);
    }

    // Main method to interact with the Supply Chain System
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SupplyChainApp scn = new SupplyChainApp();

        while (true) {
            System.out.println("\nWelcome to the Dynamic Supply Chain System");
            System.out.println("1. Add an Entity");
            System.out.println("2. Add a Flow");
            System.out.println("3. Remove a Flow");
            System.out.println("4. Remove an Entity");
            System.out.println("5. Display Supply Chain");
            System.out.println("6. Find Shortest Path");
            System.out.println("7. Exit");
            System.out.print("Choose an option (1/2/3/4/5/6/7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            if (choice == 1) {
                System.out.print("Enter entity name to add: ");
                String entity = scanner.nextLine();
                scn.addEntity(entity);
            } else if (choice == 2) {
                System.out.print("Enter the source entity: ");
                String from = scanner.nextLine();
                System.out.print("Enter the destination entity: ");
                String to = scanner.nextLine();
                scn.addFlow(from, to);
            } else if (choice == 3) {
                System.out.print("Enter the source entity: ");
                String from = scanner.nextLine();
                System.out.print("Enter the destination entity: ");
                String to = scanner.nextLine();
                scn.removeFlow(from, to);
            } else if (choice == 4) {
                System.out.print("Enter entity name to remove: ");
                String entity = scanner.nextLine();
                scn.removeEntity(entity);
            } else if (choice == 5) {
                scn.displaySupplyChain();
            } else if (choice == 6) {
                System.out.print("Enter start entity: ");
                String startEntity = scanner.nextLine();
                System.out.print("Enter end entity: ");
                String endEntity = scanner.nextLine();
                scn.findShortestPath(startEntity, endEntity);
            } else if (choice == 7) {
                System.out.println("Exiting the program...");
                break;
            } else {
                System.out.println("Invalid option. Please choose a valid option.");
            }
        }

        scanner.close();
    }
}
