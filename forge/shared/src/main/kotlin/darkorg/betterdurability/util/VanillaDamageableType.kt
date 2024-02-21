package darkorg.betterdurability.util

import darkorg.betterdurability.polyfill.minecraft.inventory.EquipmentType
import darkorg.betterdurability.polyfill.minecraft.item.*


typealias VanillaDamageableCategory = VanillaDamageableType.Category

enum class VanillaDamageableType(category: Category, brokenThreshold: Int, protectValue: Int) {
    // brokenThreshold and protectValue here is somewhat arbitrary
    AXE(Category.TOOL, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is AxeItem
        }
    },
    PICKAXE(Category.TOOL, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is PickaxeItem
        }
    },
    SHOVEL(Category.TOOL, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is ShovelItem
        }
    },
    HOE(Category.TOOL, 1, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is HoeItem
        }
    },
    SHEARS(Category.TOOL, 1, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is ShearsItem
        }
    },
    SWORD(Category.TOOL, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is SwordItem
        }
    },
    FISHING_ROD(Category.TOOL, 5, 2) {
        override fun isItemThisType(item: Item): Boolean {
            return item is FishingRodItem
        }
    },
    FLINT_AND_STEEL(Category.TOOL, 1, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is FlintAndSteelItem
        }
    },
    BOW(Category.TOOL, 1, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is BowItem
        }
    },
    TRIDENT(Category.TOOL, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is TridentItem
        }
    },
    CROSSBOW(Category.TOOL, 9, 4) {
        override fun isItemThisType(item: Item): Boolean {
            return item is CrossbowItem
        }
    },
    HELMET(Category.ARMOR, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is ArmorItem && item.slot == EquipmentType.HEAD
        }
    },
    CHESTPLATE(Category.ARMOR, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is ArmorItem && item.slot == EquipmentType.CHEST
        }
    },
    LEGGINGS(Category.ARMOR, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is ArmorItem && item.slot == EquipmentType.LEGS
        }
    },
    BOOTS(Category.ARMOR, 2, 1) {
        override fun isItemThisType(item: Item): Boolean {
            return item is ArmorItem && item.slot == EquipmentType.FEET
        }
    },
    SHIELD(Category.SHIELD, 4, 2) {
        override fun isItemThisType(item: Item): Boolean {
            return item is ShieldItem
        }
    };

    enum class Category { TOOL, ARMOR, SHIELD }

    @JvmField val category: Category
    @JvmField val brokenThreshold: Int // damageable things will lose functionality if durability not more than this
    @JvmField val protectValue: Int // durability will never drop below this, should be not more than brokenThreshold

    init {
        assert(protectValue in 1..brokenThreshold)
        this.category = category
        this.brokenThreshold = brokenThreshold
        this.protectValue = protectValue
    }

    fun isItemBroken(stack: ItemStack): Boolean {
        val durabilityLeft = stack.maxDamage - stack.damageValue
        return durabilityLeft <= this.brokenThreshold
    }

    abstract fun isItemThisType(item: Item): Boolean

    companion object {
        @JvmStatic fun getTypeByItem(item: Item): VanillaDamageableType? {
            return values().firstOrNull { t -> t.isItemThisType(item) }
        }

        @JvmStatic fun isItemKnownBroken(stack: ItemStack): Boolean {
            if (stack.isDamageableItem) {
                val itemType = getTypeByItem(stack.item)
                return itemType != null && itemType.isItemBroken(stack)
            }
            return false
        }
    }
}
