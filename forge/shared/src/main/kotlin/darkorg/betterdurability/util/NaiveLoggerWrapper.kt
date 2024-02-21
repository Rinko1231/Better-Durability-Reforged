package darkorg.betterdurability.util

import org.apache.logging.log4j.Logger

class NaiveLoggerWrapper(private val internal: Logger) {
    var prefix: String = ""

    fun debug(message: String, vararg params: Any?) {
        internal.debug(prefix + message, *params)
    }

    fun error(message: String, vararg params: Any?) {
        internal.error(prefix + message, *params)
    }

    fun fatal(message: String, vararg params: Any?) {
        internal.fatal(prefix + message, *params)
    }

    fun info(message: String, vararg params: Any?) {
        internal.info(prefix + message, *params)
    }

    fun trace(message: String, vararg params: Any?) {
        internal.trace(prefix + message, *params)
    }

    fun warn(message: String, vararg params: Any?) {
        internal.warn(prefix + message, *params)
    }

    fun withPrefix(prefix: String): NaiveLoggerWrapper {
        this.prefix = prefix
        return this
    }
}
