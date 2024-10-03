package by.kirilldikun.crypto.commons.exception.handler

import by.kirilldikun.crypto.commons.exception.ApiErrorResponse
import by.kirilldikun.crypto.commons.exception.BadRequestException
import by.kirilldikun.crypto.commons.exception.ConflictException
import by.kirilldikun.crypto.commons.exception.NotFoundException
import by.kirilldikun.crypto.commons.exception.UnauthorizedException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ApiErrorResponse {
        val errorResponse = ApiErrorResponse(HttpStatus.BAD_REQUEST, "The request was not validated")
        e.bindingResult.fieldErrors
            .forEach { fieldError ->
                errorResponse.addField(
                    fieldError.field,
                    fieldError.defaultMessage
                )
            }
        return errorResponse
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ApiErrorResponse {
        val errorResponse = ApiErrorResponse(HttpStatus.BAD_REQUEST, "The request was not validated")
        e.constraintViolations.forEach { constraintViolation ->
            errorResponse.addField(
                constraintViolation.propertyPath.toString(),
                constraintViolation.message
            )
        }
        return errorResponse
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): ApiErrorResponse {
        val message = e.message ?: "Bad request"
        return ApiErrorResponse(HttpStatus.BAD_REQUEST, message)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(e: UnauthorizedException): ApiErrorResponse {
        val message = e.message ?: "Unauthorized"
        return ApiErrorResponse(HttpStatus.UNAUTHORIZED, message)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ApiErrorResponse {
        val message = e.message ?: "Not found"
        return ApiErrorResponse(HttpStatus.NOT_FOUND, message)
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(e: ConflictException): ApiErrorResponse {
        val message = e.message ?: "Conflict"
        return ApiErrorResponse(HttpStatus.CONFLICT, message)
    }
}