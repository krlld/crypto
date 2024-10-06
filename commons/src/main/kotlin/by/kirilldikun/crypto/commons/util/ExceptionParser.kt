package by.kirilldikun.crypto.commons.util

import by.kirilldikun.crypto.commons.exception.ApiErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import feign.FeignException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import org.springframework.stereotype.Component

@Component
class ExceptionParser(
    val objectMapper: ObjectMapper
) {

    fun toApiError(e: FeignException): ApiErrorResponse {
        return toApiError(e.responseBody().get())
    }

    fun toApiError(message: String): ApiErrorResponse {
        return objectMapper.readValue(message, ApiErrorResponse::class.java)
    }

    fun toApiError(byteBuffer: ByteBuffer): ApiErrorResponse {
        val message = StandardCharsets.UTF_8.decode(byteBuffer).toString()
        return toApiError(message)
    }
}