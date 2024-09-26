package by.kirilldikun.crypto.fileservice.controller

import by.kirilldikun.crypto.fileservice.dto.FileMetadataDto
import by.kirilldikun.crypto.fileservice.service.FileService
import java.nio.charset.StandardCharsets
import java.util.UUID
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriUtils

@RestController
@RequestMapping("/files")
class FileController(
    val fileService: FileService,
) {

    @GetMapping("/{uuid}/download", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun downloadFile(@PathVariable uuid: UUID): ResponseEntity<Resource> {
        val (fileMetadataDto, file) = fileService.getById(uuid)
        val encodedFilename = UriUtils.encode(fileMetadataDto.originalFilename, StandardCharsets.UTF_8)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''$encodedFilename")
            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
            .body(file)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun uploadFile(@RequestParam file: MultipartFile): FileMetadataDto {
        return fileService.uploadFile(file)
    }
}