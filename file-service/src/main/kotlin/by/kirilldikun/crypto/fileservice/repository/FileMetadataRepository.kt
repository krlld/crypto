package by.kirilldikun.crypto.fileservice.repository

import by.kirilldikun.crypto.fileservice.model.FileMetadata
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface FileMetadataRepository : JpaRepository<FileMetadata, UUID>