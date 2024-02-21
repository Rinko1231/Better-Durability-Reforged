package darkorg.betterdurability.event

import darkorg.betterdurability.BetterDurability
import darkorg.betterdurability.polyfill.minecraft.text.TextFormatting
import darkorg.betterdurability.polyfill.minecraft.text.makeTranslatableComponent
import darkorg.betterdurability.util.VanillaDamageableType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(modid = BetterDurability.MOD_ID, value = [Dist.CLIENT])
object ForgeClientEventsHandler {
    @SubscribeEvent
    fun onItemTooltip(event: ItemTooltipEvent) {
        val tooltip = event.toolTip
        if (VanillaDamageableType.isItemKnownBroken(event.itemStack)) {
            tooltip.add(0, makeTranslatableComponent("tooltip.betterdurability.broken").withStyle(TextFormatting.RED))
        }
    }
}
