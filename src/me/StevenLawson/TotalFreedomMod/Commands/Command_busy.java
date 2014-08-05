package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Busy atm.", usage = "/<command> [on | off]")
public class Command_busy extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
      if (args.length == 1)
        {
        if (args[0].equals("on"))
        {
        TFM_Util.adminAction(ChatColor.RED + sender.getName(), "I'm currently busy, please conatact another admin.", false);
        return true;
        }
        else if (args[0].equals("off"))
        {
      TFM_Util.adminAction(ChatColor.AQUA + sender.getName(), "I'm not busy anymore, you can conatact me now.", false);
      return true;
        }
        }
        return true;
    }
}
