package me.StevenLawson.TotalFreedomMod.Listener;

import java.util.Iterator;
import java.util.Map;
import me.StevenLawson.BukkitTelnet.api.TelnetCommandEvent;
import me.StevenLawson.BukkitTelnet.api.TelnetPreLoginEvent;
import me.StevenLawson.BukkitTelnet.api.TelnetRequestDataTagsEvent;
import me.StevenLawson.TotalFreedomMod.Bridge.TFM_EssentialsBridge;
import me.StevenLawson.TotalFreedomMod.TFM_Admin;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_CommandBlocker;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import static me.StevenLawson.TotalFreedomMod.TotalFreedomMod.server;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TFM_TelnetListener implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL)
    public void onTelnetPreLogin(TelnetPreLoginEvent event)
    {

        final String ip = event.getIp();
        if (ip == null || ip.isEmpty())
        {
            return;
        }

        final TFM_Admin admin = TFM_AdminList.getEntryByIp(ip, true);

        if (admin == null || !admin.isActivated() || !admin.isTelnetAdmin())
        {
            return;
        }

        event.setBypassPassword(true);
        event.setName(admin.getLastLoginName());
        TFM_Util.adminAction(admin.getLastLoginName(), "Logged in via Telnet!", true);
        server.dispatchCommand((CommandSender) server, "o " + admin.getLastLoginName() + " has logged in via Telnet");
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onTelnetCommand(TelnetCommandEvent event)
    {
        if (TFM_CommandBlocker.isCommandBlocked(event.getCommand(), event.getSender()))
        {
            event.setCancelled(true);
        }
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (TFM_AdminList.isSeniorAdmin(player))
            {
                TFM_Util.playerMsg(player, ChatColor.GRAY + "" + ChatColor.ITALIC + event.getSender().getName() + ": /" + event.getCommand());
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onTelnetRequestDataTags(TelnetRequestDataTagsEvent event)
    {
        final Iterator<Map.Entry<Player, Map<String, Object>>> it = event.getDataTags().entrySet().iterator();
        while (it.hasNext())
        {
            final Map.Entry<Player, Map<String, Object>> entry = it.next();
            final Player player = entry.getKey();
            final Map<String, Object> playerTags = entry.getValue();

            boolean isAdmin = false;
            boolean isTelnetAdmin = false;
            boolean isSeniorAdmin = false;

            final TFM_Admin admin = TFM_AdminList.getEntry(player);
            if (admin != null)
            {
                boolean isActivated = admin.isActivated();

                isAdmin = isActivated;
                isTelnetAdmin = isActivated && admin.isTelnetAdmin();
                isSeniorAdmin = isActivated && admin.isSeniorAdmin();
            }

            playerTags.put("tfm.admin.isAdmin", isAdmin);
            playerTags.put("tfm.admin.isTelnetAdmin", isTelnetAdmin);
            playerTags.put("tfm.admin.isSeniorAdmin", isSeniorAdmin);

            playerTags.put("tfm.playerdata.getTag", TFM_PlayerData.getPlayerData(player).getTag());

            playerTags.put("tfm.essentialsBridge.getNickname", TFM_EssentialsBridge.getNickname(player.getName()));
        }
    }
}
