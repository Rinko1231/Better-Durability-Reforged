@file:Suppress("UnusedImport")

package darkorg.betterdurability.event

import darkorg.betterdurability.BetterDurability
import darkorg.betterdurability.core.DurabilityEventsHandler.onItemUsage
import darkorg.betterdurability.polyfill.forge.event.*
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed
import net.minecraftforge.event.entity.player.PlayerInteractEvent.*
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber


@EventBusSubscriber(modid = BetterDurability.MOD_ID)
object ForgeEventsHandler {
    @SubscribeEvent
    fun onLeftClickBlock(event: BreakSpeed) {
        if (event.state.block.speedFactor != 0.0f) {
            val targetStack = event.player.mainHandItem
            if (!targetStack.isDamageableItem) return
            val canFunction = onItemUsage(
                targetStack,
                ItemUsageType.TOOL_LEFT_CLICK_BLOCK
            )
            if (!canFunction) {
                event.isCanceled = true
            }
        }
    }

    @SubscribeEvent
    fun onLeftClickEntity(event: AttackEntityEvent) {
        val targetStack = event.player.mainHandItem
        if (!targetStack.isDamageableItem) return
        val canFunction = onItemUsage(
            targetStack,
            ItemUsageType.TOOL_LEFT_CLICK_ENTITY
        )
        if (!canFunction) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onRightClickItem(event: RightClickItem) {
        val targetStack = event.itemStack
        if (!targetStack.isDamageableItem) return
        val canFunction = onItemUsage(
            targetStack,
            ItemUsageType.TOOL_RIGHT_CLICK_ITEM
        )
        if (!canFunction) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onRightClickBlock(event: RightClickBlock) {
        val targetStack = event.itemStack
        if (!targetStack.isDamageableItem) return
        val canFunction = onItemUsage(
            targetStack,
            ItemUsageType.TOOL_RIGHT_CLICK_BLOCK
        )
        if (!canFunction) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onRightClickEntity(event: EntityInteract) {
        val targetStack = event.itemStack
        if (!targetStack.isDamageableItem) return
        val canFunction = onItemUsage(
            targetStack,
            ItemUsageType.TOOL_RIGHT_CLICK_ENTITY
        )
        if (!canFunction) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onRightClickEntitySpecific(event: EntityInteractSpecific) {
        val targetStack = event.itemStack
        if (!targetStack.isDamageableItem) return
        val canFunction = onItemUsage(
            targetStack,
            ItemUsageType.TOOL_RIGHT_CLICK_ENTITY
        )
        if (!canFunction) {
            event.isCanceled = true
        }
    }
}
