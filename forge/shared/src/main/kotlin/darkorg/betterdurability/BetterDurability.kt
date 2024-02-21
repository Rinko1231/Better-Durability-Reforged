package darkorg.betterdurability

import darkorg.betterdurability.setup.ConfigurationHandler
import darkorg.betterdurability.util.NaiveLoggerWrapper
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.apache.logging.log4j.LogManager


@Mod(BetterDurability.MOD_ID)
object BetterDurability {
    const val MOD_ID: String = "betterdurability"
    @JvmField val LOGGER: NaiveLoggerWrapper = NaiveLoggerWrapper(LogManager.getLogger())
        .withPrefix("[Better Durability] ")
    init {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigurationHandler.SERVER_CONFIG)
        MinecraftForge.EVENT_BUS.register(this)
    }
}
