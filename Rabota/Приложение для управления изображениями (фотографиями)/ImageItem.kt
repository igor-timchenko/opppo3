import android.net.Uri
import java.util.Date

data class ImageItem(
    val id: Long,
    val uri: Uri,
    val name: String,
    val dateTaken: Date,
    val size: Long,
    val album: String = "All Photos"
)
