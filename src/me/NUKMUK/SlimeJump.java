package me.NUKMUK;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class SlimeJump extends JavaPlugin implements Listener {

    private ArrayList<String> cooldown = new ArrayList<String>();
    private Double pt = getConfig().getDouble("particletime");

    private int taskId = 0;

    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    cooldown.remove(pl.getName());
                    pl.sendTitle("", "");
                    Bukkit.getScheduler().cancelTasks(getServer().getPluginManager().getPlugin("SlimeJump"));
                }
            }
        }, 100, 100);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (getConfig().getBoolean("slime")) {
            if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK) {
                if(!e.getPlayer().isSneaking()) {
                    if (!cooldown.contains(e.getPlayer().getName())) {
                        cooldown.add(e.getPlayer().getName());
                        final Player p = e.getPlayer();
                        Double slh = this.getConfig().getDouble("slimeheight") / 10;
                        p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(getConfig().getDouble("slimelength")));
                        p.setVelocity(new Vector(e.getPlayer().getVelocity().getX(), slh, e.getPlayer().getVelocity().getZ()));
                        Location l = e.getPlayer().getLocation();
                        p.getWorld().playEffect(e.getPlayer().getLocation().getBlock().getLocation().add(0, -0.5, 0), Effect.MOBSPAWNER_FLAMES, 1);
                        p.playEffect(e.getPlayer().getLocation().add(0, -2, 0), Effect.GHAST_SHOOT, 0);

                        taskId = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                            int count = 0;

                            public void run() {
                                if (getConfig().getBoolean("titlecooldown")) {
                                    if (count == pt - 15) {
                                        p.sendTitle(ChatColor.GREEN + "                              |||", "");
                                    } else if (count == pt - 10) {
                                        p.sendTitle(ChatColor.GREEN + "                              ||", "");
                                    } else if (count == pt - 5) {
                                        p.sendTitle(ChatColor.GREEN + "                              |", "");
                                    } else if (count == pt) {
                                        p.sendTitle("", "");
                                        cooldown.remove(p.getName());

                                    }
                                }
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.CLOUD, 1);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.COLOURED_DUST, 1);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORKS_SPARK, 1);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORKS_SPARK, 0);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORKS_SPARK, 1);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.FLAME, 0);
                                if (count >= getConfig().getInt("particletime")) {
                                    Bukkit.getScheduler().cancelTask(taskId);
                                    cooldown.remove(p.getName());

                                }
                                count++;
                            }
                        }, 0L, 2L);
                    }
                }
            }
        }

        if (getConfig().getBoolean("sponge")) {
            if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE || e.getTo().getBlock().getRelative(BlockFace.UP).getType() == Material.SPONGE) {
                if(!e.getPlayer().isSneaking()) {
                    if (!cooldown.contains(e.getPlayer().getName())) {
                        cooldown.add(e.getPlayer().getName());
                        final Player p = e.getPlayer();
                        Double sph = this.getConfig().getDouble("spongeheight") / 10;
                        p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(getConfig().getDouble("spongelength")));
                        p.setVelocity(new Vector(e.getPlayer().getVelocity().getX(), sph, e.getPlayer().getVelocity().getZ()));
                        Location l = e.getPlayer().getLocation();
                        p.getWorld().playEffect(e.getPlayer().getLocation().getBlock().getLocation().add(0, -0.5, 0), Effect.MOBSPAWNER_FLAMES, 1);
                        p.playEffect(e.getPlayer().getLocation().add(0, -2, 0), Effect.GHAST_SHOOT, 0);

                        taskId = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                            int count = 0;

                            public void run() {
                                if (getConfig().getBoolean("titlecooldown")) {
                                    if (count == pt - 15) {
                                        p.sendTitle(ChatColor.YELLOW + "                              |||", "");
                                    } else if (count == pt - 10) {
                                        p.sendTitle(ChatColor.YELLOW + "                              ||", "");
                                    } else if (count == pt - 5) {
                                        p.sendTitle(ChatColor.YELLOW + "                              |", "");
                                    } else if (count >= pt - 1) {
                                        p.sendTitle("", "");
                                        cooldown.remove(p.getName());
                                    }
                                }
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.CLOUD, 1);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.COLOURED_DUST, 1);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORKS_SPARK, 1);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORKS_SPARK, 0);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORKS_SPARK, 1);
                                p.getWorld().playEffect(p.getEyeLocation(), Effect.FLAME, 0);
                                if (count >= getConfig().getInt("particletime") - 1) {
                                    Bukkit.getScheduler().cancelTask(taskId);
                                    cooldown.remove(p.getName());
                                }
                                count++;
                            }
                        }, 0L, 2L);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setDamage(e.getDamage() / getConfig().getInt("falldmg"));
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (getConfig().getBoolean("limeclay")) {

            if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.STAINED_CLAY) {
                Block block = e.getTo().getBlock().getRelative(BlockFace.DOWN);
                Wool wool = new Wool(block.getType(), block.getData());
                if (wool.getColor() == DyeColor.LIME) {
                    e.getPlayer().setWalkSpeed((float) getConfig().getDouble("clay-speed"));


                }
            }
        }
        if (getConfig().getBoolean("limeclay")) {
            if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() != Material.STAINED_CLAY) {
                Block block = e.getTo().getBlock().getRelative(BlockFace.DOWN);
                Wool wool = new Wool(block.getType(), block.getData());
                if (wool.getColor() != DyeColor.LIME) {
                    e.getPlayer().setWalkSpeed((float) getConfig().getDouble("normal-speed"));
                }
            }
        }
    }
}