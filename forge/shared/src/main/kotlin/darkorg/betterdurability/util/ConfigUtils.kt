package darkorg.betterdurability.util

import darkorg.betterdurability.polyfill.minecraft.registry.Registry
import darkorg.betterdurability.polyfill.minecraft.resource.ResourceLocation
import darkorg.betterdurability.polyfill.minecraft.item.Item

inline fun <reified T: Enum<T>> enumNames(): List<String> {
    return enumValues<T>().map { it.name }
}

inline fun <reified T: Enum<T>> loadEnum(name: String): T? {
    return try {
        enumValueOf<T>(name)
    } catch (err: IllegalArgumentException) {
        null
    }
}

fun loadItem(itemId: String): Item? {
    return Registry.ITEM.getOptional(ResourceLocation.of(itemId, ':')).orElse(null)
}

fun <I, O> batchLoad(
    src: Collection<I>, dst: MutableCollection<O>,
    loader: (I) -> O?, onFail: (I) -> Unit
) {
    for (element in src) {
        val result = loader(element)
        if (result == null) {
            onFail(element)
        } else {
            dst.add(result)
        }
    }
}
