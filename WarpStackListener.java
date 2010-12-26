
import java.util.HashMap;
import java.util.ArrayList;

public class WarpStackListener extends PluginListener {

    private LocationDataStore locations = new LocationDataStore();

    private void updateWarpState(Player player) {
        player.teleportTo(locations.getLocation(player));
        player.sendMessage(Colors.Rose + "Woosh!");
    }

    private void noMoreSlots(Player player) {
        player.sendMessage(Colors.Rose + "You are at your maximum for warp slots.");
    }

    public boolean pushWarpSpawn(Player player) {
        if (locations.pushLocation(player, etc.getServer().getSpawnLocation(), "spawn")) {
            updateWarpState(player);
            return true;
        } else {
            noMoreSlots(player);
            return false;
        }
    }

    public boolean pushWarp(Player player, Warp warp) {
        if (locations.pushLocation(player, warp.Location, warp.Name)) {
            updateWarpState(player);
            return true;
        } else {
            noMoreSlots(player);
            return false;
        }
    }

    public boolean pushWarp(Player player, Player warp) {
        if (locations.pushLocation(player, warp.getLocation(), "tp:" + warp.getName())) {
            updateWarpState(player);
            return true;
        } else {
            noMoreSlots(player);
            return false;
        }
    }

    public boolean popWarp(Player player) {
        if (locations.popLocation(player)) {
            updateWarpState(player);
            return true;
        } else {
            player.sendMessage(Colors.Rose + "No previous warps.");
            return false;
        }
    }

    public void rotateWarps(Player player, int rotateBy) {
        locations.rotate(player, rotateBy);
        updateWarpState(player);
    }

    public boolean onCommand(Player player, String[] split) {
        if (split[0].equalsIgnoreCase("/swarp") && player.canUseCommand("/warp") && player.canUseCommand("/swarp")) {
            if (split.length < 2) {
                player.sendMessage(Colors.Rose + "Correct usage is: /swarp [warpname]");
                return true;
            }

            Warp warp = etc.getDataSource().getWarp(split[1]);
            if (warp == null || (!player.isInGroup(warp.Group) && !warp.Group.equals(""))) {
                player.sendMessage(Colors.Rose + "Warp not found.");
                return true;
            }

            pushWarp(player, warp);
            return true;
        }

        if (split[0].equalsIgnoreCase("/sspawn") && player.canUseCommand("/spawn") && player.canUseCommand("/sspawn")) {
            pushWarpSpawn(player);
            return true;
        }

        if (split[0].equalsIgnoreCase("/shome") && player.canUseCommand("/home") && player.canUseCommand("/shome")) {
            Warp home = null;
            if (split.length > 1 && player.isAdmin()) {
                home = etc.getDataSource().getHome(split[1]);
            } else {
                home = etc.getDataSource().getHome(player.getName());
            }

            if (home != null)
                pushWarp(player, home);
            else if (split.length > 1 && player.isAdmin())
                player.sendMessage(Colors.Rose + "That player home does not exist");
            else
                pushWarpSpawn(player);

            return true;
        }

        if (split[0].equalsIgnoreCase("/stp") && player.canUseCommand("/tp") && player.canUseCommand("/stp")) {
            if (split.length < 2) {
                player.sendMessage(Colors.Rose + "Correct usage is: /stp [player]");
                return true;
            }

            Player other = etc.getServer().matchPlayer(split[1]);

            if (other != null) {
                if (player.getName().equalsIgnoreCase(other.getName())) {
                    player.sendMessage(Colors.Rose + "You're already here!");
                    return true;
                }

                pushWarp(player, other);
            } else
                player.sendMessage(Colors.Rose + "Can't find user " + split[1] + ".");
            return true;
        }

        if (split[0].equalsIgnoreCase("/stphere") && player.canUseCommand("/tphere") && player.canUseCommand("/stphere")) {
            if (split.length < 2) {
                player.sendMessage(Colors.Rose + "Correct usage is: /stphere [player]");
                return true;
            }

            Player other = etc.getServer().matchPlayer(split[1]);

            if (other != null) {
                if (player.getName().equalsIgnoreCase(other.getName())) {
                    player.sendMessage(Colors.Rose + "Wow look at that! You teleported yourself to yourself!");
                    return true;
                }

                pushWarp(other, player);
            } else
                player.sendMessage(Colors.Rose + "Can't find user " + split[1] + ".");
            return true;
        }

        if (split[0].equalsIgnoreCase("/sback") && player.canUseCommand("/sback")) {
            popWarp(player);
            return true;
        }

        if (split[0].equalsIgnoreCase("/swlist") && player.canUseCommand("/swlist")) {
            StringBuffer message = new StringBuffer(Colors.Rose + "Your stack: " + Colors.LightBlue);
            int size = locations.getSize(player), active = locations.getActive(player);
            for (int i = 0; i < size; i ++) {
                if (i == active) {
                    message.append(Colors.Yellow + locations.getName(player, i));
                    if (i < size-1) message.append("  " + Colors.LightBlue);
                } else {
                    message.append(locations.getName(player, i));
                    if (i < size-1) message.append("  ");
                }
            }

            player.sendMessage(message.toString());
            return true;
        }

        if (split[0].equalsIgnoreCase("/smove") && player.canUseCommand("/smove")) {
            int rotateBy = 1;

            if (split.length > 1)
                try {
                    rotateBy = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) { }

            rotateWarps(player, rotateBy);
            return true;
        }

        return false;
    }
}
