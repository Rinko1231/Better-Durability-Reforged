package darkorg.betterdurability.polyfill.minecraft.text

typealias TextComponent = net.minecraft.network.chat.Component
typealias TranslatableTextComponent = net.minecraft.network.chat.MutableComponent
typealias TextFormatting = net.minecraft.ChatFormatting

fun makeTranslatableComponent(key: String): TranslatableTextComponent {
    return TextComponent.translatable(key)
}
fun makeTranslatableComponent(key: String, vararg args: Any): TranslatableTextComponent {
    return TextComponent.translatable(key, args)
}
