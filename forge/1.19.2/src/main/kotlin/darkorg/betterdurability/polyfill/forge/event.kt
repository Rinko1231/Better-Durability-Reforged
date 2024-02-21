package darkorg.betterdurability.polyfill.forge.event

import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.entity.player.PlayerEvent

typealias ModConfigLoading = net.minecraftforge.fml.event.config.ModConfigEvent.Loading
typealias ModConfigReloading = net.minecraftforge.fml.event.config.ModConfigEvent.Reloading

inline val <T: PlayerEvent> T.player: Player get() = entity
