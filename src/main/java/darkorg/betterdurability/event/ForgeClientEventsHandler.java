package darkorg.betterdurability.event;

import darkorg.betterdurability.BetterDurability;
import darkorg.betterdurability.util.VanillaDamageableType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEventsHandler {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        if (VanillaDamageableType.isItemKnownBroken(event.getItemStack()) || VanillaDamageableType.isItemKnownBrokenAnother(event.getItemStack()) ) {
            tooltip.add(0, Component.translatable("tooltip.betterdurability.broken").withStyle(ChatFormatting.RED));
        }
    }
}
