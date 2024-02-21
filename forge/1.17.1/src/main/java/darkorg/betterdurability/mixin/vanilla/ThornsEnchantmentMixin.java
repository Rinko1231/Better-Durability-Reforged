package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.core.DurabilityEventsHandler;
import darkorg.betterdurability.event.ItemUsageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Random;

@Mixin(ThornsEnchantment.class)
public class ThornsEnchantmentMixin {
    // inject the Thorns checking
    @Redirect(method = "doPostHurt(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRandomItemWith(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/Map$Entry;"))
    private Map.Entry<EquipmentSlot, ItemStack> modifyThornsCheck$doNotPickBroken(Enchantment pTargetEnchantment, LivingEntity pEntity) {
        // if not cancelled, we can use it for Thorns checking
        return EnchantmentHelper.getRandomItemWith(Enchantments.THORNS, pEntity, stack -> DurabilityEventsHandler.onItemUsage(stack, ItemUsageType.ARMOR_THORNS));
    }
    @Inject(method = "doPostHurt(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;I)V",
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private void modifyThornsCheck$discardEffect(LivingEntity pUser, Entity pAttacker, int pLevel, CallbackInfo ci, Random randomsource, Map.Entry<EquipmentSlot, ItemStack> entry) {
        // entry is nullable with our injection, so add a check here
        if (entry == null) { ci.cancel(); }
    }
}
