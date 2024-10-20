package by.kirilldikun.crypto.notificationservice.service

import org.springframework.core.io.Resource

interface FileService {

    fun downloadFile(id: String): Resource
}