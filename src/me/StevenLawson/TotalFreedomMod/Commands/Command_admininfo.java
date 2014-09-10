package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.confuser.barapi.BarAPI;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
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
        TFM_Util.playerMsg(sender_p, "So you wish to apply for admin do you?", ChatColor.GREEN);
        TFM_Util.playerMsg(sender_p, "Well, apply at http://freedomop.boards.net/ !", ChatColor.GREEN);
        TFM_Util.playerMsg(sender_p, "Don't beg to look at the application, it'll shorten the percentage on if it can get accepted.", ChatColor.GREEN);
        return true;
    }
}
