package akki697222.hideandseek.core;

public class MapUtil {
    private static boolean checkValidMap(GameMap map) {
        return map != null &&
                map.getName() != null &&
                map.getDescription() != null &&
                map.getCredits() != null &&
                map.getTitle() != null &&
                map.getHiderSpawn() != null &&
                map.getSeekerSpawn() != null &&
                map.getSpectatorSpawn() != null &&
                map.getExecutableCommands() != null;
    }
}
