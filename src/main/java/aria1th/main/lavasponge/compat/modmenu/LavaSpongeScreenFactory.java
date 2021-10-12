package aria1th.main.lavasponge.compat.modmenu;

import aria1th.main.lavasponge.gui.LavaSpongeModScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.gui.screen.Screen;

public class LavaSpongeScreenFactory implements ConfigScreenFactory {
    @Override
    public Screen create(Screen parent) {
        return new LavaSpongeModScreen(parent);
    }
}
