package by.kirilldikun.crypto.notificationservice.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "fileFeignClient", url = "\${service.file.url}")
interface FileFeignClient {

    @GetMapping ("/files/{id}/download")
    fun downloadFile(@PathVariable id: String): Resource
}