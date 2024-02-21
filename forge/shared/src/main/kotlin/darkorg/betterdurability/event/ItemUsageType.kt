package darkorg.betterdurability.event

/**
 * Various types of effects item can make. Note that these types are decided by implementation, every injection
 * point maps to a type here.
 */
enum class ItemUsageType {
    TOOL_LEFT_CLICK_BLOCK,
    TOOL_LEFT_CLICK_ENTITY,
    TOOL_RIGHT_CLICK_ITEM,
    TOOL_RIGHT_CLICK_BLOCK,
    TOOL_RIGHT_CLICK_ENTITY,
    ARMOR_ENEMY_ATTACK,
    ARMOR_THORNS,
    HELMET_HEAD_STRUCK,
    BOOTS_SOULSPEED,
    SHIELD_DEFEND
}
