package by.kirilldikun.crypto.authservice.service

import by.kirilldikun.crypto.authservice.dto.ProfileDto

interface ProfileService {

    fun update(userId: Long, profileDto: ProfileDto): ProfileDto
}