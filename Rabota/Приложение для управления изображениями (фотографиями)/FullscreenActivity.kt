import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

class FullscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val imageUri = Uri.parse(intent.getStringExtra("image_uri"))
        val photoView: PhotoView = findViewById(R.id.photo_view)

        Glide.with(this)
            .load(imageUri)
            .fitCenter()
            .into(photoView)

        // Закрытие при касании
        photoView.setOnPhotoTapListener { _, _, _ -> finish() }
    }
}
