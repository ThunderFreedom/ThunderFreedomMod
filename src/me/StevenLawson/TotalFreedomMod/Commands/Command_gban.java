package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.ArrayList;
import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_Ban;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_Player;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Ban a griefer", usage = "/<command> <username>")
public class Command_gban extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
         if (args.length == 1)
        {
            String username;
            final List<String> ips = new ArrayList<String>();

            final Player player = getPlayer(args[1]);

            if (player == null)
            {
                final TFM_Player entry = TFM_PlayerList.getEntry(args[1]);

                if (entry == null)
                {
                    TFM_Util.playerMsg(sender, "Can't find that user. If target is not logged in, make sure that you spelled the name exactly.");
                    return true;
                }

                username = entry.getLastLoginName();
                ips.addAll(entry.getIps());
            }
            else
            {
                username = player.getName();
                ips.add(player.getAddress().getAddress().getHostAddress());
            }
                TFM_Util.adminAction(sender.getName(), "Banning " + username + " and IPs: " + StringUtils.join(ips, ","), true);

                Player target = getPlayer(username, true);
                if (target != null)
                {
                    TFM_BanManager.addUuidBan(new TFM_Ban(TFM_Util.getUuid(target), target.getName()));
                    server.dispatchCommand(sender, "co rb u:" + target.getName() + " r:#global t:1d");
                    target.kickPlayer("Griefing - CoreProtect Confirm!");
                }
                else
                {
                    TFM_BanManager.addUuidBan(new TFM_Ban(TFM_Util.getUuid(username), username));
                    server.dispatchCommand(sender, "co rb u:" + target.getName() + " r:#global t:1d");
                }

                for (String ip : ips)
                {
                    TFM_BanManager.addIpBan(new TFM_Ban(ip, username));
                    String[] ip_address_parts = ip.split("\\.");
                    TFM_BanManager.addIpBan(new TFM_Ban(ip_address_parts[0] + "." + ip_address_parts[1] + ".*.*", username));
                }
            }
            return true;
        }
}

