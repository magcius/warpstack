
import java.util.ArrayList;

public class LocationDataStack {

    public ArrayList<LocationData> store;
    public int size;
    public int pointer;

    public LocationDataStack(Location initialSlot) {
        store = new ArrayList<LocationData> (getLimit());
        clear(initialSlot);
    }

    public int getLimit() {
        return WarpStack.stack_limit;
    }

    public boolean canAddSlot() {
        return size < getLimit();
    }

    public Location getLocation() {
        return store.get(pointer).location;
    }

    public String getName() {
        return store.get(pointer).name;
    }

    public String getName(int index) {
        return store.get(index).name;
    }

    public void setLocation(Location to) {
        Location loc = getLocation();
        loc.x = to.x; loc.y = to.y; loc.z = to.z;
        loc.rotX = to.rotX; loc.rotY = to.rotY;
    }

    public void addSlot(Location location, String name) {
        if (!canAddSlot()) return;
        store.add(++ pointer, new LocationData(location, name));
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

    public void clear(Location initialSlot) {
        if (size <= 1)
            return;
        store.clear();
        store.add(new LocationData(initialSlot, "<start>"));
        pointer = 0;
        size = 1;
    }

    public void rotate(int by) {
        pointer += by % size;
        if(pointer >= size)
            pointer -= size;
        if(pointer < 0)
            pointer += size;
    }

}
