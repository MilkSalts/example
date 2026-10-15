import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SaveExample {
    public static void main(String[] args) {
        String saveName = "New_Save";
        try {
            // ensure the runtime save exists; if not, copy from repo defaults like ./New_Save/Player
            SaveHelpers.ensureSaveExists(saveName);

            Path playerPath = SavePaths.playerFile(saveName);
            Path roomsPath  = SavePaths.roomsFile(saveName);

            System.out.println("Using save root: " + SavePaths.getSaveRoot().toAbsolutePath());
            System.out.println("Player file -> " + playerPath.toAbsolutePath());
            System.out.println("Rooms file  -> " + roomsPath.toAbsolutePath());

            // Read the default New_Save files from the runtime save location
            Player loaded = Player.readPlayer(playerPath);
            Map<Integer, Room> loadedRooms = Room.readRooms(roomsPath);

            System.out.println("Loaded player: " + loaded);
            System.out.println("Loaded rooms:");
            loadedRooms.values().forEach(r -> System.out.println("  " + r));

            // Example: if the player chooses to save to a different slot later,
            // write to Save1 or Save2 using SavePaths.playerFile("Save1") etc.
            Player p2 = new Player(2, 90, "Bob");
            Map<Integer, Room> r2 = new HashMap<>();
            r2.put(1, new Room(1, "Hall", "A saved hall", false));
            r2.put(2, new Room(2, "Door", "A saved door", true));

            Player.writePlayer(SavePaths.playerFile("Save1"), p2);
            Room.writeRooms(SavePaths.roomsFile("Save1"), r2);

            System.out.println("Example Save1 files written to: " + SavePaths.playerFile("Save1").toAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
