package fr.kizyow.mobshop.datas;

import fr.kizyow.mobshop.utils.TimeUnit;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class MobData {

    private final EntityType entityType;
    private final UUID uuid;
    private final double price;
    private final long expireAt;

    public MobData(EntityType entityType, UUID uuid, double price){
        this.entityType = entityType;
        this.uuid = uuid;
        this.price = price;
        this.expireAt = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7);
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

    public long getExpireAt(){
        return expireAt;
    }

    public String getTimeLeft(){

        long timeLeft = (expireAt - System.currentTimeMillis()) / 1000;
        int days = 0;
        int hours = 0;
        int minutes = 0;

        while(timeLeft >= TimeUnit.DAY.getToSecond()){
            days++;
            timeLeft -= TimeUnit.DAY.getToSecond();

        }

        while(timeLeft >= TimeUnit.HOUR.getToSecond()){
            hours++;
            timeLeft -= TimeUnit.HOUR.getToSecond();

        }

        while(timeLeft >= TimeUnit.MINUTE.getToSecond()){
            minutes++;
            timeLeft -= TimeUnit.MINUTE.getToSecond();

        }

        return days + TimeUnit.DAY.getShortcut() + " " +
                hours + TimeUnit.HOUR.getShortcut() + " " +
                minutes + TimeUnit.MINUTE.getShortcut();


    }



    @Override
    public String toString() {
        return "MobData{" +
                "entityType=" + entityType +
                ", uuid=" + uuid +
                ", price=" + price +
                '}';
    }
}
