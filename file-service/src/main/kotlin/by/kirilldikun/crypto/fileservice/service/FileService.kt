package by.kirilldikun.crypto.fileservice.service

import by.kirilldikun.crypto.fileservice.dto.FileMetadataDto
import java.util.UUID
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface FileService {

    fun getById(uuid: UUID): Pair<FileMetadataDto, Resource>

    fun uploadFile(multipartFile: MultipartFile): FileMetadataDto
}