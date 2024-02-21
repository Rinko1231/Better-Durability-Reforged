package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.core.DurabilityEventsHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract int getDamageValue();
    @Shadow public abstract void setDamageValue(int pDamage);
    @Shadow public abstract int getMaxDamage();

    @Inject(method = "hurt(ILjava/util/Random;Lnet/minecraft/server/level/ServerPlayer;)Z",
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDamageValue()I", ordinal = 1))
    private void injectItemStackHurt(int pAmount, Random pRandom, ServerPlayer pUser, CallbackInfoReturnable<Boolean> cir) {
        int newDamageValue = this.getDamageValue() + pAmount;
        int maxDamageValue = this.getMaxDamage();
        if (newDamageValue >= maxDamageValue) {
            int reserveDurability = DurabilityEventsHandler.onItemBreaking((ItemStack)(Object)this, pAmount);
            if (reserveDurability > 0) {
                this.setDamageValue(maxDamageValue - reserveDurability);
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
