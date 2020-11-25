package fr.kizyow.mobshop.datas;

import org.bukkit.entity.EntityType;

import java.util.UUID;

public class MobData {

    private final EntityType entityType;
    private final UUID uuid;
    private final double price;

    public MobData(EntityType entityType, UUID uuid, double price){
        this.entityType = entityType;
        this.uuid = uuid;
        this.price = price;
    }

    public EntityType getEntityType(){
        return entityType;
    }

    public UUID getUUID(){
        return uuid;
    }

    public double getPrice(){
        return price;
    }

}
