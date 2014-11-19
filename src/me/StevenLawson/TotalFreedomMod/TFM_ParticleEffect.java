/*   1:    */ package me.StevenLawson.TotalFreedomMod;
/*   2:    */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
/*  10:    */
/*  11:    */ public enum TFM_ParticleEffect
/*  12:    */ {
    /*  13: 12 */ HUGE_EXPLODE("hugeexplosion", 0), LARGE_EXPLODE("largeexplode", 1), FIREWORK_SPARK("fireworksSpark", 2), AIR_BUBBLE("bubble", 3), SUSPEND("suspend", 4), DEPTH_SUSPEND("depthSuspend", 5), TOWN_AURA("townaura", 6), CRITICAL_HIT("crit", 7), MAGIC_CRITICAL_HIT("magicCrit", 8), MOB_SPELL("mobSpell", 9), MOB_SPELL_AMBIENT("mobSpellAmbient", 10), SPELL("spell", 11), INSTANT_SPELL("instantSpell", 12), BLUE_SPARKLE("witchMagic", 13), NOTE_BLOCK("note", 14), ENDER("portal", 15), ENCHANTMENT_TABLE("enchantmenttable", 16), EXPLODE("explode", 17), FIRE("flame", 18), LAVA_SPARK("lava", 19), FOOTSTEP("footstep", 20), SPLASH("splash", 21), LARGE_SMOKE("largesmoke", 22), CLOUD("cloud", 23), REDSTONE_DUST("reddust", 24), SNOWBALL_HIT("snowballpoof", 25), DRIP_WATER("dripWater", 26), DRIP_LAVA("dripLava", 27), SNOW_DIG("snowshovel", 28), SLIME("slime", 29), HEART("heart", 30), ANGRY_VILLAGER("angryVillager", 31), GREEN_SPARKLE("happyVillager", 32), ICONCRACK("iconcrack", 33), TILECRACK("tilecrack", 34);
    /*  14:    */
    /*  15:    */    private String name;
    /*  16:    */    private int id;
    /*  17:    */
    /*  18:    */ private TFM_ParticleEffect(String name, int id)
    /*  19:    */ {
        /*  20: 27 */ this.name = name;
        /*  21: 28 */ this.id = id;
        /*  22:    */    }
    /*  23:    */
    /*  24:    */    String getName()
    /*  25:    */ {
        /*  26: 37 */ return this.name;
        /*  27:    */    }
    /*  28:    */
    /*  29:    */    int getId()
    /*  30:    */ {
        /*  31: 46 */ return this.id;
        /*  32:    */    }
    /*  33:    */
    /*  34:    */ public static void sendToPlayer(TFM_ParticleEffect effect, Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count)
    /*  35:    */ {
        /*  36:    */ try
        /*  37:    */ {
            /*  38: 73 */ Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
            /*  39: 74 */ sendPacket(player, packet);
            /*  40:    */        }
        /*  41:    */ catch (Exception e)
        /*  42:    */ {
            /*  43: 76 */ e.printStackTrace();
            /*  44:    */        }
        /*  45:    */    }
    /*  46:    */
    /*  47:    */ public static void sendToLocation(TFM_ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count)
    /*  48:    */ {
        /*  49:    */ try
        /*  50:    */ {
            /*  51:102 */ Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
            /*  52:103 */ for (Player player : Bukkit.getOnlinePlayers())
            {
                /*  53:104 */ sendPacket(player, packet);
                /*  54:    */            }
            /*  55:    */        }
        /*  56:    */ catch (Exception e)
        /*  57:    */ {
            /*  58:107 */ e.printStackTrace();
            /*  59:    */        }
        /*  60:    */    }
    /*  61:    */
    /*  62:    */ private static Object createPacket(TFM_ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count)
            /*  63:    */ throws Exception
    /*  64:    */ {
        /*  65:113 */ if (count <= 0)
        {
            /*  66:114 */ count = 1;
            /*  67:    */        }
        /*  68:116 */ Class<?> packetClass = getCraftClass("PacketPlayOutWorldParticles");
        /*  69:    */
        /*  70:118 */ Object packet = packetClass.getConstructor(new Class[]
        {
            String.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE
        }).newInstance(new Object[]
        {
            effect.name, Float.valueOf((float) location.getX()),
            /*  71:119 */ Float.valueOf((float) location.getY()), Float.valueOf((float) location.getZ()), Float.valueOf(offsetX), Float.valueOf(offsetY), Float.valueOf(offsetZ), Float.valueOf(speed), Integer.valueOf(count)
        });
        /*  72:120 */ return packet;
        /*  73:    */    }
    /*  74:    */
    /*  75:    */ private static void sendPacket(Player p, Object packet)
            /*  76:    */ throws Exception
    /*  77:    */ {
        /*  78:124 */ Object eplayer = getHandle(p);
        /*  79:125 */ Field playerConnectionField = eplayer.getClass().getField("playerConnection");
        /*  80:126 */ Object playerConnection = playerConnectionField.get(eplayer);
        /*  81:127 */ for (Method m : playerConnection.getClass().getMethods())
        {
            /*  82:128 */ if (m.getName().equalsIgnoreCase("sendPacket"))
            /*  83:    */ {
                /*  84:129 */ m.invoke(playerConnection, new Object[]
                {
                    packet
                });
                /*  85:130 */ return;
                /*  86:    */            }
            /*  87:    */        }
        /*  88:    */    }
    /*  89:    */
    /*  90:    */ private static Object getHandle(Entity entity)
    /*  91:    */ {
        /*  92:    */ try
        /*  93:    */ {
            /*  94:137 */ Method entity_getHandle = entity.getClass().getMethod("getHandle", new Class[0]);
            /*  95:138 */ return entity_getHandle.invoke(entity, new Object[0]);
            /*  96:    */        }
        /*  97:    */ catch (Exception ex)
        /*  98:    */ {
            /*  99:141 */ ex.printStackTrace();
            /* 100:    */        }
        /* 101:142 */ return null;
        /* 102:    */    }
    /* 103:    */
    /* 104:    */ private static Class<?> getCraftClass(String name)
    /* 105:    */ {
        /* 106:147 */ String version = getVersion() + ".";
        /* 107:148 */ String className = "net.minecraft.server." + version + name;
        /* 108:149 */ Class<?> clazz = null;
        /* 109:    */ try
        /* 110:    */ {
            /* 111:151 */ clazz = Class.forName(className);
            /* 112:    */        }
        /* 113:    */ catch (ClassNotFoundException e)
        /* 114:    */ {
            /* 115:153 */ e.printStackTrace();
            /* 116:    */        }
        /* 117:155 */ return clazz;
        /* 118:    */    }
    /* 119:    */
    /* 120:    */ private static String getVersion()
    /* 121:    */ {
        /* 122:159 */ return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        /* 123:    */    }
    /* 124:    */ }

/* Location:           Z:\home\robingall2910\NetBeansProjects\FreedomOPMod\freedomopmod\FreedomOpMod.jar

 * Qualified Name:     me.StevenLawson.TotalFreedomMod.TFM_ParticleEffect

 * JD-Core Version:    0.7.0.1

 */
