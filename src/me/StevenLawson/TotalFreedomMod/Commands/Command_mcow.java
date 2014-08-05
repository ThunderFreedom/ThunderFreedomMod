package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Ban;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerList;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "The Mystical Cow's banning command.", usage = "/<command> <player>")
public class Command_mcow extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
    
           if (!sender.getName().equalsIgnoreCase("cowgomooo12"))
        {
            playerMsg(TotalFreedomMod.MSG_NO_PERMS);
            return true;
        }
        if (args.length != 1)
        {
            return false;
        }

        final Player player = getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(TotalFreedomMod.PLAYER_NOT_FOUND);
            return true;
        }
        
        final String ip = player.getAddress().getAddress().getHostAddress().trim();


        if (TFM_AdminList.isSuperAdmin(player))
        {
            TFM_AdminList.removeSuperadmin(player);
        }
        player.setOp(false);
        for (String playerIp : TFM_PlayerList.getEntry(player).getIps())
        {
            TFM_BanManager.addIpBan(new TFM_Ban(playerIp, player.getName()));
        }
        TFM_BanManager.addUuidBan(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.closeInventory();
        player.getInventory().clear();
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.setHealth(0.0);
            }
        }.runTaskLater(plugin, 2L * 20L);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.kickPlayer(ChatColor.RED + "You have been banned by The Mystical Cow!");
            }
        }.runTaskLater(plugin, 3L * 20L);

        return true;
    }
}

// This command was created by using Command_build as a template!
// Giant thanks, @buildcarter8
