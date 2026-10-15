import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class SaveHelpers {
    private SaveHelpers() {}

    /**
     * Ensure the runtime save exists under <project>/saves/<saveName>/.
     * If it does not exist, copy the bundled default files from a repo folder
     * like ./New_Save/Player and ./New_Save/Rooms.
     */
    public static void ensureSaveExists(String saveName) throws IOException {
        Path playerTarget = SavePaths.playerFile(saveName);
        Path roomsTarget = SavePaths.roomsFile(saveName);

        // already present -> nothing to do
        if (Files.exists(playerTarget) && Files.exists(roomsTarget)) {
            return;
        }

        Files.createDirectories(playerTarget.getParent());

        Path defaultPlayer = Path.of(saveName, "Player");
        Path defaultRooms = Path.of(saveName, "Rooms");

        if (Files.exists(defaultPlayer) && !Files.exists(playerTarget)) {
            Files.copy(defaultPlayer, playerTarget, StandardCopyOption.REPLACE_EXISTING);
        }
        if (Files.exists(defaultRooms) && !Files.exists(roomsTarget)) {
            Files.copy(defaultRooms, roomsTarget, StandardCopyOption.REPLACE_EXISTING);
        }

        if (!Files.exists(playerTarget) || !Files.exists(roomsTarget)) {
            throw new IOException(
                "Default save files for '" + saveName + "' were not found. Looked for: "
                + defaultPlayer.toAbsolutePath() + " and " + defaultRooms.toAbsolutePath() + "."
            );
        }
    }
}
