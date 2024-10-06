package by.kirilldikun.crypto.commons.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.crypto.SecretKey
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtParser(
    val jwtProperties: JwtProperties
) {

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: (claims: Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignedKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun generateToken(customUserDetails: CustomUserDetails): String {
        return generateToken(HashMap(), customUserDetails)
    }

    fun generateToken(extraClaims: Map<String, Any>, customUserDetails: CustomUserDetails): String {
        return Jwts
            .builder()
            .claims()
            .add(extraClaims)
            .subject(customUserDetails.username)
            .add("userId", customUserDetails.getId())
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + jwtProperties.expiration.toMillis()))
            .and()
            .signWith(getSignedKey(), Jwts.SIG.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun getSignedKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}