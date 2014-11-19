package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.OP, source = SourceType.ONLY_IN_GAME, blockHostConsole = true)
@CommandParameters(description = "Report a player for admins to see.", usage = "/<command> <player> <reason>")
public class Command_report extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 2)
        {
            return false;
        }

        Player player = getPlayer(args[0]);

        if (player == null)
        {
            playerMsg(TotalFreedomMod.PLAYER_NOT_FOUND);
            return true;
        }

        if (sender instanceof Player)
        {
            if (player == (Player) sender)
            {
                playerMsg(ChatColor.RED + "Please, don't try to report yourself.");
                return true;
            }
        }

        if (TFM_AdminList.isSuperAdmin(player))
        {
            playerMsg(ChatColor.RED + "You may not report " + player.getName() + ", they are an admin.");
            return true;
        }

        String reported = player.getName();
        String reporter = sender.getName();
        String report = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        sender.sendMessage(ChatColor.GREEN + "Thank you, your report has been successfully logged.");

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (TFM_AdminList.isSuperAdmin(p))
            {
                p.sendMessage(ChatColor.RED + "[REPORTS] " + ChatColor.GOLD + reporter + " has reported " + reported + " for " + report);
            }
        }

        return true;
    }
}
