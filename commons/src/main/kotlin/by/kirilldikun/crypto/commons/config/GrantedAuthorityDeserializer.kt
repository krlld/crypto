package by.kirilldikun.crypto.commons.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import java.io.IOException
import com.fasterxml.jackson.databind.JsonDeserializer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class GrantedAuthorityDeserializer : JsonDeserializer<Set<GrantedAuthority>>() {

    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Set<GrantedAuthority> {
        val authorities = mutableSetOf<GrantedAuthority>()

        while (p.nextToken() !== JsonToken.END_ARRAY) {
            val role: String = p.text
            authorities.add(SimpleGrantedAuthority(role))
        }
        return authorities
    }
}