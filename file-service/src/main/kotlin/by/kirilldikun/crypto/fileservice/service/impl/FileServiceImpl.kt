package by.kirilldikun.crypto.fileservice.service.impl

import by.kirilldikun.crypto.fileservice.dto.FileMetadataDto
import by.kirilldikun.crypto.fileservice.model.FileMetadata
import by.kirilldikun.crypto.fileservice.repository.FileMetadataRepository
import by.kirilldikun.crypto.fileservice.service.FileService
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileServiceImpl(
    @Value("\${storage.path}")
    val pathToStorage: String,
    val fileMetadataRepository: FileMetadataRepository,
) : FileService {

    override fun getById(uuid: UUID): Pair<FileMetadataDto, Resource> {
        val fileMetadata = fileMetadataRepository.findById(uuid)
            .orElseThrow { Exception("File with id: $uuid not found") }  // TODO: change exception type
        val filename = "${fileMetadata.id}.${getFileExtension(fileMetadata.originalFilename)}"
        val file = getFile(filename)
        return Pair(toDto(fileMetadata), file)
    }

    fun getFile(filename: String): Resource {
        val absolutePath = Paths.get(pathToStorage, filename).toAbsolutePath().normalize().toString()
        val file = File(absolutePath)
        return UrlResource(file.toURI())
    }

    override fun uploadFile(multipartFile: MultipartFile): FileMetadataDto {
        val originalFilename = multipartFile.originalFilename.toString()
        val (generatedUUID, absolutePath) = generateFilename(originalFilename)

        val file = File(absolutePath)
        multipartFile.transferTo(file)

        val savedFileMetadata = fileMetadataRepository.save(
            FileMetadata(
                id = generatedUUID,
                originalFilename = originalFilename
            )
        )

        return toDto(savedFileMetadata)
    }

    fun generateFilename(originalFilename: String): Pair<UUID, String> {
        val extension = getFileExtension(originalFilename)

        for (i in 1..100) {
            val generatedUUID = UUID.randomUUID()
            val generatedFilename = "$generatedUUID.$extension"
            val absolutePath = Paths.get(pathToStorage, generatedFilename).toAbsolutePath().normalize()

            if (Files.exists(absolutePath)) {
                continue
            }

            if (Files.notExists(absolutePath.parent)) {
                Files.createDirectories(absolutePath.parent)
            }

            return Pair(generatedUUID, absolutePath.toString())
        }

        throw Exception("Can't generate new file name")
    }

    fun getFileExtension(filename: String): String {
        if (filename.isEmpty() || !filename.contains(".")) {
            return ""
        }
        return filename.substring(filename.lastIndexOf(".") + 1)
    }

    fun toDto(fileMetadata: FileMetadata): FileMetadataDto {
        return FileMetadataDto(
            id = fileMetadata.id,
            originalFilename = fileMetadata.originalFilename
        )
    }
}