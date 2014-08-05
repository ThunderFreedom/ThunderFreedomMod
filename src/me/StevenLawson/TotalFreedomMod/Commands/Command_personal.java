package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.Arrays;
import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Run your personal command.", usage = "/<command>")
public class Command_personal extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        switch(sender_p.getName())
        {
            case "Camzie99":
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    ItemStack wool = new ItemStack(Material.WOOL, 1, (short) 10);
                    ItemMeta meta = wool.getItemMeta();
                    meta.setDisplayName(ChatColor.DARK_PURPLE + "The Purple Aura");
                    List<String> lore = Arrays.asList(ChatColor.DARK_PURPLE + "The Purple Lord's", ChatColor.DARK_PURPLE + "powers protect you.");
                    meta.setLore(lore);
                    wool.setItemMeta(meta);
                    inv.setHelmet(wool);
                }
            TFM_Util.adminAction(sender_p.getName(), "Gracing the world with Purple!", false);
                
            case "cowgomoo12":
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    TFM_Util.spawnMob(player, EntityType.COW);
                }
            TFM_Util.adminAction(sender_p.getName(), "Let there be cows!", false);
            
            case "PieGuy7896":
                TFM_Util.adminAction(sender_p.getName(), "This is Pi.", false);
                TFM_Util.bcastMsg("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679", ChatColor.DARK_AQUA);
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    inv.addItem(new ItemStack(Material.PUMPKIN_PIE, 64));
                }
            
            case "robotexplorer":
                TFM_Util.adminAction(sender_p.getName(), "You can't outsmart a robot? I think NOT!", true);
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    ItemStack robot = new ItemStack(Material.REDSTONE_BLOCK, 1);
                    ItemMeta meta = robot.getItemMeta();
                    meta.setDisplayName(ChatColor.RED + "Robot");
                    robot.setItemMeta(meta);
                }
            
            case "GigaByte_Jr":
                TFM_Util.asciiDog();
                TFM_Util.adminAction(sender_p.getName(), "Giving everyone a pet Woofie.\nTame them with the bone!", false);
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    inv.addItem(new ItemStack(Material.BONE, 1));
                    LivingEntity dog = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
                    dog.setCustomNameVisible(true);
                    dog.setCustomName(ChatColor.DARK_AQUA + "Woofie!");
                }
            
            default:
                TFM_Util.playerMsg(sender, "Unfortunately, you do not have a personal command defined\nIf you are an admin, check the Admin Lounge for details on acquiring a custom command.", ChatColor.AQUA);  
        }
        return true;
    }
}
