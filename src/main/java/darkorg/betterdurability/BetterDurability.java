package darkorg.betterdurability;

import darkorg.betterdurability.setup.ConfigurationHandler;
import darkorg.betterdurability.util.NaiveLoggerWrapper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;

@Mod(BetterDurability.MOD_ID)
public class BetterDurability {
    public static final String MOD_ID = "betterdurability";
    public static final NaiveLoggerWrapper LOGGER = new NaiveLoggerWrapper(LogManager.getLogger())
            .withPrefix("[Better Durability] ");

    public BetterDurability() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigurationHandler.SERVER_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerChat(ServerChatEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        if (!heldItem.isEmpty()) {
            if (heldItem.isDamageableItem()) {
                // 将物品的耐久值设为2（注意这是剩余耐久值，不是损耗的耐久）
                heldItem.setDamageValue(heldItem.getMaxDamage() - 2);
                player.displayClientMessage(Component.literal("Damaged"), true);
            }
        }
    }

}