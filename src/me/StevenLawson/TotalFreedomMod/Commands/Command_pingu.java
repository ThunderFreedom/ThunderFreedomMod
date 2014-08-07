package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.Random;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "lel", usage = "/<command>")
public class Command_pingu extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        TFM_Util.bcastMsg("Pingu is love, Pingu is life.", ChatColor.RED);
        }

        ItemStack heldItem = new ItemStack(Material.COOKIE);
        ItemMeta heldItemMeta = heldItem.getItemMeta();
        heldItemMeta.setDisplayName((new StringBuilder()).append(ChatColor.WHITE).append("Pingu_Is_Love").append(ChatColor.BLACK).append("LPingu_Is_Life").toString());
        heldItem.setItemMeta(heldItemMeta);

        for (Player player : server.getOnlinePlayers())
        {
            player.getInventory().setItem(player.getInventory().firstEmpty(), heldItem);
        }

        TFM_Util.bcastMsg(output.toString());
        return true;
    }
}