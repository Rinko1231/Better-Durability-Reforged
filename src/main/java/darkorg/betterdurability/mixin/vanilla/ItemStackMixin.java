package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.event.ItemDurabilityEvent.ItemBreaking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract int getDamageValue();
    @Shadow public abstract void setDamageValue(int pDamage);
    @Shadow public abstract int getMaxDamage();

    @Inject(method = "hurt(ILnet/minecraft/util/RandomSource;Lnet/minecraft/server/level/ServerPlayer;)Z",
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDamageValue()I", ordinal = 1))
    private void injectItemStackHurt(int pAmount, RandomSource pRandom, ServerPlayer pUser, CallbackInfoReturnable<Boolean> cir) {
        int newDamageValue = this.getDamageValue() + pAmount;
        int maxDamageValue = this.getMaxDamage();
        if (newDamageValue >= maxDamageValue) {
            ItemBreaking event = new ItemBreaking((ItemStack)(Object)this, pAmount);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.reserveDurability > 0) {
                this.setDamageValue(maxDamageValue - event.reserveDurability);
                cir.setReturnValue(false);
            } else {
                this.setDamageValue(newDamageValue);
                cir.setReturnValue(true);
            }
        } else {
            this.setDamageValue(newDamageValue);
            cir.setReturnValue(false);
        }
        cir.cancel();
    }
}
