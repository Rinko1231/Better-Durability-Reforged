package darkorg.betterdurability.event

import darkorg.betterdurability.polyfill.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge


object ForgeEventTrigger {
    @JvmStatic fun onItemBreaking(targetStack: ItemStack, damageValue: Int, reserveDurability: Int): Int {
        val event = ItemDurabilityEvent.ItemBreaking(targetStack, damageValue, reserveDurability)
        MinecraftForge.EVENT_BUS.post(event)
        return event.reserveDurability
    }

    fun onItemUsage(targetStack: ItemStack, type: ItemUsageType, isUsable: Boolean): Boolean {
        val event = ItemDurabilityEvent.ItemUsage(targetStack, type)
        event.isCanceled = !isUsable
        return !MinecraftForge.EVENT_BUS.post(event)
    }
}
