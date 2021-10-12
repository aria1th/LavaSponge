package aria1th.main.lavasponge.config;

import aria1th.main.lavasponge.IOption;
import aria1th.main.lavasponge.utils.LavaSpongeMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Configs extends GameOptions {
    public static Configs instance;
    private final File configFile = new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "lavasponge");
    public CyclingOption<Boolean>[] allBooleanConfigs;

    private boolean lavaSpongeIsOn = false;
    private boolean SlimeBlockPlacingEnabled = false;


    public Configs() throws IOException {
        super(MinecraftClient.getInstance(), new File(new File(MinecraftClient.getInstance().runDirectory, "config"), "lavasponge"));
        instance = this;
        allBooleanConfigs = new CyclingOption[]{
                Config.LAVASPONGE_ON,
                Config.SLIME_ENABLED
        };
        loadFromFile();
    }

    private void loadFromFile() throws IOException {
        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            Scanner reader = new Scanner(configFile);
            while (reader.hasNextLine()) {
                String[] configWord;
                String line = reader.nextLine();
                configWord = line.split(" ");
                if (configWord.length > 1) {
                    if ("LavaSpongeIsOn".equals(configWord[0])) {
                        lavaSpongeIsOn = (configWord[1].equals("true"));
                    } else if ("SlimeBlockBreakingEnabled".equals(configWord[0])){
                        SlimeBlockPlacingEnabled = (configWord[1].equals("true"));
                    }
                    System.out.println("LavaSponge : Loaded " + configWord[0] + " as " + configWord[1]);
                } else {
                    System.out.println("LavaSponge : The config file is invalid");
                }
            }
        } else {
            System.out.println("LavaSponge : Couldn't find config file, or the file is invalid");
        }
        saveToFile();
    }

    public void saveToFile() throws IOException {
        configFile.delete();
        FileWriter fw = new FileWriter(configFile);
        for (CyclingOption<Boolean> config : allBooleanConfigs) {
            String configKey = ((IOption)config).getKey();
            if ("LavaSpongeIsOn".equals(configKey)) {
                fw.write(((IOption) config).getKey() + " " + Configs.getlavaSpongeIsOn() + "\n");
            } else if ("SlimeBlockBreakingEnabled".equals(configKey)){
                fw.write(((IOption) config).getKey() + " " + Configs.getSlimeBlockPlacingEnabled() + "\n");
            }
        }
        fw.close();
    }

    public static Configs getInstance() {
        return instance;
    }

    public static void setAll(boolean value){
        Configs.setlavaSpongeIsOn(value);

    }

    public static void setlavaSpongeIsOn(boolean value) {
        Configs.getInstance().lavaSpongeIsOn = value;
        LavaSpongeMain.refreshInstance();
    }
    public static boolean getlavaSpongeIsOn() {
        return Configs.getInstance().lavaSpongeIsOn;
    }
    public static void setSlimeBlockPlacingEnabled(boolean value) {
        Configs.getInstance().SlimeBlockPlacingEnabled = value;
        LavaSpongeMain.refreshInstance();
    }
    public static boolean getSlimeBlockPlacingEnabled() {
        return Configs.getInstance().SlimeBlockPlacingEnabled;
    }


}