import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Player {
    private int roomIn;
    private int health;
    private String name;

    public Player(int roomIn, int health, String name) {
        this.roomIn = roomIn;
        this.health = health;
        this.name = name;
    }

    public int getRoomIn() { return roomIn; }
    public int getHealth() { return health; }
    public String getName() { return name; }

    // Format: room-health-name  (same simple format as your project)
    public static void writePlayer(Path file, Player p) throws IOException {
        Files.createDirectories(file.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            bw.write(p.roomIn + "-" + p.health + "-" + p.name);
            bw.newLine();
        }
    }

    public static Player readPlayer(Path file) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line = br.readLine();
            if (line == null || line.isBlank()) throw new IOException("Empty player file: " + file);
            String[] parts = line.split("-", 3);
            if (parts.length < 3) throw new IOException("Invalid player format in: " + file);
            int room = Integer.parseInt(parts[0].trim());
            int hp = Integer.parseInt(parts[1].trim());
            String name = parts[2].trim();
            return new Player(room, hp, name);
        }
    }

    @Override
    public String toString() {
        return "Player{name='" + name + "', roomIn=" + roomIn + ", health=" + health + "}";
    }
}
