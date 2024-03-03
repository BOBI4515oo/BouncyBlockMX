package BouncyBlocksMX;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.level.Location;

import java.util.HashMap;
import java.util.Map;

public class Main extends PluginBase implements Listener {

    private Map<Integer, Double> bouncyBlocks = new HashMap<>();
    private double defaultJumpPower = 1.0;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().scheduleRepeatingTask(new BounceTask(), 20);
        this.getLogger().info(TextFormat.GREEN + "Плагин BouncyBlocksMX включен!");

        bouncyBlocks.put(Block.STONE_PRESSURE_PLATE, 2.5);
        bouncyBlocks.put(147, 2.0);
        bouncyBlocks.put(148, 1.7);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.RED + "Плагин BouncyBlocksMX выключен!");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Block blockBelowFeet = location.subtract(0, 1, 0).getLevelBlock();
        Block blockBelowHead = location.subtract(0, 2, 0).getLevelBlock();

        if (bouncyBlocks.containsKey(blockBelowFeet.getId())) {
            double jumpPower = bouncyBlocks.get(blockBelowFeet.getId());
            player.setMotion(player.getMotion().add(0, jumpPower, 0));
        } else if (bouncyBlocks.containsKey(blockBelowHead.getId())) {
            double jumpPower = bouncyBlocks.get(blockBelowHead.getId());
            player.setMotion(player.getMotion().add(0, jumpPower, 0));
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled();
        }
    }

    class BounceTask extends Task {
        @Override
        public void onRun(int currentTick) {

        }
    }
}
