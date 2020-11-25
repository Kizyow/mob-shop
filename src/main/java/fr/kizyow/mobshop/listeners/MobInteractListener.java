package fr.kizyow.mobshop.listeners;

import com.gamingmesh.jobs.Jobs;
import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.inventories.SellInventory;
import me.angeschossen.lands.api.integration.LandsIntegration;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.List;

public class MobInteractListener implements Listener {

    private final Plugin plugin;
    private final LandsIntegration landsIntegration;

    public MobInteractListener(Plugin plugin){
        this.plugin = plugin;
        this.landsIntegration = new LandsIntegration(plugin);
    }

    @EventHandler
    public void onMobInteract(EntityDamageByEntityEvent event){

        List<EntityType> entities = plugin.getMobShopConfig().getShopEntities();
        boolean isInList = entities.stream().anyMatch(type -> type == event.getEntityType());

        if(event.getDamager() instanceof Player && event.getCause() == DamageCause.ENTITY_ATTACK && isInList){

            Entity mob = event.getEntity();
            Player player = (Player) event.getDamager();
            Location location = player.getLocation().clone();

            if(!landsIntegration.isClaimed(location)){
                return;
            }

            boolean isPlayerFarmer = Jobs.getPlayerManager().getJobsPlayer(player).isInJob(Jobs.getJob("Farmer"));
            boolean isPlayerClaimOwn = landsIntegration.getLand(location).getOwnerUID().equals(player.getUniqueId());

            if(isPlayerFarmer && isPlayerClaimOwn){

                event.setCancelled(true);
                SellInventory sellInventory = new SellInventory(plugin, mob);
                sellInventory.getInventory().open(player);

            }

        }

    }

}
