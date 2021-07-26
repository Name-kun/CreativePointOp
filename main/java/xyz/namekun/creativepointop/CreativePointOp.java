package xyz.namekun.creativepointop;


import org.bukkit.plugin.java.JavaPlugin;

public final class CreativePointOp extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CreativePointOpListener(), this);
        getCommand("sendtext").setExecutor(new CreativePointOpListener());
        getCommand("sendtext").setTabCompleter(new CreativePointOpListener());
        getCommand("cplist").setExecutor(new CreativePointOpListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
