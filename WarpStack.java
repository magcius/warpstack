
public class WarpStack extends Plugin {
    public String name = "WarpStack";
    public String version = "0.5";

    private WarpStackListener listener;

    public void enable() {
        etc.getInstance().addCommand("/swarp", "[warpname] - push a warp to your warp stack");
        etc.getInstance().addCommand("/sback", "go back");
        etc.getInstance().addCommand("/smove", "+N or -N - move within your warp stack");
    }

    public void disable() {
        etc.getInstance().removeCommand("/swarp");
        etc.getInstance().removeCommand("/sback");
        etc.getInstance().removeCommand("/smove");
    }

    public void initialize() {
        listener = new WarpStackListener();

        etc.getLoader().addListener(
            PluginLoader.Hook.COMMAND,
            listener, this,
            PluginListener.Priority.MEDIUM);
    }
}
