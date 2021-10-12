package aria1th.main.lavasponge.config;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;

public abstract class Config extends Option {

    public static final CyclingOption<Boolean> LAVASPONGE_ON;
    public static final CyclingOption<Boolean> SLIME_ENABLED;

    static {
        LAVASPONGE_ON =  CyclingOption.create("LavaSpongeIsOn",
                config -> Configs.getlavaSpongeIsOn(),
                (gameOptions,config,lavaSpongeIsOn) -> Configs.setlavaSpongeIsOn(lavaSpongeIsOn)
        );
        SLIME_ENABLED =  CyclingOption.create("SlimeBlockBreakingEnabled",
                config -> Configs.getSlimeBlockPlacingEnabled(),
                (gameOptions,config,SlimeBlockPlacingEnabled) -> Configs.setSlimeBlockPlacingEnabled(SlimeBlockPlacingEnabled)
        );
    }

    public Config(String key) {
        super(key);
    }

    @Override
    public ButtonWidget createButton(GameOptions options, int x, int y, int width) {
        return null;
    }
}