package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Admin info.", usage = "/<command>", aliases = "admininfo")
public class Command_ai extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String lbl, String[] args, boolean senderIsConsole)
    {
        if(args.length != 0)
        {
            return false;
        }
        else
        {
            playerMsg(ChatColor.GREEN + "Applying for admin: ");
            playerMsg(ChatColor.GREEN + "To apply for admin, please go to the forums, at http://www.freedomop.boards.net");
            playerMsg(ChatColor.GREEN + "You must then read the requirements.");
            playerMsg(ChatColor.GREEN + "Then, if you feel you are ready, make a new thread in the Admin Applications board, ");
            playerMsg(ChatColor.GREEN + "and fill out the template in a new thread.");
            playerMsg(ChatColor.RED + "Do not ask any admins for recommendations. This will get your application auto-denied.");
            playerMsg(ChatColor.RED + "Don't lie on your application. That is also a way to be auto-denied.");
            playerMsg(ChatColor.GREEN + "Good Luck!");
            return true;
        }
    }
}