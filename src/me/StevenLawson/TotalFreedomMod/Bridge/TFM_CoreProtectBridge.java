/*package me.StevenLawson.TotalFreedomMod.Bridge;

public class TFM_CoreProtectBridge

{
  private CoreProtectPlugin coreprotectPlugin = null;

    private TFM_CoreProtectBridge()
    {
    }
    public CoreProtectPlugin getCoreProtectPlugin()
    {
        if (this.worldEditPlugin == null)
        {
            try
            {
                Plugin we = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");
                if (ci != null)
                {
                    if (ci instanceof CoreProtectPlugin)
                    {
                        this.coreProtectPlugin = (CoreProtectPlugin) ci;
                    }
                }
            }
            catch (Exception ex)
            {
                TFM_Log.severe(ex);
            }
        }
        return this.worldEditPlugin;
    }
    public BukkitPlayer getBukkitPlayer(Player player)
    {
        try
        {
            CoreProtectPlugin wep = this.getCoreProtectPlugin();
            if (wep != null)
            {
                return cip.wrapPlayer(player);
            }
        }
        catch (Exception ex)
        {
            TFM_Log.severe(ex);
        }
        return null;
    }
    
}
*/
