package fr.kizyow.mobshop.utils;

import com.google.gson.Gson;
import fr.kizyow.mobshop.Plugin;
import fr.kizyow.mobshop.datas.MobData;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JsonData {

    public static void saveData(Map<Integer, MobData> mobDataMap) {

        if (mobDataMap == null) return;

        try {

            String path = Plugin.getInstance().getDataFolder().getPath();
            File file = new File(path + "/data.json");
            file.createNewFile();

            Gson gson = new Gson();
            Writer writer = Files.newBufferedWriter(Paths.get(path + "/data.json"));

            gson.toJson(mobDataMap, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<Integer, MobData> loadData() {

        try {

            String path = Plugin.getInstance().getDataFolder().getPath();

            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path + "/data.json"));

            Map<?, ?> jsonMap = gson.fromJson(reader, Map.class);
            Map<Integer, MobData> outMap = new HashMap<>();

            for (Entry<?, ?> entry : jsonMap.entrySet()) {

                Integer id = Integer.valueOf(String.valueOf(entry.getKey()));
                MobData mobData = gson.fromJson(String.valueOf(entry.getValue()), MobData.class);

                outMap.put(id, mobData);

            }

            return outMap;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();

    }

}
