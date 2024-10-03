package by.kirilldikun.crypto.commons.exception

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiErrorResponse(
    var httpStatus: HttpStatus,
    var message: String,
    var value: Int = httpStatus.value(),
    var series: HttpStatus.Series = httpStatus.series(),
    var reasonPhrase: String = httpStatus.reasonPhrase,
    var fields: MutableList<Field>? = null
) {

    data class Field(
        var name: String,
        var message: String?
    )

    fun addField(name: String, message: String?) {
        if (fields == null) {
            fields = ArrayList()
        }
        fields!!.add(Field(name, message))
    }
}

