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
            playerMsg(ChatColor.AQUA + "The following is accurate as of 8/5/14");
            playerMsg(ChatColor.RED + "Currently applying for Super Admin is impossible, the admin applications are on hold until september.");
         /*  playerMsg(ChatColor.GREEN + "To apply for admin you need to go to the forums at http://to.fop.us.to/apply");
            playerMsg(ChatColor.YELLOW + "Then read the requirements in the Super Admin in the \"Admin Application Template\".");
            playerMsg(ChatColor.WHITE + "Then if you feel you are ready, make a new thread in the 'admin applications'' board.");
            playerMsg(ChatColor.BLUE + "And fill out the template in the new thread.");
            playerMsg(ChatColor.RED + "We ask for you not to ask existing admins for recommendations, this will get your application denied.");
            playerMsg(ChatColor.GOLD + "Good Luck!");
        */
            return true;
        }
    }
}