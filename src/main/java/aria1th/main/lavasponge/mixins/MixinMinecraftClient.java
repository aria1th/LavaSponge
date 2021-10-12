package aria1th.main.lavasponge.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow
    @Nullable
    public ClientWorld world;
    @Shadow
    @Nullable
    public ClientPlayerEntity player;
    @Shadow
    @Nullable
    public HitResult crosshairTarget;
    @Inject(method = "doItemUse", at = @At(value = "HEAD"))
    private void switchOnOff(CallbackInfo ci){
        return;
        //if (this.crosshairTarget.getType() == HitResult.Type.MISS &&  player.getMainHandStack().isOf(Items.SPONGE) ) {
            //LavaSpongeMain.switchOnOff();
        //}
        //if (this.crosshairTarget.getType() == HitResult.Type.MISS &&  player.getMainHandStack().isOf(Items.SLIME_BLOCK)) {
            //LavaSpongeMain.switchOnOffSlime();
        //}
    }
}