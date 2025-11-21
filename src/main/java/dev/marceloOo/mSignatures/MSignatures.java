package dev.marceloOo.mSignatures;

import dev.marceloOo.mSignatures.Commands.CheckSignCommand;
import dev.marceloOo.mSignatures.Commands.ReloadCommand;
import dev.marceloOo.mSignatures.Commands.SignCommand;
import dev.marceloOo.mSignatures.Utils.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class MSignatures extends JavaPlugin {

    private static MSignatures instance;

    private int pluginid;

    @Override
    public void onEnable() {
        getLogger().info("[mSignatures] >> Enabled.");
        getCommand("sign").setExecutor(new SignCommand());
        getCommand("msreload").setExecutor(new ReloadCommand());
        getCommand("checksign").setExecutor(new CheckSignCommand());
        saveDefaultConfig();
        reloadConfig();
        instance = this;
        this.pluginid = 18440;
        Metrics metrics = new Metrics(this, this.pluginid);
    }

    @Override
    public void onDisable() {
        getLogger().info("[mSignatures] >> Disabled.");
    }

    public static MSignatures getInstance() {
        return instance;
    }
}
