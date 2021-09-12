package aria1th.main.lavasponge.utils;

import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.item.Items;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.world.World;
import java.util.HashSet;
import java.util.stream.Collectors;

public class LavaSpongeMain {
    private static MinecraftClient mc;
    private static ClientWorld world;
    private static final int reachDistance = 6;
    private static final int maxInteraction = 12;
    private static final ItemConvertible useItem = new Blocks().SLIME_BLOCK.asItem();
    private static BlockPos playerBlockPos;
    private static boolean enabled = false;
    private static boolean enabledSlime = false;
    private static HashSet<Long> storageLocal = new HashSet<Long>();
    public static void tick() {
        playerBlockPos = mc.player.getBlockPos();
        HashSet<Long> hashSet = getNearbyFluidPos();
        if (!hashSet.isEmpty()) {
            if (playerInventorySwitch(useItem)) {
            hashSet.stream().forEach(a -> placeBlock(BlockPos.fromLong(a)));}
        }
        if (enabledSlime) {getNearbySlimePos().stream().forEach(a -> breakBlock(BlockPos.fromLong(a)));}
    }
    public static HashSet<Long> getNearbyFluidPos () {
        return BlockPos.streamOutwards(playerBlockPos, reachDistance, reachDistance, reachDistance).
                filter(position -> playerBlockPos.getSquaredDistance(position)< reachDistance * reachDistance).
                filter(position -> isLavaSourceBlock(position)).limit(maxInteraction).
                map(position -> position.asLong()).
                collect(Collectors.toCollection(HashSet::new));
    }
    public static HashSet<Long> getNearbySlimePos () {
        return BlockPos.streamOutwards(playerBlockPos, reachDistance, reachDistance, reachDistance).
                filter(position -> playerBlockPos.getSquaredDistance(position)< reachDistance * reachDistance).
                filter(position -> isSlimeBlock(position)).limit(maxInteraction).
                filter(position -> storageLocal.contains(position.asLong())).
                map(position -> position.asLong()).
                collect(Collectors.toCollection(HashSet::new));
    }
    private static boolean isLavaSourceBlock (BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isAir()) {
            return false;
        }
        else if (blockState.contains(FluidBlock.LEVEL) && blockState.get(FluidBlock.LEVEL) == 0) {
            return true;
        }
        return false;
    }
    private static boolean isSlimeBlock (BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isAir()) {
            return false;
        }
        else if (blockState.isOf(Blocks.SLIME_BLOCK)) {
            return true;
        }
        return false;
    }
    private static void placeBlock(BlockPos pos){
        storageLocal.add(pos.asLong());
        Vec3d hitVec = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        Hand hand = Hand.MAIN_HAND;
        BlockHitResult hitResult = new BlockHitResult(hitVec, Direction.DOWN, pos, false);
        mc.interactionManager.interactBlock(mc.player, world, hand, hitResult);
    }
    private static void breakBlock(BlockPos pos){
        mc.interactionManager.attackBlock(pos, Direction.DOWN);
    }
    public static void switchOnOff(){
        enabled = !enabled;
        mc = MinecraftClient.getInstance();
        world = mc.world;
    }
    public static void switchOnOffSlime(){
        enabledSlime = !enabledSlime;
    }
    public static boolean isEnabled() {
        return enabled;
    }
    public static boolean playerInventorySwitch(ItemConvertible itemName){
        PlayerInventory playerInventory = mc.player.getInventory();
        int i = playerInventory.getSlotWithStack(new ItemStack(itemName));
        if (i != -1) {
            if (PlayerInventory.isValidHotbarIndex(i)) {
                playerInventory.selectedSlot = i;
            } else {
                mc.interactionManager.pickFromInventory(i);
            }
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(playerInventory.selectedSlot));
            return true;
        }
        return false;
    }
}
