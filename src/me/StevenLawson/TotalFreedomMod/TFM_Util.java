package me.StevenLawson.TotalFreedomMod;

import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.StevenLawson.TotalFreedomMod.Config.TFM_Config;
import net.minecraft.util.org.apache.commons.io.FileUtils;

import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.FileUtil;

public class TFM_Util
{
    private static final Map<String, Integer> ejectTracker = new HashMap<String, Integer>();
    public static final Map<String, EntityType> mobtypes = new HashMap<String, EntityType>();
    public static final List<String> DEVELOPERS = Arrays.asList("Madgeek1450", "DarthSalamon", "AcidicCyanide", "wild1145", "WickedGamingUK");
    public static final List<String> FOP_DEVELOPERS = Arrays.asList("Paldiu", "RobinGall2910", "Freelix2000", "PieGuy7896");
    public static final List<String> SPECIAL_EXECS = Arrays.asList("aggelosQQ", "Immurtle");
    public static final List<String> SYS_ADMINS = Arrays.asList("lynxlps", "cowgomooo12", "EnderLolzeh", "CrafterSmith12");
    private static final Random RANDOM = new Random();
    public static String DATE_STORAGE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
    public static final Map<String, ChatColor> CHAT_COLOR_NAMES = new HashMap<String, ChatColor>();
    public static final List<ChatColor> CHAT_COLOR_POOL = Arrays.asList(
            ChatColor.DARK_BLUE,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_RED,
            ChatColor.DARK_PURPLE,
            ChatColor.GOLD,
            ChatColor.BLUE,
            ChatColor.GREEN,
            ChatColor.AQUA,
            ChatColor.RED,
            ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW);
    static
    {
        for (EntityType type : EntityType.values())
        {
            try
            {
                if (TFM_DepreciationAggregator.getName_EntityType(type) != null)
                {
                    if (Creature.class.isAssignableFrom(type.getEntityClass()))
                    {
                        mobtypes.put(TFM_DepreciationAggregator.getName_EntityType(type).toLowerCase(), type);
                    }
                }
            }
            catch (Exception ex)
            {
            }
        }

        for (ChatColor chatColor : CHAT_COLOR_POOL)
        {
            CHAT_COLOR_NAMES.put(chatColor.name().toLowerCase().replace("_", ""), chatColor);
        }
    }

    public static void adminAction(String string)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static String getUniqueId(Player player)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private TFM_Util()
    {
        throw new AssertionError();
    }

    public static boolean isUniqueId(String uuid)
    {
        try
        {
            UUID.fromString(uuid);
        }
        catch (IllegalArgumentException ex)
        {
            return false;
        }

        return true;
    }

    public static UUID getUuid(OfflinePlayer offlinePlayer)
    {
        if (offlinePlayer instanceof Player)
        {
            return TFM_PlayerData.getPlayerData((Player) offlinePlayer).getUniqueId();
        }

        return getUuid(offlinePlayer.getName());
    }

    public static UUID getUuid(String offlineplayer)
    {
        final UUID uuid = TFM_UuidResolver.getUUIDOf(offlineplayer);

        if (uuid == null)
        {
            return generateUuidForName(offlineplayer);
        }

        return uuid;
    }

    public static UUID generateUuidForName(String name)
    {
        TFM_Log.info("Generating spoof UUID for " + name);
        name = name.toLowerCase();
        try
        {
            final MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(name.getBytes());
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++)
            {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            return UUID.fromString(
                    sb.substring(0, 8)
                    + "-" + sb.substring(8, 12)
                    + "-" + sb.substring(12, 16)
                    + "-" + sb.substring(16, 20)
                    + "-" + sb.substring(20, 32));
        }
        catch (NoSuchAlgorithmException ex)
        {
            TFM_Log.severe(ex);
        }

        return UUID.randomUUID();
    }

    public static void bcastMsg(String message, ChatColor color)
    {
        TFM_Log.info(message, true);

        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.sendMessage((color == null ? "" : color) + message);
        }
    }

    public static void bcastMsg(String message)
    {
        TFM_Util.bcastMsg(message, null);
    }

    // Still in use by listeners
    public static void playerMsg(CommandSender sender, String message, ChatColor color)
    {
        sender.sendMessage(color + message);
    }

    // Still in use by listeners
    public static void playerMsg(CommandSender sender, String message)
    {
        TFM_Util.playerMsg(sender, message, ChatColor.GRAY);
    }

    public static void adminAction(String adminName, String action, boolean isRed)
    {
        TFM_Util.bcastMsg(adminName + " - " + action, (isRed ? ChatColor.RED : ChatColor.AQUA));
    }

    public static String getIp(OfflinePlayer player)
    {
        if (player instanceof Player)
        {
            return ((Player) player).getAddress().getAddress().getHostAddress().trim();
        }

        final TFM_Player entry = TFM_PlayerList.getEntry(TFM_Util.getUuid(player));

        if (entry == null)
        {
            return null;
        }

        return entry.getIps().get(0);
    }

    public static String formatLocation(Location location)
    {
        return String.format("%s: (%d, %d, %d)",
                location.getWorld().getName(),
                Math.round(location.getX()),
                Math.round(location.getY()),
                Math.round(location.getZ()));
    }

    public static String formatPlayer(OfflinePlayer player)
    {
        return player.getName() + " (" + TFM_Util.getUuid(player) + ")";
    }

    /**
     * Escapes an IP-address to a config-friendly version.
     *
     * <p>Example:
     * <pre>
     * IpUtils.toEscapedString("192.168.1.192"); // 192_168_1_192
     * </pre></p>
     *
     * @param ip The IP-address to escape.
     * @return The config-friendly IP address.
     * @see #fromEscapedString(String)
     */
    public static String toEscapedString(String ip) // BukkitLib @ https://github.com/Pravian/BukkitLib
    {
        return ip.trim().replaceAll("\\.", "_");
    }

    /**
     * Un-escapes a config-friendly Ipv4-address.
     *
     * <p>Example:
     * <pre>
     * IpUtils.fromEscapedString("192_168_1_192"); // 192.168.1.192
     * </pre></p>
     *
     * @param escapedIp The IP-address to un-escape.
     * @return The config-friendly IP address.
     * @see #toEscapedString(String)
     */
    public static String fromEscapedString(String escapedIp) // BukkitLib @ https://github.com/Pravian/BukkitLib
    {
        return escapedIp.trim().replaceAll("_", "\\.");
    }

    public static void gotoWorld(CommandSender sender, String targetworld)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (player.getWorld().getName().equalsIgnoreCase(targetworld))
            {
                sender.sendMessage(ChatColor.GRAY + "Going to main world.");
                player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                return;
            }

            for (World world : Bukkit.getWorlds())
            {
                if (world.getName().equalsIgnoreCase(targetworld))
                {
                    sender.sendMessage(ChatColor.GRAY + "Going to world: " + targetworld);
                    player.teleport(world.getSpawnLocation());
                    return;
                }
            }

            sender.sendMessage(ChatColor.GRAY + "World " + targetworld + " not found.");
        }
        else
        {
            sender.sendMessage(TotalFreedomMod.NOT_FROM_CONSOLE);
        }
    }

    public static String decolorize(String string)
    {
        return string.replaceAll("\\u00A7(?=[0-9a-fk-or])", "&");
    }

    public static void buildHistory(Location location, int length, TFM_PlayerData playerdata)
    {
        final Block center = location.getBlock();
        for (int xOffset = -length; xOffset <= length; xOffset++)
        {
            for (int yOffset = -length; yOffset <= length; yOffset++)
            {
                for (int zOffset = -length; zOffset <= length; zOffset++)
                {
                    final Block block = center.getRelative(xOffset, yOffset, zOffset);
                    playerdata.insertHistoryBlock(block.getLocation(), block.getType());
                }
            }
        }
    }

    public static void generateCube(Location location, int length, Material material)
    {
        final Block center = location.getBlock();
        for (int xOffset = -length; xOffset <= length; xOffset++)
        {
            for (int yOffset = -length; yOffset <= length; yOffset++)
            {
                for (int zOffset = -length; zOffset <= length; zOffset++)
                {
                    final Block block = center.getRelative(xOffset, yOffset, zOffset);
                    if (block.getType() != material)
                    {
                        block.setType(material);
                    }
                }
            }
        }
    }

    public static void generateHollowCube(Location location, int length, Material material)
    {
        final Block center = location.getBlock();
        for (int xOffset = -length; xOffset <= length; xOffset++)
        {
            for (int yOffset = -length; yOffset <= length; yOffset++)
            {
                for (int zOffset = -length; zOffset <= length; zOffset++)
                {
                    // Hollow
                    if (Math.abs(xOffset) != length && Math.abs(yOffset) != length && Math.abs(zOffset) != length)
                    {
                        continue;
                    }

                    final Block block = center.getRelative(xOffset, yOffset, zOffset);

                    if (material != Material.SKULL)
                    {
                        // Glowstone light
                        if (material != Material.GLASS && xOffset == 0 && yOffset == 2 && zOffset == 0)
                        {
                            block.setType(Material.GLOWSTONE);
                            continue;
                        }

                        block.setType(material);
                    }
                    else // Darth mode
                    {
                        if (Math.abs(xOffset) == length && Math.abs(yOffset) == length && Math.abs(zOffset) == length)
                        {
                            block.setType(Material.GLOWSTONE);
                            continue;
                        }

                        block.setType(Material.SKULL);
                        final Skull skull = (Skull) block.getState();
                        skull.setSkullType(SkullType.PLAYER);
                        skull.setOwner("DarthSalamon");
                        skull.update();
                    }
                }
            }
        }
    }

    public static void setWorldTime(World world, long ticks)
    {
        long time = world.getTime();
        time -= time % 24000;
        world.setTime(time + 24000 + ticks);
    }

    public static void createDefaultConfiguration(final String configFileName)
    {
        final File targetFile = new File(TotalFreedomMod.plugin.getDataFolder(), configFileName);

        if (targetFile.exists())
        {
            return;
        }

        TFM_Log.info("Installing default configuration file template: " + targetFile.getPath());

        try
        {
            final InputStream configFileStream = TotalFreedomMod.plugin.getResource(configFileName);
            FileUtils.copyInputStreamToFile(configFileStream, targetFile);
            configFileStream.close();
        }
        catch (IOException ex)
        {
            TFM_Log.severe(ex);
        }
    }

    public static boolean deleteFolder(final File file)
    {
        if (file.exists() && file.isDirectory())
        {
            return FileUtils.deleteQuietly(file);
        }
        return false;
    }

    public static void deleteCoreDumps()
    {
        final File[] coreDumps = new File(".").listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File file)
            {
                return file.getName().startsWith("java.core");
            }
        });

        for (File dump : coreDumps)
        {
            TFM_Log.info("Removing core dump file: " + dump.getName());
            dump.delete();
        }
    }

    public static EntityType getEntityType(String mobname) throws Exception
    {
        mobname = mobname.toLowerCase().trim();

        if (!TFM_Util.mobtypes.containsKey(mobname))
        {
            throw new Exception();
        }

        return TFM_Util.mobtypes.get(mobname);
    }

    /**
     * Write the specified InputStream to a file.
     *
     * @param in The InputStream from which to read.
     * @param file The File to write to.
     * @throws IOException
     */
    public static void copy(InputStream in, File file) throws IOException // BukkitLib @ https://github.com/Pravian/BukkitLib
    {
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
        }

        final OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
        {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
    }

    /**
     * Returns a file at located at the Plugins Data folder.
     *
     * @param plugin The plugin to use
     * @param name The name of the file.
     * @return The requested file.
     */
    public static File getPluginFile(Plugin plugin, String name)  // BukkitLib @ https://github.com/Pravian/BukkitLib
    {
        return new File(plugin.getDataFolder(), name);
    }

    public static void autoEject(Player player, String kickMessage)
    {
        EjectMethod method = EjectMethod.STRIKE_ONE;
        final String ip = TFM_Util.getIp(player);

        if (!TFM_Util.ejectTracker.containsKey(ip))
        {
            TFM_Util.ejectTracker.put(ip, 0);
        }

        int kicks = TFM_Util.ejectTracker.get(ip);
        kicks += 1;

        TFM_Util.ejectTracker.put(ip, kicks);

        if (kicks <= 1)
        {
            method = EjectMethod.STRIKE_ONE;
        }
        else if (kicks == 2)
        {
            method = EjectMethod.STRIKE_TWO;
        }
        else if (kicks >= 3)
        {
            method = EjectMethod.STRIKE_THREE;
        }

        TFM_Log.info("AutoEject -> name: " + player.getName() + " - player ip: " + ip + " - method: " + method.toString());

        player.setOp(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();

        switch (method)
        {
            case STRIKE_ONE:
            {
                final Calendar cal = new GregorianCalendar();
                cal.add(Calendar.MINUTE, 1);
                final Date expires = cal.getTime();

                TFM_Util.bcastMsg(ChatColor.RED + player.getName() + " has been banned for 1 minute.");

                TFM_BanManager.addIpBan(new TFM_Ban(ip, player.getName(), "AutoEject", expires, kickMessage));
                TFM_BanManager.addUuidBan(new TFM_Ban(TFM_Util.getUuid(player), player.getName(), "AutoEject", expires, kickMessage));
                player.kickPlayer(kickMessage);

                break;
            }
            case STRIKE_TWO:
            {
                final Calendar c = new GregorianCalendar();
                c.add(Calendar.MINUTE, 3);
                final Date expires = c.getTime();

                TFM_Util.bcastMsg(ChatColor.RED + player.getName() + " has been banned for 3 minutes.");

                TFM_BanManager.addIpBan(new TFM_Ban(ip, player.getName(), "AutoEject", expires, kickMessage));
                TFM_BanManager.addUuidBan(new TFM_Ban(TFM_Util.getUuid(player), player.getName(), "AutoEject", expires, kickMessage));
                player.kickPlayer(kickMessage);
                break;
            }
            case STRIKE_THREE:
            {
                String[] ipAddressParts = ip.split("\\.");

                TFM_BanManager.addIpBan(new TFM_Ban(ip, player.getName(), "AutoEject", null, kickMessage));
                TFM_BanManager.addIpBan(new TFM_Ban(ipAddressParts[0] + "." + ipAddressParts[1] + ".*.*", player.getName(), "AutoEject", null, kickMessage));
                TFM_BanManager.addUuidBan(new TFM_Ban(TFM_Util.getUuid(player), player.getName(), "AutoEject", null, kickMessage));

                TFM_Util.bcastMsg(ChatColor.RED + player.getName() + " has been banned.");

                player.kickPlayer(kickMessage);
                break;
            }
        }
    }

    public static Date parseDateOffset(String time)
    {
        Pattern timePattern = Pattern.compile(
                "(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);
        Matcher m = timePattern.matcher(time);
        int years = 0;
        int months = 0;
        int weeks = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        boolean found = false;
        while (m.find())
        {
            if (m.group() == null || m.group().isEmpty())
            {
                continue;
            }
            for (int i = 0; i < m.groupCount(); i++)
            {
                if (m.group(i) != null && !m.group(i).isEmpty())
                {
                    found = true;
                    break;
                }
            }
            if (found)
            {
                if (m.group(1) != null && !m.group(1).isEmpty())
                {
                    years = Integer.parseInt(m.group(1));
                }
                if (m.group(2) != null && !m.group(2).isEmpty())
                {
                    months = Integer.parseInt(m.group(2));
                }
                if (m.group(3) != null && !m.group(3).isEmpty())
                {
                    weeks = Integer.parseInt(m.group(3));
                }
                if (m.group(4) != null && !m.group(4).isEmpty())
                {
                    days = Integer.parseInt(m.group(4));
                }
                if (m.group(5) != null && !m.group(5).isEmpty())
                {
                    hours = Integer.parseInt(m.group(5));
                }
                if (m.group(6) != null && !m.group(6).isEmpty())
                {
                    minutes = Integer.parseInt(m.group(6));
                }
                if (m.group(7) != null && !m.group(7).isEmpty())
                {
                    seconds = Integer.parseInt(m.group(7));
                }
                break;
            }
        }
        if (!found)
        {
            return null;
        }

        Calendar c = new GregorianCalendar();

        if (years > 0)
        {
            c.add(Calendar.YEAR, years);
        }
        if (months > 0)
        {
            c.add(Calendar.MONTH, months);
        }
        if (weeks > 0)
        {
            c.add(Calendar.WEEK_OF_YEAR, weeks);
        }
        if (days > 0)
        {
            c.add(Calendar.DAY_OF_MONTH, days);
        }
        if (hours > 0)
        {
            c.add(Calendar.HOUR_OF_DAY, hours);
        }
        if (minutes > 0)
        {
            c.add(Calendar.MINUTE, minutes);
        }
        if (seconds > 0)
        {
            c.add(Calendar.SECOND, seconds);
        }

        return c.getTime();
    }

    public static String playerListToNames(Set<OfflinePlayer> players)
    {
        List<String> names = new ArrayList<String>();
        for (OfflinePlayer player : players)
        {
            names.add(player.getName());
        }
        return StringUtils.join(names, ", ");
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Boolean> getSavedFlags()
    {
        Map<String, Boolean> flags = null;

        File input = new File(TotalFreedomMod.plugin.getDataFolder(), TotalFreedomMod.SAVED_FLAGS_FILE);
        if (input.exists())
        {
            try
            {
                FileInputStream fis = new FileInputStream(input);
                ObjectInputStream ois = new ObjectInputStream(fis);
                flags = (HashMap<String, Boolean>) ois.readObject();
                ois.close();
                fis.close();
            }
            catch (Exception ex)
            {
                TFM_Log.severe(ex);
            }
        }

        return flags;
    }

    public static boolean getSavedFlag(String flag) throws Exception
    {
        Boolean flagValue = null;

        Map<String, Boolean> flags = TFM_Util.getSavedFlags();

        if (flags != null)
        {
            if (flags.containsKey(flag))
            {
                flagValue = flags.get(flag);
            }
        }

        if (flagValue != null)
        {
            return flagValue.booleanValue();
        }
        else
        {
            throw new Exception();
        }
    }

    public static void setSavedFlag(String flag, boolean value)
    {
        Map<String, Boolean> flags = TFM_Util.getSavedFlags();

        if (flags == null)
        {
            flags = new HashMap<String, Boolean>();
        }

        flags.put(flag, value);

        try
        {
            final FileOutputStream fos = new FileOutputStream(new File(TotalFreedomMod.plugin.getDataFolder(), TotalFreedomMod.SAVED_FLAGS_FILE));
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(flags);
            oos.close();
            fos.close();
        }
        catch (Exception ex)
        {
            TFM_Log.severe(ex);
        }
    }

    public static void createBackups(String file)
    {
        final String save = file.split("\\.")[0];
        final TFM_Config config = new TFM_Config(TotalFreedomMod.plugin, "backup.yml", false);
        config.load();

        // Daily
        if (!config.isInt(save + ".daily"))
        {
            performBackup(file, "daily");
            config.set(save + ".daily", TFM_Util.getUnixTime());
        }
        else
        {
            int lastBackupDaily = config.getInt(save + ".daily");

            if (lastBackupDaily + 3600 * 24 < TFM_Util.getUnixTime())
            {
                performBackup(file, "daily");
                config.set(save + ".daily", TFM_Util.getUnixTime());
            }
        }

        // Weekly
        if (!config.isInt(save + ".weekly"))
        {
            performBackup(file, "weekly");
            config.set(save + ".weekly", TFM_Util.getUnixTime());
        }
        else
        {
            int lastBackupWeekly = config.getInt(save + ".weekly");

            if (lastBackupWeekly + 3600 * 24 * 7 < TFM_Util.getUnixTime())
            {
                performBackup(file, "weekly");
                config.set(save + ".weekly", TFM_Util.getUnixTime());
            }
        }

        config.save();
    }

    private static void performBackup(String file, String type)
    {
        TFM_Log.info("Backing up " + file + " to " + file + "." + type + ".bak");
        final File oldYaml = new File(TotalFreedomMod.plugin.getDataFolder(), file);
        final File newYaml = new File(TotalFreedomMod.plugin.getDataFolder(), file + "." + type + ".bak");
        FileUtil.copy(oldYaml, newYaml);
    }

    public static String dateToString(Date date)
    {
        return new SimpleDateFormat(DATE_STORAGE_FORMAT, Locale.ENGLISH).format(date);
    }

    public static Date stringToDate(String dateString)
    {
        try
        {
            return new SimpleDateFormat(DATE_STORAGE_FORMAT, Locale.ENGLISH).parse(dateString);
        }
        catch (ParseException pex)
        {
            return new Date(0L);
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean isFromHostConsole(String senderName)
    {
        return ((List<String>) TFM_ConfigEntry.HOST_SENDER_NAMES.getList()).contains(senderName.toLowerCase());
    }

    public static List<String> removeDuplicates(List<String> oldList)
    {
        List<String> newList = new ArrayList<String>();
        for (String entry : oldList)
        {
            if (!newList.contains(entry))
            {
                newList.add(entry);
            }
        }
        return newList;
    }

    public static boolean fuzzyIpMatch(String a, String b, int octets)
    {
        boolean match = true;

        String[] aParts = a.split("\\.");
        String[] bParts = b.split("\\.");

        if (aParts.length != 4 || bParts.length != 4)
        {
            return false;
        }

        if (octets > 4)
        {
            octets = 4;
        }
        else if (octets < 1)
        {
            octets = 1;
        }

        for (int i = 0; i < octets && i < 4; i++)
        {
            if (aParts[i].equals("*") || bParts[i].equals("*"))
            {
                continue;
            }

            if (!aParts[i].equals(bParts[i]))
            {
                match = false;
                break;
            }
        }

        return match;
    }

    public static String getFuzzyIp(String ip)
    {
        final String[] ipParts = ip.split("\\.");
        if (ipParts.length == 4)
        {
            return String.format("%s.%s.*.*", ipParts[0], ipParts[1]);
        }

        return ip;
    }

    public static int replaceBlocks(Location center, Material fromMaterial, Material toMaterial, int radius)
    {
        int affected = 0;

        Block centerBlock = center.getBlock();
        for (int xOffset = -radius; xOffset <= radius; xOffset++)
        {
            for (int yOffset = -radius; yOffset <= radius; yOffset++)
            {
                for (int zOffset = -radius; zOffset <= radius; zOffset++)
                {
                    Block block = centerBlock.getRelative(xOffset, yOffset, zOffset);

                    if (block.getType().equals(fromMaterial))
                    {
                        if (block.getLocation().distanceSquared(center) < (radius * radius))
                        {
                            block.setType(toMaterial);
                            affected++;
                        }
                    }
                }
            }
        }

        return affected;
    }

    public static void downloadFile(String url, File output) throws java.lang.Exception
    {
        downloadFile(url, output, false);
    }

    public static void downloadFile(String url, File output, boolean verbose) throws java.lang.Exception
    {
        final URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(output);
        fos.getChannel().transferFrom(rbc, 0, 1 << 24);
        fos.close();

        if (verbose)
        {
            TFM_Log.info("Downloaded " + url + " to " + output.toString() + ".");
        }
    }

    public static void adminChatMessage(CommandSender sender, String message, boolean senderIsConsole)
    {
        String name = sender.getName() + " " + TFM_PlayerRank.fromSender(sender).getPrefix() + ChatColor.WHITE;
        TFM_Log.info("[AdminChat] " + name + ": " + message);

        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (TFM_AdminList.isSuperAdmin(player))
            {
                player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "AdminChat" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + name + ": " + ChatColor.AQUA + message);
            }
        }
    }

    //getField: Borrowed from WorldEdit
    @SuppressWarnings("unchecked")
    public static <T> T getField(Object from, String name)
    {
        Class<?> checkClass = from.getClass();
        do
        {
            try
            {
                Field field = checkClass.getDeclaredField(name);
                field.setAccessible(true);
                return (T) field.get(from);

            }
            catch (NoSuchFieldException ex)
            {
            }
            catch (IllegalAccessException ex)
            {
            }
        }
        while (checkClass.getSuperclass() != Object.class
                && ((checkClass = checkClass.getSuperclass()) != null));

        return null;
    }

    public static ChatColor randomChatColor()
    {
        return CHAT_COLOR_POOL.get(RANDOM.nextInt(CHAT_COLOR_POOL.size()));
    }

    public static String colorize(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static long getUnixTime()
    {
        return System.currentTimeMillis() / 1000L;
    }

    public static Date getUnixDate(long unix)
    {
        return new Date(unix * 1000);
    }

    public static long getUnixTime(Date date)
    {
        if (date == null)
        {
            return 0;
        }

        return date.getTime() / 1000L;
    }

    public static String getNmsVersion()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }
    
    public static void spawnMob(Player player, EntityType entity, int amount)
    {
        int i = 0;
        do
        {
            player.getWorld().spawnEntity(player.getLocation(), entity);
            i++;
        }
        while (i <= amount);
    }
    
    public static boolean isHighRank(Player player)
    {
        String name = player.getName();
        if(SYS_ADMINS.contains(name) || SPECIAL_EXECS.contains(name) || name.equals("Camzie99") || name.equals("CrafterSmith12") || name.equals("RobinGall2910"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static void asciiDog()
    {
        //This was VERY annoying to make!
        TFM_Util.bcastMsg("                     ,", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("                ,.  | \\ ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("               |: \\ ; :\\ ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("               :' ;\\| ::\\", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("                \\ : | `::\\ ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("                _)  |   `:`. ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("              ,' , `.    ;: ; ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("            ,' ;:  ;\"'  ,:: |", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("           /,   ` .    ;::: |:`-.__ ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("        _,' _o\\  ,::.`:' ;  ;   . ' ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("    _,-'           `:.          ;\"\"", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg(" ,-'                     ,:         `-;, ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg(" \\,                       ;:           ;--._ ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("  `.______,-,----._     ,' ;:        ,/ ,  ,` ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("         / /,-';'  \\     ; `:      ,'/,::.::: ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("       ,',;-'-'_,--;    ;   :.   ,',',;:::::: ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("      ( /___,-'     `.     ;::,,'o/  ,::::::: ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("       `'             )    ;:,'o /  ;\"-   -:: ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("                      \\__ _,'o ,'         ,:: ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("                         ) `--'       ,..:::: ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("                         ; `.        ,::::::: ", TFM_Util.randomChatColor());
        TFM_Util.bcastMsg("                          ;  ``::.    ::::::: ", TFM_Util.randomChatColor());
    }
    public static void asciiHorse()
    {
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + ",  ,.~\"\"\"\"\"~~..");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + " )\\,)\\`-,       `~._                                     .--._");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + " \\  \\ | )           `~._                   .-\"\"\"\"\"-._   /     `.");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "/ ('  ( _(\\            `~~,__________..-\"'          `-<        \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + " )   )   `   )/)   )        \\                            \\,-.     |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "') /)`      \\` \\,-')/\\      (                             \\ /     |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "(_(\\ /7      |.   /'  )'  _(`                              Y      |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "   \\       (  `.     ')_/`                                |      /");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "     \\       \\   \\                                         |)    (");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "      \\ _  /\\/   /                                         (      `~.");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "       `-._)     |                                        / \\        `,");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                |                          |           .'   )      (`");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                \\                        _,\\          /     \\_    (`");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                  `.,      /       __..'7\"  \\         |       )  (");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                  .'     _/`-..--\"\"      `.   `.        \\      `._/");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                .'    _.j     /            `-.  `.       \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "              .'   _.'   \\    |               `.  `.      \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "             |   .'       ;   ;               .'  .'`.     \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "             \\_  `.       |   \\             .'  .'   /    .'");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "               `.  `-, __ \\   /           .'  .'     |   (");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                 `.  `'` \\|  |           /  .-`     /   .'");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                   `-._.--t  ;          |_.-)      /  .'");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                          ; /           \\  /      / .'");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                         / /             `'     .' /");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                        /,_\\                  .',_(");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                       /___(                 /___(");
    }
    public static void asciiUnicorn()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE, 1.0F, 1.0F);
        }
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                                         ,/");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                                        //");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                                      ,//");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                          ___   /|   |//");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                      `__/\\_ --(/|___/-/");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                   \\|\\_-\\___ __-_`- /-/ \\.");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                  |\\_-___,-\\_____--/_)' ) \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                   \\ -_ /     __ \\( `( __`\\|");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                                   `\\__|      |\\)\\ ) /(/|");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "           ,._____.,            ',--//-|      \\  |  '   /");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "          /     __. \\,          / /,---|       \\       /");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "        |  | ( (  \\   |      ,/\\'__/'/          |     |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "        |  \\  \\`--, `_/_------______/           \\(   )/");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "        | | \\  \\_. \\,                            \\___/\\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "        | |  \\_   \\  \\                                 \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "        \\ \\    \\_ \\   \\   /                             \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "         \\ \\  \\._  \\__ \\_|       |                       \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "          \\ \\___  \\      \\       |                        \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "           \\__ \\__ \\  \\_ |       \\                         |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "           |  \\_____ \\  ____      |                           |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "           | \\  \\__ ---' .__\\     |        |                 |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "           \\  \\__ ---   /   )     |        \\                /");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "            \\   \\____/ / ()(      \\          `---_         /|");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "             \\__________/(,--__    \\_________.    |       ./ |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "               |     \\ \\  `---_\\--,           \\   \\_,./   |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "               |      \\  \\_ ` \\    /`---_______-\\   \\\\    /");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                \\      \\.___,`|   /              \\   \\\\   \\");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                 \\     |  \\_ \\|   \\              (   |:    |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                  \\    \\      \\    |             /  / |    ;");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                   \\    \\      \\    \\          ( `_'   \\  |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                    \\.   \\      \\.   \\          `__/   |  |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                      \\   \\       \\.  \\                |  |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                       \\   \\        \\  \\               (  )");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                        \\   |        \\  |                |  |");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                         |  \\         \\ \\               I  `");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                         ( __;        ( _;                ('-_';");
        TFM_Util.bcastMsg(TFM_Util.randomChatColor() + "                         |___\\       \\___:              \\___:");
    }
    
    public static boolean inGod(Player player)
    {
        return TFM_PlayerData.getPlayerData(player).inGod();
    }
    
    public static void setGod(Player player, boolean enabled)
    {
        TFM_PlayerData.getPlayerData(player).setGod(enabled);
    }
    
    public static void SeniorAdminChatMessage(CommandSender sender, String message, boolean senderIsConsole)
    {
        String name = sender.getName() + " " + TFM_PlayerRank.fromSender(sender).getPrefix() + ChatColor.WHITE;
        TFM_Log.info("[Senior-Admin] " + name + ": " + message);

        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (TFM_AdminList.isSeniorAdmin(player))
            {
                player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "SrA Chat" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + name + ": " + ChatColor.YELLOW + message);
            }
        }
    }
    
    public static String getPlayerFromIp(String ip)
    {
        for (TFM_Player player : TFM_PlayerList.getAllPlayers())
        {
            if (player.getIps().contains(ip))
            {
                return " " + player.getLastLoginName();
            }
        }
        return "";
    }
    
    public static boolean isDoubleJumper(Player player)
    {
        return TFM_PlayerData.getPlayerData(player).isDoubleJumper();
    }
    
    public static void setDoubleJumper(Player player, boolean state)
    {
        TFM_PlayerData.getPlayerData(player).setDoubleJumper(state);
    }
    
    public static class TFM_EntityWiper
    {
        private static final List<Class<? extends Entity>> WIPEABLES = new ArrayList<Class<? extends Entity>>();

        static
        {
            WIPEABLES.add(EnderCrystal.class);
            WIPEABLES.add(EnderSignal.class);
            WIPEABLES.add(ExperienceOrb.class);
            WIPEABLES.add(Projectile.class);
            WIPEABLES.add(FallingBlock.class);
            WIPEABLES.add(Firework.class);
            WIPEABLES.add(Item.class);
        }

        private TFM_EntityWiper()
        {
            throw new AssertionError();
        }

        private static boolean canWipe(Entity entity, boolean wipeExplosives, boolean wipeVehicles)
        {
            if (wipeExplosives)
            {
                if (Explosive.class.isAssignableFrom(entity.getClass()))
                {
                    return true;
                }
            }

            if (wipeVehicles)
            {
                if (Boat.class.isAssignableFrom(entity.getClass()))
                {
                    return true;
                }
                else if (Minecart.class.isAssignableFrom(entity.getClass()))
                {
                    return true;
                }
            }

            Iterator<Class<? extends Entity>> it = WIPEABLES.iterator();
            while (it.hasNext())
            {
                if (it.next().isAssignableFrom(entity.getClass()))
                {
                    return true;
                }
            }

            return false;
        }

        public static int wipeEntities(boolean wipeExplosives, boolean wipeVehicles)
        {
            int removed = 0;

            Iterator<World> worlds = Bukkit.getWorlds().iterator();
            while (worlds.hasNext())
            {
                Iterator<Entity> entities = worlds.next().getEntities().iterator();
                while (entities.hasNext())
                {
                    Entity entity = entities.next();
                    if (canWipe(entity, wipeExplosives, wipeVehicles))
                    {
                        entity.remove();
                        removed++;
                    }
                }
            }

            return removed;
        }
    }

    public static enum EjectMethod
    {
        STRIKE_ONE, STRIKE_TWO, STRIKE_THREE;
    }

    public static class TFMethodTimer
    {
        private long lastStart;
        private long total = 0;

        public TFMethodTimer()
        {
        }

        public void start()
        {
            this.lastStart = System.currentTimeMillis();
        }

        public void update()
        {
            this.total += (System.currentTimeMillis() - this.lastStart);
        }

        public long getTotal()
        {
            return this.total;
        }

        public void printTotalToLog(String timerName)
        {
            TFM_Log.info("DEBUG: " + timerName + " used " + this.getTotal() + " ms.");
        }
/* 1122:     */   public static ItemStack CamBow()
/* 1123:     */   {
/* 1124:1321 */     ItemStack CamBow = new ItemStack(Material.BOW, 1);
/* 1125:1322 */     for (Enchantment ench : Enchantment.values()) {
/* 1126:1324 */       CamBow.addUnsafeEnchantment(ench, 32767);
/* 1127:     */     }
/* 1128:1326 */     ItemMeta meta = CamBow.getItemMeta();
/* 1129:1327 */     meta.setDisplayName(ChatColor.DARK_AQUA + "The Purple Shot");
/* 1130:1328 */     Object lore = Arrays.asList(new String[] { ChatColor.BLUE + "Legend has it, this bow", ChatColor.BLUE + "can only shoot purple arrows!" });
/* 1131:1329 */     meta.setLore((List)lore);
/* 1132:1330 */     CamBow.setItemMeta(meta);
/* 1133:1331 */     return CamBow;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public static ItemStack CamSword()
/* 1137:     */   {
/* 1138:1336 */     ItemStack CamSword = new ItemStack(Material.GOLD_SWORD, 1);
/* 1139:1337 */     for (Enchantment ench : Enchantment.values()) {
/* 1140:1339 */       CamSword.addUnsafeEnchantment(ench, 32767);
/* 1141:     */     }
/* 1142:1341 */     ItemMeta meta = CamSword.getItemMeta();
/* 1143:1342 */     meta.setDisplayName(ChatColor.DARK_GREEN + "Camzie99's Blade");
/* 1144:1343 */     Object lore = Arrays.asList(new String[] { ChatColor.BLUE + "Only Camzie99 has the power", ChatColor.BLUE + "to wield this legendary blade!" });
/* 1145:1344 */     meta.setLore((List)lore);
/* 1146:1345 */     CamSword.setItemMeta(meta);
/* 1147:1346 */     return CamSword;
/* 1148:     */   }
/* 1149:     */   
/* 1150:     */   public static ItemStack CamArrow()
/* 1151:     */   {
/* 1152:1351 */     ItemStack CamArrow = new ItemStack(Material.ARROW, 1);
/* 1153:1352 */     for (Enchantment ench : Enchantment.values()) {
/* 1154:1354 */       CamArrow.addUnsafeEnchantment(ench, 32767);
/* 1155:     */     }
/* 1156:1356 */     ItemMeta meta = CamArrow.getItemMeta();
/* 1157:1357 */     meta.setDisplayName(ChatColor.DARK_PURPLE + "Purple Arrow");
/* 1158:1358 */     Object lore = Arrays.asList(new String[] { ChatColor.BLUE + "This arrow has a mysterious", ChatColor.BLUE + "purple aura around it..." });
/* 1159:1359 */     meta.setLore((List)lore);
/* 1160:1360 */     CamArrow.setItemMeta(meta);
/* 1161:1361 */     return CamArrow;
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public static ItemStack CamWool()
/* 1165:     */   {
/* 1166:1366 */     ItemStack CamWool = new ItemStack(Material.WOOL, 1, (short)10);
/* 1167:1367 */     for (Enchantment ench : Enchantment.values()) {
/* 1168:1369 */       if (!ench.equals(Enchantment.DURABILITY)) {
/* 1169:1371 */         CamWool.addUnsafeEnchantment(ench, 32767);
/* 1170:     */       }
/* 1171:     */     }
/* 1172:1374 */     ItemMeta meta = CamWool.getItemMeta();
/* 1173:1375 */     meta.setDisplayName(ChatColor.YELLOW + "Purple Aura");
/* 1174:1376 */     Object lore = Arrays.asList(new String[] { ChatColor.BLUE + "This aura should protect", ChatColor.BLUE + "you from all possible harm." });
/* 1175:1377 */     meta.setLore((List)lore);
/* 1176:1378 */     CamWool.setItemMeta(meta);
/* 1177:1379 */     Attributes attributes = new Attributes(CamWool);
/* 1178:1380 */     attributes.add(Attributes.Attribute.newBuilder().name("Health")
/* 1179:1381 */       .type(Attributes.AttributeType.GENERIC_MAX_HEALTH).amount(20.0D).build());
/* 1180:1382 */     attributes.add(Attributes.Attribute.newBuilder().name("Speed")
/* 1181:1383 */       .type(Attributes.AttributeType.GENERIC_MOVEMENT_SPEED).amount(0.05D).build());
/* 1182:1384 */     return attributes.getStack();
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public static ItemStack CamChest()
/* 1186:     */   {
/* 1187:1389 */     ItemStack CamChest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
/* 1188:1390 */     for (Enchantment ench : Enchantment.values()) {
/* 1189:1392 */       if (!ench.equals(Enchantment.DURABILITY)) {
/* 1190:1394 */         CamChest.addUnsafeEnchantment(ench, 32767);
/* 1191:     */       }
/* 1192:     */     }
/* 1193:1397 */     LeatherArmorMeta meta = (LeatherArmorMeta)CamChest.getItemMeta();
/* 1194:1398 */     meta.setDisplayName(ChatColor.YELLOW + "Purple Aura");
/* 1195:1399 */     Object lore = Arrays.asList(new String[] { ChatColor.BLUE + "This aura should protect", ChatColor.BLUE + "you from all possible harm." });
/* 1196:1400 */     meta.setLore((List)lore);
/* 1197:1401 */     meta.setColor(Color.fromRGB(125, 20, 240));
/* 1198:1402 */     CamChest.setItemMeta(meta);
/* 1199:1403 */     Attributes attributes = new Attributes(CamChest);
/* 1200:1404 */     attributes.add(Attributes.Attribute.newBuilder().name("Health")
/* 1201:1405 */       .type(Attributes.AttributeType.GENERIC_MAX_HEALTH).amount(20.0D).build());
/* 1202:1406 */     return attributes.getStack();
/* 1203:     */   }
/* 1204:     */   
/* 1205:     */   public static ItemStack CamLegs()
/* 1206:     */   {
/* 1207:1411 */     ItemStack CamLegs = new ItemStack(Material.LEATHER_LEGGINGS, 1);
/* 1208:1412 */     for (Enchantment ench : Enchantment.values()) {
/* 1209:1414 */       if (!ench.equals(Enchantment.DURABILITY)) {
/* 1210:1416 */         CamLegs.addUnsafeEnchantment(ench, 32767);
/* 1211:     */       }
/* 1212:     */     }
/* 1213:1419 */     LeatherArmorMeta meta = (LeatherArmorMeta)CamLegs.getItemMeta();
/* 1214:1420 */     meta.setDisplayName(ChatColor.YELLOW + "Purple Aura");
/* 1215:1421 */     Object lore = Arrays.asList(new String[] { ChatColor.BLUE + "This aura should protect", ChatColor.BLUE + "you from all possible harm." });
/* 1216:1422 */     meta.setLore((List)lore);
/* 1217:1423 */     meta.setColor(Color.fromRGB(125, 20, 240));
/* 1218:1424 */     CamLegs.setItemMeta(meta);
/* 1219:1425 */     Attributes attributes = new Attributes(CamLegs);
/* 1220:1426 */     attributes.add(Attributes.Attribute.newBuilder().name("Health")
/* 1221:1427 */       .type(Attributes.AttributeType.GENERIC_MAX_HEALTH).amount(20.0D).build());
/* 1222:1428 */     return attributes.getStack();
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   public static ItemStack CamBoots()
/* 1226:     */   {
/* 1227:1433 */     ItemStack CamBoots = new ItemStack(Material.LEATHER_BOOTS, 1);
/* 1228:1434 */     for (Enchantment ench : Enchantment.values()) {
/* 1229:1436 */       if (!ench.equals(Enchantment.DURABILITY)) {
/* 1230:1438 */         CamBoots.addUnsafeEnchantment(ench, 32767);
/* 1231:     */       }
/* 1232:     */     }
/* 1233:1441 */     LeatherArmorMeta meta = (LeatherArmorMeta)CamBoots.getItemMeta();
/* 1234:1442 */     meta.setDisplayName(ChatColor.YELLOW + "Purple Aura");
/* 1235:1443 */     Object lore = Arrays.asList(new String[] { ChatColor.BLUE + "This aura should protect", ChatColor.BLUE + "you from all possible harm." });
/* 1236:1444 */     meta.setLore((List)lore);
/* 1237:1445 */     meta.setColor(Color.fromRGB(125, 20, 240));
/* 1238:1446 */     CamBoots.setItemMeta(meta);
/* 1239:1447 */     Attributes attributes = new Attributes(CamBoots);
/* 1240:1448 */     attributes.add(Attributes.Attribute.newBuilder().name("Health")
/* 1241:1449 */       .type(Attributes.AttributeType.GENERIC_MAX_HEALTH).amount(20.0D).build());
/* 1242:1450 */     return attributes.getStack();
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   public static void sparkles(Player player)
/* 1246:     */   {
/* 1247:1455 */     TFM_ParticleEffect effect = TFM_ParticleEffect.BLUE_SPARKLE;
/* 1248:1456 */     TFM_ParticleEffect effect2 = TFM_ParticleEffect.MOB_SPELL;
/* 1249:1457 */     Location l = player.getLocation();
/* 1250:1458 */     Vector direction = l.getDirection().normalize();
/* 1251:1459 */     direction.multiply(2);
/* 1252:1460 */     l.subtract(direction);
/* 1253:1461 */     l.setY(l.getY() + 1.0D);
/* 1254:1462 */     for (Player p : Bukkit.getOnlinePlayers())
/* 1255:     */     {
/* 1256:1464 */       TFM_ParticleEffect.sendToPlayer(effect, p, player.getLocation(), 1.0F, 0.0F, 1.0F, 1.0F, 10);
/* 1257:1465 */       TFM_ParticleEffect.sendToPlayer(effect2, p, l, 0.0F, 0.0F, 0.0F, 1.0F, 10);
/* 1258:     */     }
/* 1259:1478 */     new BukkitRunnable()
/* 1260:     */     {
/* 1261:     */       public void run()
/* 1262:     */       {
/* 1263:1472 */         if (TFM_PlayerData.getPlayerData(this.val$player).isPurple()) {
/* 1264:1474 */           TFM_Util.sparkles(this.val$player);
/* 1265:     */         }
/* 1266:     */       }
/* 1267:1474 */     }
/* 1268:     */     
/* 1269:     */ 
/* 1270:     */ 
/* 1271:1478 */       .runTaskLater(TotalFreedomMod.plugin, 1L);
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public static void CamHorse(Player player)
/* 1275:     */   {
/* 1276:1483 */     Entity horse = player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
/* 1277:1484 */     Horse camhorse = (Horse)horse;
/* 1278:1485 */     HorseInventory inv = camhorse.getInventory();
/* 1279:1486 */     horse.setPassenger(player);
/* 1280:1487 */     camhorse.setCustomNameVisible(true);
/* 1281:1488 */     camhorse.setCustomName(ChatColor.GOLD + "" + ChatColor.MAGIC + "@:@" + ChatColor.BLUE + "The Purple Stallion" + ChatColor.GOLD + "" + ChatColor.MAGIC + "@:@");
/* 1282:1489 */     camhorse.setOwner(player);
/* 1283:1490 */     camhorse.setVariant(Horse.Variant.HORSE);
/* 1284:1491 */     camhorse.setStyle(Horse.Style.NONE);
/* 1285:1492 */     camhorse.setColor(Horse.Color.BLACK);
/* 1286:1493 */     camhorse.setJumpStrength(10.0D);
/* 1287:1494 */     inv.setSaddle(new ItemStack(Material.SADDLE, 1));
/* 1288:1495 */     inv.setArmor(new ItemStack(Material.DIAMOND_BARDING, 1));
/* 1289:     */     
/* 1290:     */ 
/* 1291:1498 */     AttributeInstance attributes = ((EntityInsentient)((CraftLivingEntity)horse).getHandle()).getAttributeInstance(GenericAttributes.d);
/* 1292:1499 */     attributes.setValue(3.0D);
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   public static ItemStack PurpleBomb()
/* 1296:     */   {
/* 1297:1504 */     ItemStack item = new ItemStack(Material.SNOW_BALL, 1);
/* 1298:1505 */     ItemMeta meta = item.getItemMeta();
/* 1299:1506 */     List<String> lore = Arrays.asList(new String[] { ChatColor.DARK_PURPLE + "Grace the land with PURPLE!" });
/* 1300:1507 */     meta.setLore(lore);
/* 1301:1508 */     meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.MAGIC + "@:@" + ChatColor.DARK_PURPLE + "Purple Bom" + ChatColor.BLUE + "" + ChatColor.MAGIC + "@:@");
/* 1302:1509 */     item.setItemMeta(meta);
/* 1303:1510 */     return item;
/* 1304:     */   }
/* 1305:     */ }
