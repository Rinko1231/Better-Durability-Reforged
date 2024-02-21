package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.core.DurabilityEventsHandler;
import darkorg.betterdurability.event.ItemUsageType;
import darkorg.betterdurability.util.VanillaDamageableType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    // Injected instance things
    @Shadow protected ItemStack useItem;
    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);

    // inject the Helmet Checking
    @ModifyVariable(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", argsOnly = true,
            at = @At(value = "CONSTANT", args = "floatValue=0.75"))
    private float modifyHelmetCheck$discardDefense(float value) {
        // increase damage if helmet should be broken
        ItemStack helmetStack = this.getItemBySlot(EquipmentSlot.HEAD);
        return DurabilityEventsHandler.onItemUsage(helmetStack, ItemUsageType.HELMET_HEAD_STRUCK) ? value : value * 1.33F;
    }

    // inject the Armor Checking
    // Fun fact: helmet can be hurt by ANVIL and FALLING_BLOCK damage, which does not bypass armor,
    //           so helmet will be double-damaged in such occasions
    @Inject(method = "getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D", cancellable = true,
            at = @At(value = "TAIL"))
    private void modifyArmorCheck$discardToughness(Attribute pAttribute, CallbackInfoReturnable<Double> cir) {
        if (pAttribute == Attributes.ARMOR_TOUGHNESS) {
            double result = cir.getReturnValue();
            float invalidToughness = 0;
            for (EquipmentSlot eqSlot: ARMOR_SLOTS) {
                ItemStack armorStack = this.getItemBySlot(eqSlot);
                if (armorStack.getItem() instanceof ArmorItem) {
                    ArmorItem armorItem = (ArmorItem) armorStack.getItem();
                    if (!DurabilityEventsHandler.onItemUsage(armorStack, ItemUsageType.ARMOR_ENEMY_ATTACK)) {
                        invalidToughness += armorItem.getToughness();
                    }
                }
            }
            result -= invalidToughness;
            cir.setReturnValue(result);
        }
    }
    @Inject(method = "getArmorValue()I", cancellable = true,
            at = @At(value = "TAIL"))
    private void modifyArmorCheck$discardDefense(CallbackInfoReturnable<Integer> cir) {
        int result = cir.getReturnValue();
        int invalidDefense = 0;
        for (EquipmentSlot eqSlot: ARMOR_SLOTS) {
            ItemStack armorStack = this.getItemBySlot(eqSlot);
            if (armorStack.getItem() instanceof ArmorItem) {
                ArmorItem armorItem = (ArmorItem) armorStack.getItem();
                if (!DurabilityEventsHandler.onItemUsage(armorStack, ItemUsageType.ARMOR_ENEMY_ATTACK)) {
                    invalidDefense += armorItem.getDefense();
                }
            }
        }
        result -= invalidDefense;
        cir.setReturnValue(result);
    }

    // inject the Shield Checking
    @Inject(method = "isBlocking()Z", cancellable = true,
            at = @At(value = "HEAD"))
    private void modifyShieldCheck$discardDefense(CallbackInfoReturnable<Boolean> cir) {
        if (VanillaDamageableType.SHIELD.isItemThisType(this.useItem.getItem())) {
            if (!DurabilityEventsHandler.onItemUsage(this.useItem, ItemUsageType.SHIELD_DEFEND)) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

    // inject the Soul Speed Checking
    @Inject(method = "tryAddSoulSpeed()V", cancellable = true,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;addTransientModifier(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V"))
    private void modifySoulSpeedCheck$discardEffect(CallbackInfo ci) {
        ItemStack bootStack = this.getItemBySlot(EquipmentSlot.FEET);
        if (!DurabilityEventsHandler.onItemUsage(bootStack, ItemUsageType.BOOTS_SOULSPEED)) {
            ci.cancel();
        }
    }
}

