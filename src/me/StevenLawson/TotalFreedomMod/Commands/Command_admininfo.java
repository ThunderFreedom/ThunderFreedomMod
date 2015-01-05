package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(
        description = "See how to become admin.",
        aliases = "ai",
        usage = "/<command>")
public class Command_admininfo extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        TFM_Util.playerMsg(sender_p, "So you wish to apply for admin do you?", ChatColor.BLUE);
        TFM_Util.playerMsg(sender_p, "Go to our forums at http://freedomop.boards.net", ChatColor.GREEN);
        TFM_Util.playerMsg(sender_p, "Create a thread in the Super Admin applications forum following the template:", ChatColor.GREEN);
        TFM_Util.playerMsg(sender_p, "http://freedomop.boards.net/thread/249/admin-application-template-revised-july", ChatColor.GREEN);
        TFM_Util.playerMsg(sender_p, "wait for admin responses on your application and an eventual verfdict.", ChatColor.GREEN);
        TFM_Util.playerMsg(sender_p, "Don't ask for admins to look at the application, it will lower your chances of being accepted.", ChatColor.RED);
        return true;
    }
}
