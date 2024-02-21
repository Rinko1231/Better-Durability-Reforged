package darkorg.betterdurability.polyfill.minecraft.text

typealias TextComponent = net.minecraft.util.text.TextComponent
typealias TranslatableTextComponent = net.minecraft.util.text.TranslationTextComponent
typealias TextFormatting = net.minecraft.util.text.TextFormatting

fun makeTranslatableComponent(key: String): TranslatableTextComponent {
    return TranslatableTextComponent(key)
}
fun makeTranslatableComponent(key: String, vararg args: Any): TranslatableTextComponent {
    return TranslatableTextComponent(key, args)
}
