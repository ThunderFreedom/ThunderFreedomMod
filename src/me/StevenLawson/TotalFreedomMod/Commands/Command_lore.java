package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.ONLY_IN_GAME)
@CommandParameters(
        description = "Add Lore to the current item in your hand.",
        usage = "/<command> <line> <lore>",
        aliases = "lr")
public class Command_lore extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 2)
        {
            return false;
        }
        String loreRaw = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        String itemLore = TFM_Util.colorize(loreRaw.trim());
        ItemStack i = sender_p.getItemInHand();
        if (i != null)
        {
            ItemMeta im = i.getItemMeta();
            List<String> lore = im.getLore();
            lore.set(Integer.parseInt(args[0]), itemLore);
            i.setItemMeta(im);
        }
        return true;
    }
}
