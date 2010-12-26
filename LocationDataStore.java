
import java.util.HashMap;

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

    public boolean pushLocation(Player player, Location loc) {
        LocationDataStack stack = get(player);
        if (!stack.canAddSlot())
            return false;
        stack.setLocation(player.getLocation());
        stack.addSlot(loc);
        return true;
    }

    public boolean popLocation(Player player) {
        return get(player).removeSlot();
    }

    public void rotate(Player player, int by) {
        LocationDataStack stack = get(player);
        stack.setLocation(player.getLocation());
        stack.rotate(by);
    }

    public Location getLocation(Player player) {
        return get(player).getLocation();
    }
}
