package myInterviews.Google;

import org.testng.annotations.Test;

import java.util.*;

/**
 * Write a class that uses the following methods in Java.
 *
 * public void Init(List<String> servers){
 * }
 *
 * public String allocateServer(String serverType){
 * }
 *
 * public String deallocateServer(String server){
 * }
 *
 * In  Init(List<String> servers) method the input servers will be list of servers with the server name in the schema "<type>-<index>"
 * eg:  ("db-1","db-2", "api-3", "api-4")
 *
 * allocateServer method should return server name with <type>-<index>.
 * If there any missing index for the requested server type, first the minimum missing index should be used else the next maximum index in that server type should be used. Eg: when allocateServer is called with "api" server type. The current missing indices are 1 and 2. so the new server name should be "api-1".
 * when allocateServer is called with "db" server type. As there are no missin index the server name should be "db-3"
 *
 * When the deallocateServer is called, the corresponding server should be removed/deallocated.
 */

public class ServerAllocator {
    private Map<String, TreeSet<Integer>> serverIndices;

    public ServerAllocator() {
        serverIndices = new HashMap<>();
    }

    // Initialize the server list
    public void Init(List<String> servers) {
        serverIndices.clear();
        for (String server : servers) {
            String[] parts = server.split("-");
            String type = parts[0];
            int index = Integer.parseInt(parts[1]);

            serverIndices.putIfAbsent(type, new TreeSet<>());
            serverIndices.get(type).add(index);
        }
    }

    // Allocate a new server
    public String allocateServer(String serverType) {
        serverIndices.putIfAbsent(serverType, new TreeSet<>());

        TreeSet<Integer> indices = serverIndices.get(serverType);

        // Find the minimum missing index
        int newIndex = 1; // Start at 1
        for (int index : indices) {
            if (index != newIndex) break; // Found a gap
            newIndex++;
        }

        // Add the new index to the set
        indices.add(newIndex);

        return serverType + "-" + newIndex;
    }

    // Deallocate an existing server
    public String deallocateServer(String server) {
        String[] parts = server.split("-");
        String type = parts[0];
        int index = Integer.parseInt(parts[1]);

        TreeSet<Integer> indices = serverIndices.get(type);

        if (indices != null && indices.contains(index)) {
            indices.remove(index); // Remove the index
        }

        return server + " deallocated";
    }


    @Test
    public void test() {
        ServerAllocator allocator = new ServerAllocator();

        // Example initialization
        List<String> servers = Arrays.asList("db-1", "db-2", "api-3", "api-4");
        allocator.Init(servers);

        // Allocate servers
        System.out.println(allocator.allocateServer("api")); // Expected: "api-1"
        System.out.println(allocator.allocateServer("db"));  // Expected: "db-3"

        // Deallocate a server
        System.out.println(allocator.deallocateServer("api-3")); // Expected: "api-3 deallocated"

        // Allocate again
        System.out.println(allocator.allocateServer("api")); // Expected: "api-3" (reuses deallocated index)
        System.out.println(allocator.allocateServer("db"));

        System.out.println(allocator.deallocateServer("db-2"));
        System.out.println(allocator.allocateServer("db"));
    }
}

