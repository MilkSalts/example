import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SaveExample {
    public static void main(String[] args) {
        String saveName = "ExampleSave"; // not a filesystem literal root — SavePaths controls the actual location
        try {
            Path playerPath = SavePaths.playerFile(saveName);
            Path roomsPath  = SavePaths.roomsFile(saveName);

            System.out.println("Using save root: " + SavePaths.getSaveRoot().toAbsolutePath());
            System.out.println("Player file -> " + playerPath.toAbsolutePath());
            System.out.println("Rooms file  -> " + roomsPath.toAbsolutePath());

            // Create sample data
            Player p = new Player(1, 100, "Alice");
            Map<Integer, Room> rooms = new HashMap<>();
            rooms.put(1, new Room(1, "Entrance", "A dimly lit entrance", false));
            rooms.put(2, new Room(2, "Hall", "A long hall with portraits", false));

            // Write files
            Player.writePlayer(playerPath, p);
            Room.writeRooms(roomsPath, rooms);
            System.out.println("Saved player and rooms.");

            // Read them back
            Player loaded = Player.readPlayer(playerPath);
            Map<Integer, Room> loadedRooms = Room.readRooms(roomsPath);

            System.out.println("Loaded player: " + loaded);
            System.out.println("Loaded rooms:");
            loadedRooms.values().forEach(r -> System.out.println("  " + r));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
