package darkorg.betterdurability.setup

import com.google.common.collect.ImmutableList
import darkorg.betterdurability.BetterDurability
import darkorg.betterdurability.core.Blacklist
import darkorg.betterdurability.polyfill.forge.event.ModConfigLoading
import darkorg.betterdurability.polyfill.forge.event.ModConfigReloading
import darkorg.betterdurability.util.*
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod


@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object ConfigurationHandler {
    @JvmField public val SERVER_CONFIG: ForgeConfigSpec
    public const val CATEGORY_BLACKLISTS: String = "blacklists"
    @JvmField public var DISABLED_CATEGORY_NAMES: ConfigValue<List<String>>
    @JvmField public var DISABLED_TYPE_NAMES: ConfigValue<List<String>>
    @JvmField public var BLACKLISTED_ITEM_IDS: ConfigValue<List<String>>

    init {
        val serverBuilder = ForgeConfigSpec.Builder()

        serverBuilder.comment("Blacklist things, loved by server owners ;)").push(CATEGORY_BLACKLISTS)
        DISABLED_CATEGORY_NAMES = serverBuilder.comment("List of disabled damage protection categories. Available: ${enumNames<VanillaDamageableCategory>().joinToString()}.")
                .defineList("disabledCategoryNames", ImmutableList.of()) { true }
        DISABLED_TYPE_NAMES = serverBuilder.comment("List of disabled damage protection types. Available: ${enumNames<VanillaDamageableType>().joinToString()}.")
                .defineList("disabledTypeNames", ImmutableList.of()) { true }
        BLACKLISTED_ITEM_IDS = serverBuilder.comment("List of blacklisted items. Format is modId:itemId, modId can be omitted for vanilla.")
                .defineList("blacklistedItemIds", ImmutableList.of()) { true }
        serverBuilder.pop()

        SERVER_CONFIG = serverBuilder.build()
    }

    @SubscribeEvent
    fun onLoad(configEvent: ModConfigLoading) {
        BetterDurability.LOGGER.info("Loading config ...")
        Blacklist.loadCategoryNames(DISABLED_CATEGORY_NAMES.get())
        Blacklist.loadTypeNames(DISABLED_TYPE_NAMES.get())
        Blacklist.loadItemIds(BLACKLISTED_ITEM_IDS.get())
    }

    @SubscribeEvent
    fun onReload(configEvent: ModConfigReloading) {
        BetterDurability.LOGGER.info("Reloading config ...")
        Blacklist.reloadCategoryNames(DISABLED_CATEGORY_NAMES.get())
        Blacklist.reloadTypeNames(DISABLED_TYPE_NAMES.get())
        Blacklist.reloadItemIds(BLACKLISTED_ITEM_IDS.get())
    }
}
