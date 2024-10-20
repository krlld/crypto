package by.kirilldikun.crypto.commons.service.impl

import by.kirilldikun.crypto.commons.dto.UserDto
import by.kirilldikun.crypto.commons.feign.UserFeignClient
import by.kirilldikun.crypto.commons.service.UserService
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Service

@Service
@ConditionalOnBean(UserFeignClient::class)
@ConditionalOnMissingBean(UserService::class)
class UserServiceImpl(
    val userFeignClient: UserFeignClient
) : UserService {

    override fun findAllByIds(userIds: List<Long>): List<UserDto> {
        return userFeignClient.findAllByIds(userIds)
    }
}