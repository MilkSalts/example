import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

// Simple Room model with lines stored as:
// -name-id-description-visited
public class Room {
    private final int id;
    private final String name;
    private final String description;
    private final boolean visited;

    public Room(int id, String name, String description, boolean visited) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.visited = visited;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isVisited() { return visited; }

    // Write all rooms into a single Rooms file (one room per line)
    public static void writeRooms(Path file, Map<Integer, Room> rooms) throws IOException {
        Files.createDirectories(file.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            for (Room r : rooms.values()) {
                // use a '-' prefix to match your earlier format but not required
                bw.write("-" + r.getName() + "-" + r.getId() + "-" + r.getDescription() + "-" + r.isVisited());
                bw.newLine();
            }
        }
    }

    // Read rooms from the single Rooms file
    public static Map<Integer, Room> readRooms(Path file) throws IOException {
        Map<Integer, Room> map = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                // Expecting lines like: -Name-1-Description-true
                String[] parts = line.split("-", 5);
                // parts[0] is empty because of leading '-'
                if (parts.length < 5) continue; // skip malformed
                String name = parts[1].trim();
                int id = Integer.parseInt(parts[2].trim());
                String desc = parts[3].trim();
                boolean visited = Boolean.parseBoolean(parts[4].trim());
                map.put(id, new Room(id, name, desc, visited));
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", name='" + name + "', visited=" + visited + "}";
    }
}
