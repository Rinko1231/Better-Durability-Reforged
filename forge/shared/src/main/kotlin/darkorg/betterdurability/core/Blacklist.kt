package darkorg.betterdurability.core

import darkorg.betterdurability.BetterDurability
import darkorg.betterdurability.polyfill.minecraft.item.Item
import darkorg.betterdurability.util.*


object Blacklist {
    @JvmField public val DISABLED_CATEGORIES = HashSet<VanillaDamageableCategory>()
    @JvmField public val DISABLED_TYPES = HashSet<VanillaDamageableType>()
    @JvmField public val BLACKLISTED_ITEMS = HashSet<Item>()

    fun loadCategoryNames(names: Collection<String>) {
        batchLoad(names, DISABLED_CATEGORIES, { loadEnum<VanillaDamageableCategory>(it) }) {
            BetterDurability.LOGGER.error("Trying to blacklist damageable category {} in but it does not exist ...", it)
        }
    }
    fun reloadCategoryNames(names: Collection<String>) {
        DISABLED_CATEGORIES.clear()
        loadCategoryNames(names)
    }

    fun loadTypeNames(names: Collection<String>) {
        batchLoad(names, DISABLED_TYPES, { loadEnum<VanillaDamageableType>(it) }) {
            BetterDurability.LOGGER.error("Trying to blacklist damageable type {} in but it does not exist ...", it)
        }
    }
    fun reloadTypeNames(names: Collection<String>) {
        DISABLED_TYPES.clear()
        loadTypeNames(names)
    }

    fun loadItemIds(ids: Collection<String>) {
        batchLoad(ids, BLACKLISTED_ITEMS, ::loadItem) {
            BetterDurability.LOGGER.error("Trying to blacklist item {} but it does not exist ...", it)
        }
    }
    fun reloadItemIds(ids: Collection<String>) {
        BLACKLISTED_ITEMS.clear()
        loadItemIds(ids)
    }
}
