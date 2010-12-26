
import java.util.HashMap;
import java.util.Stack;

public class WarpStack extends Plugin {
    private Listener listener = new Listener();
    private String name = "WarpStack";
    private String version = "0.1";

    private HashMap<Player,Stack<Location>> locations = new HashMap<Player,Stack<Location>> ();

    public void enable() {
        etc.getInstance().addCommand("/pushwarp", "[warpname] - push a warp to your warp stack");
        etc.getInstance().addCommand("/popwarp", "pop a warp and go back");
    }

    public void disable() {
        etc.getInstance().removeCommand("/pushwarp");
        etc.getInstance().removeCommand("/popwarp");
    }

    public void initialize() {
        etc.getLoader().addListener(
            PluginLoader.Hook.COMMAND,
            listener, this,
            PluginListener.Priority.MEDIUM);
    }

    class Listener extends PluginListener {

        public boolean onCommand(Player player, String[] split) {
            if (split[0].equalsIgnoreCase("/pushwarp") && player.canUseCommand("/pushwarp")) {
                if (split.length < 2) {
                    player.sendMessage(Colors.Rose + "Correct usage is: /pushwarp [warpname]");
                    return true;
                }

                Warp warp = etc.getDataSource().getWarp(split[1]);
                if (warp == null || (!player.isInGroup(warp.Group) && !warp.Group.equals(""))) {
                    player.sendMessage(Colors.Rose + "Warp not found.");
                    return true;
                }

                Stack<Location> stack = locations.get(player);
                if (stack == null) {
                    stack = new Stack<Location>();
                    locations.put(player, stack);
                }

                stack.push(player.getLocation());
                player.teleportTo(warp.Location);
                player.sendMessage(Colors.Rose + "Woosh!");
                return true;
            }

            if (split[0].equalsIgnoreCase("/popwarp") && player.canUseCommand("/popwarp")) {
                Stack<Location> stack = locations.get(player);

                if (stack == null || stack.empty()) {
                    player.sendMessage(Colors.Rose + "No previous warps.");
                    return true;
                }

                player.teleportTo(stack.pop());
                player.sendMessage(Colors.Rose + "Woosh!");
                return true;
            }

            return false;
        }
    }
}