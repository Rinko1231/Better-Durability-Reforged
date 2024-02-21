package darkorg.betterdurability.event

import darkorg.betterdurability.polyfill.minecraft.item.ItemStack
import net.minecraftforge.eventbus.api.Cancelable
import net.minecraftforge.eventbus.api.Event

/**
 * These events are triggered in various points where durability is involved.
 * You can use them to implement your own logic.
 */
open class ItemDurabilityEvent(
    /**
     * This field holds the "victim" of this event. It is guaranteed to be damageable.
     *
     *
     * Note: Java don't have "const" like C++, "final" just means that you cannot assign other objects to this field,
     * but the internal data of the object is always mutable. For example, you can change durability of this itemStack
     * directly by calling [ItemStack.setDamageValue] and this WILL affect following process. You are free to
     * do so as long as you don't shoot your own feet. ;)
     */
    val targetStack: ItemStack
) : Event() {
    /**
     * This event is triggered when item is going to be broken by a deadly hit.
     * Look at [reserveDurability] on how to use.
     */
    class ItemBreaking(
        targetStack: ItemStack,
        /**
         * This field holds the damage that is originally to be dealt.
         */
        val damageValue: Int,
        /**
         * This field holds the original reserved durability decided by core logic. If it is set to a value greater than
         * 0, then tool will reserve durability equal to this value no matter how much damage it takes and will not
         * break. Otherwise, the hurt process will stay same as if we never injected.
         */
        var reserveDurability: Int
    ) : ItemDurabilityEvent(targetStack)

    /**
     * This event is triggered when a damageable item is going to function and consume durability.
     *
     *
     * Cancelling this means item will not function this time.
     *
     *
     * Note: most enchantments will not trigger this event, except for Soul Speed and Thorns, for they cost durability.
     *
     *
     * Another note: this event is not guaranteed to be triggered before or after [ItemBreaking].
     */
    @Cancelable
    class ItemUsage(
        targetStack: ItemStack,
        /**
         * The source of this event, look at [ItemUsageType] for detail.
         */
        val type: ItemUsageType
    ) : ItemDurabilityEvent(targetStack)
}
