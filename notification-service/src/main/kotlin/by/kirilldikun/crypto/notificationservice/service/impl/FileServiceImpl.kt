package by.kirilldikun.crypto.notificationservice.service.impl

import by.kirilldikun.crypto.notificationservice.feign.FileFeignClient
import by.kirilldikun.crypto.notificationservice.service.FileService
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class FileServiceImpl(
    val fileFeignClient: FileFeignClient
) : FileService {

    override fun downloadFile(id: String): Resource {
        return fileFeignClient.downloadFile(id)
    }
}