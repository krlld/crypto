package by.kirilldikun.crypto.fileservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "file_service_file_metadata")
class FileMetadata(

    @Id
    var id: UUID,

    val originalFilename: String,
)