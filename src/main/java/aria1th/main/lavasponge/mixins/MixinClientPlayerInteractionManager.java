package aria1th.main.lavasponge.mixins;

import aria1th.main.lavasponge.config.Configs;
import aria1th.main.lavasponge.utils.LavaSpongeMain;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Inject(at = @At("HEAD"), method = "tick")
    private void init(CallbackInfo ci) {
        if (Configs.getlavaSpongeIsOn()) {
            LavaSpongeMain.tick();
        }
    }
}