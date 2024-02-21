package darkorg.betterdurability.core

import darkorg.betterdurability.event.ForgeEventTrigger
import darkorg.betterdurability.event.ItemUsageType
import darkorg.betterdurability.polyfill.minecraft.item.Item
import darkorg.betterdurability.polyfill.minecraft.item.ItemStack
import darkorg.betterdurability.util.VanillaDamageableType
import darkorg.betterdurability.util.VanillaDamageableType.Companion.getTypeByItem


object DurabilityEventsHandler {
    private fun isBlacklisted(targetItem: Item?, itemType: VanillaDamageableType): Boolean {
        return (Blacklist.DISABLED_CATEGORIES.contains(itemType.category)
                || Blacklist.DISABLED_TYPES.contains(itemType)
                || Blacklist.BLACKLISTED_ITEMS.contains(targetItem))
    }

    @JvmStatic fun onItemBreaking(targetStack: ItemStack, damageValue: Int): Int {
        val targetItem = targetStack.item
        val itemType = getTypeByItem(targetItem)
        val result = if (itemType != null && !isBlacklisted(targetItem, itemType)) { itemType.protectValue } else { 0 }
        return ForgeEventTrigger.onItemBreaking(targetStack, damageValue, result)
    }

    @JvmStatic fun onItemUsage(targetStack: ItemStack, type: ItemUsageType): Boolean {
        val targetItem = targetStack.item
        val itemType = getTypeByItem(targetItem)
        val result = !(itemType != null && itemType.isItemBroken(targetStack) && !isBlacklisted(targetItem, itemType))
        return ForgeEventTrigger.onItemUsage(targetStack, type, result)
    }
}
