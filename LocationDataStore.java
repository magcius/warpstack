
import java.util.HashMap;
import java.util.ArrayList;

public class LocationDataStore {

    private HashMap<Player, LocationDataStack> store = new HashMap<Player, LocationDataStack> ();

    private LocationDataStack get(Player player) {
        LocationDataStack stack = store.get(player);
        if (stack == null) {
            stack = new LocationDataStack(player.getLocation());
            store.put(player, stack);
        }
        return stack;
    }

    public boolean pushLocation(Player player, Location loc, String name) {
        LocationDataStack stack = get(player);
        if (!stack.canAddSlot())
            return false;
        stack.setLocation(player.getLocation());
        stack.addSlot(loc, name);
        return true;
    }

    public boolean popLocation(Player player) {
        return get(player).removeSlot();
    }

    public void clear(Player player) {
        LocationDataStack stack = get(player);
        stack.clear(player.getLocation());
    }

    public void rotate(Player player, int by) {
        LocationDataStack stack = get(player);
        stack.setLocation(player.getLocation());
        stack.rotate(by);
    }

    public int getSize(Player player) {
        return get(player).size;
    }

    public int getActive(Player player) {
        return get(player).pointer;
    }

    public String getName(Player player) {
        return get(player).getName();
    }

    public String getName(Player player, int index) {
        return get(player).getName(index);
    }

    public Location getLocation(Player player) {
        return get(player).getLocation();
    }
}
