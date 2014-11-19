package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(
        description = "Senior AdminChat - Talk privately with other senior admins.",
        usage = "/<command> [message...]",
        aliases = "senioradminchat")
public class Command_p extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if(sender instanceof Player)
        {
            if(!TFM_AdminList.getEntry(sender_p).isSeniorAdmin())
            {
                TFM_Util.playerMsg(sender, TotalFreedomMod.MSG_NO_PERMS, ChatColor.RED);
                return true;
            }
        }
        if (args.length == 0)
        {
            return false;
        }
        else
        {
            TFM_Util.SeniorAdminChatMessage(sender, StringUtils.join(args, " "), senderIsConsole);
        }
        return true;
    }
}
