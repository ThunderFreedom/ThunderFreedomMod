package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static me.StevenLawson.TotalFreedomMod.Commands.Command_deafen.STEPS;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import static org.apache.commons.lang.RandomStringUtils.random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Run your personal command.", usage = "/<command>")
public class Command_personal extends TFM_Command
{    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        String which;
        if (sender.getName().equals("eagleeye64000") || sender.getName().equals("R1SSY") || sender.getName().equals("escojay"))
        {
            which = "multiEagle";
        }
        else
        {
            which = sender_p.getName();
        }
        switch(which)
        {
            case "Camzie99":
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    ItemStack wool = new ItemStack(Material.WOOL, 1, (short) 10);
                    ItemMeta meta = wool.getItemMeta();
                    World world = player.getWorld();
                    Location loc = player.getLocation();
                    meta.setDisplayName(ChatColor.DARK_PURPLE + "The Purple Aura");
                    List<String> lore = Arrays.asList(ChatColor.DARK_PURPLE + "The Purple Lord's", ChatColor.DARK_PURPLE + "powers protect you.");
                    meta.setLore(lore);
                    wool.setItemMeta(meta);
                    inv.setHelmet(wool);
                    world.strikeLightningEffect(loc);
                }
                TFM_Util.adminAction(sender_p.getName(), "Gracing the world with Purple!", false);
            break;
            case "cowgomoo12":
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    TFM_Util.spawnMob(player, EntityType.COW, 2);
                }
                TFM_Util.adminAction(sender_p.getName(), "Let there be cows!", false);
            break;
            case "PieGuy7896":
                TFM_Util.adminAction(sender_p.getName(), "This is Pi.", false);
                TFM_Util.bcastMsg("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679", ChatColor.DARK_AQUA);
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    ItemStack pie = new ItemStack(Material.PUMPKIN_PIE, 64);
                    ItemMeta meta = pie.getItemMeta();
                    meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Pie!");
                    meta.addEnchant(Enchantment.FIRE_ASPECT, 25, true);
                    meta.addEnchant(Enchantment.KNOCKBACK, 10, true);
                    pie.setItemMeta(meta);
                    inv.addItem(pie);
                }
            break;
            case "multiEagle":
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    ItemStack potato = new ItemStack(Material.POTATO, 1);
                    ItemMeta meta = potato.getItemMeta();
                    List<String> lore = Arrays.asList(ChatColor.DARK_PURPLE + "It's dangerous to go alone; take this!");
                    meta.setLore(lore);
                    potato.setItemMeta(meta);
                    inv.addItem(potato);
                }
            break;
            case "robotexplorer":
                TFM_Util.adminAction(sender_p.getName(), "You can't outsmart a robot? I think NOT!", true);
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    ItemStack robot = new ItemStack(Material.REDSTONE_BLOCK, 1);
                    ItemMeta meta = robot.getItemMeta();
                    meta.setDisplayName(ChatColor.RED + "Robot");
                    robot.setItemMeta(meta);
                    inv.addItem(robot);
                }
            break;
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
            break;
            case "0sportguy0":
                TFM_Util.adminAction(sender_p.getName(), "An apple a day keeps the doctor away!", false);
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    PlayerInventory inv = player.getInventory();
                    inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));
                }
            break;
            case "lynxlps":
                TFM_Util.adminAction("Dahlia Hawthrone", "Eliminating all signs of life.", true);
                for (World world : Bukkit.getWorlds())
                {
                    for (Entity entity : world.getEntities())
                    {
                        if(entity instanceof LivingEntity && !(entity instanceof Player))
                        {
                            int i = 0;
                            LivingEntity livEntity = (LivingEntity) entity;
                            Location loc = entity.getLocation();
                            do
                            {
                                world.strikeLightningEffect(loc);

                                i++;
                            }
                            while (i <= 2);
                            livEntity.setHealth(0);
                        }
                    }
                    for (final Player player : server.getOnlinePlayers())
                    {
                        for (double percent = 0.0; percent <= 1.0; percent += (1.0 / STEPS))
                        {
                            final float pitch = (float) (percent * 2.0);

                            new BukkitRunnable()
                            {
                                @Override
                                public void run()
                                {
                                    player.playSound(randomOffset(player.getLocation(), 5.0), Sound.values()[random.nextInt(Sound.values().length)], 100.0f, pitch);
                                }
                            }.runTaskLater(plugin, Math.round(20.0 * percent * 2.0));
                        }
                    }
                }
            break;
            default:
                TFM_Util.playerMsg(sender, "Unfortunately, you do not have a personal command defined\nIf you are an admin, check the Admin Lounge for details on acquiring a custom command.", ChatColor.AQUA);  
            break;
        }
        return true;
    }
    
    private static final Random random = new Random();
    public static final double STEPS = 10.0;
    
    private static Location randomOffset(Location a, double magnitude)
    {
        return a.clone().add(randomDoubleRange(-1.0, 1.0) * magnitude, randomDoubleRange(-1.0, 1.0) * magnitude, randomDoubleRange(-1.0, 1.0) * magnitude);
    }

    private static Double randomDoubleRange(double min, double max)
    {
        return min + (random.nextDouble() * ((max - min) + 1.0));
    }
}
