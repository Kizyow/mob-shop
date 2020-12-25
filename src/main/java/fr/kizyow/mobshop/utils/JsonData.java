package fr.kizyow.mobshop.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.MobData;
import org.bukkit.Bukkit;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public class JsonData {

    public static void saveMobData(Map<Integer, MobData> mobDataMap) {

        if (mobDataMap == null) return;

        try {

            String path = Plugin.getInstance().getDataFolder().getPath() + "\\data";
            File directory = new File(path);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(path + "/mobdata.json");
            file.createNewFile();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get(path + "/mobdata.json"));

            gson.toJson(mobDataMap, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<Integer, MobData> loadMobData() {

        try {

            String path = Plugin.getInstance().getDataFolder().getPath() + "\\data";

            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path + "/mobdata.json"));

            Map<?, ?> jsonMap = gson.fromJson(reader, Map.class);
            Map<Integer, MobData> outMap = new HashMap<>();

            for (Entry<?, ?> entry : jsonMap.entrySet()) {

                Integer id = Integer.valueOf(String.valueOf(entry.getKey()));
                MobData mobData = gson.fromJson(String.valueOf(entry.getValue()), MobData.class);

                outMap.put(id, mobData);

            }

            return outMap;

        } catch (IOException e) {
            Bukkit.getLogger().warning("The file mobdata.json wasn't found! Creating new blank one");
        }

        return new HashMap<>();

    }

    public static void savePlayerData(Map<UUID, List<String>> data) {

        if (data == null) return;

        try {

            String path = Plugin.getInstance().getDataFolder().getPath() + "\\data";
            File directory = new File(path);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(path + "/playerdata.json");
            file.createNewFile();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get(path + "/playerdata.json"));

            gson.toJson(data, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<UUID, List<String>> loadPlayerData() {

        try {

            String path = Plugin.getInstance().getDataFolder().getPath() + "\\data";

            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path + "/playerdata.json"));

            Map<?, ?> jsonMap = gson.fromJson(reader, Map.class);
            Map<UUID, List<String>> outMap = new HashMap<>();

            for (Entry<?, ?> entry : jsonMap.entrySet()) {

                Type listType = new TypeToken<List<String>>() {
                }.getType();

                UUID uuid = UUID.fromString(String.valueOf(entry.getKey()));

                List<String> list = gson.fromJson(gson.toJson(entry.getValue()), listType);

                outMap.put(uuid, new ArrayList<>(list));

            }

            return outMap;

        } catch (IOException e) {
            Bukkit.getLogger().warning("The file playerdata.json wasn't found! Creating new blank one");
        }

        return new HashMap<>();

    }

}
