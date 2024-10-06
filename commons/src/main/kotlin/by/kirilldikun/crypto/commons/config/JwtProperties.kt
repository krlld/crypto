package by.kirilldikun.crypto.commons.config

import java.time.Duration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties("jwt")
data class JwtProperties(

    @DefaultValue("87ef05cec70898543719bc94da2d853251d9f3c46d5410f62580265fca8f4fbc")
    val secret: String,

    @DefaultValue("24H")
    val expiration: Duration
)
