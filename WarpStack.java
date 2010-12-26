
import java.util.LinkedHashMap;

public class WarpStack extends Plugin {
    public String name = "WarpStack";
    public String version = "0.7";

    private WarpStackListener listener;

    public void enable() {
        LinkedHashMap<String, String> commands = etc.getInstance().getCommands();
        commands.put("/swarp", commands.get("/warp") + " [Stack]");
        commands.put("/stp", commands.get("/tp") + " [Stack]");
        commands.put("/stphere", commands.get("/tphere") + " [Stack]");
        commands.put("/shome", commands.get("/home") + " [Stack]");
        commands.put("/sspawn", commands.get("/spawn") + " [Stack]");
        commands.put("/sback", " - Go back in your warp stack [Stack]");
        commands.put("/smove", " - Move within your warp stack [Stack]");
    }

    public void disable() {
        etc e = etc.getInstance();
        e.removeCommand("/swarp");
        e.removeCommand("/stp");
        e.removeCommand("/shome");
        e.removeCommand("/sspawn");
        e.removeCommand("/sback");
        e.removeCommand("/smove");
    }

    public void initialize() {
        listener = new WarpStackListener();

        etc.getLoader().addListener(
            PluginLoader.Hook.COMMAND,
            listener, this,
            PluginListener.Priority.MEDIUM);
    }
}
