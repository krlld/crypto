package by.kirilldikun.crypto.commons.util

import java.util.Locale
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
class MessageLocaleHelper(
    val messageSource: MessageSource
) {

    fun getLocalizedMessage(
        key: String,
        args: Map<String, String>? = null,
        localeStr: String? = null
    ): String {
        val locale = if (localeStr.isNullOrEmpty()) {
            LocaleContextHolder.getLocale()
        } else {
            Locale.forLanguageTag(localeStr)
        }
        var message = messageSource.getMessage(key, null, locale)
        args?.entries?.forEach { message = message.replace("{${it.key}}", it.value) }
        return message
    }
}