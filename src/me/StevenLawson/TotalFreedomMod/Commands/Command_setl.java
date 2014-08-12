package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Bridge.TFM_WorldEditBridge;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Sets everyone's Worldedit block modification limit to a custom amount (defaults to 500).", usage = "/<command> [limit]")
public class Command_setl extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        int limit = (args.length >= 1 ? Integer.parseInt(args[0]) : 1000);
        TFM_Util.adminAction(sender.getName(), String.format("Setting everyone's Worldedit block modification limit to %s.", limit), true);
        for (final Player player : server.getOnlinePlayers())
        {
            TFM_WorldEditBridge.setLimit(player, limit);
        }
        return true;
    }
}
