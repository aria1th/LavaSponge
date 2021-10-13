//mod menu code from https://github.com/SRAZKVT/CutefulMod
//keybinding does not work >_>;
package aria1th.main.lavasponge;

import aria1th.main.lavasponge.config.Configs;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;

public class LavaSpongeMod implements ModInitializer {

    public static final String MOD_ID = "LavaSponge";

    @Override
    public void onInitialize() {
        try {
            new Configs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
