import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public final class SavePaths {
    // Use system property "mygame.save.dir" or env MYGAME_SAVE_DIR, fallback to "saves"
    private static final Path SAVE_ROOT = Optional.ofNullable(System.getProperty("mygame.save.dir"))
            .or(() -> Optional.ofNullable(System.getenv("MYGAME_SAVE_DIR")))
            .map(Paths::get)
            .orElse(Paths.get("saves"));

    private SavePaths() { }

    public static Path getSaveRoot() {
        return SAVE_ROOT;
    }

    public static Path playerFile(String saveName) {
        return SAVE_ROOT.resolve(saveName).resolve("Player");
    }

    // Single Rooms file in the save directory (could be a directory instead)
    public static Path roomsFile(String saveName) {
        return SAVE_ROOT.resolve(saveName).resolve("Rooms");
    }
}
