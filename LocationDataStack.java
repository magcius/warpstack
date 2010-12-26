
import java.util.ArrayList;

public class LocationDataStack {

    public ArrayList<Location> store;
    public int size;
    public int pointer;
    public int limit = 32;

    public LocationDataStack(Location initialSlot) {
        store = new ArrayList<Location> (limit);
        store.add(initialSlot);
        size = 1;
    }

    public Location getLocation() {
        return store.get(pointer);
    }

    public void setLocation(Location to) {
        Location loc = getLocation();
        loc.x = to.x; loc.y = to.y; loc.z = to.z;
        loc.rotX = to.rotX; loc.rotY = to.rotY;
    }

    public boolean canAddSlot() {
        return size < limit-1;
    }

    public void addSlot(Location location) {
        if (!canAddSlot()) return;
        store.add(++pointer, location);
        size ++;
    }

    public boolean removeSlot() {
        if (size <= 1)
            return false;
        store.remove(pointer --);
        size --;
        if (pointer < 0) pointer = size-1;
        return true;
    }

    public void rotate(int by) {
        pointer += by % size;
        if(pointer >= size)
            pointer -= size;
        if(pointer < 0)
            pointer += size;
    }

}
