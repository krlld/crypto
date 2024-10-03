package by.kirilldikun.crypto.commons.mapper

interface Mapper<E, D> {

    fun toDto(e: E): D

    fun toEntity(d: D): E
}