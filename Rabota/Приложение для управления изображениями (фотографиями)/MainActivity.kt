import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ImageAdapter
    private lateinit var repository: ImageRepository
    private val images = mutableListOf<ImageItem>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) loadImages()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = ImageRepository(this)
        setupRecyclerView()
        setupButtons()
        checkPermissions()
    }

    private fun setupRecyclerView() {
        adapter = ImageAdapter(
            onItemClick = { openFullscreenImage(it.uri) },
            onItemLongClick = { showDeleteDialog(it); true }
        )
        
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = this@MainActivity.adapter
            addItemDecoration(GridSpacingItemDecoration(3, 8, true))
        }
    }

    private fun setupButtons() {
        binding.fabCamera.setOnClickListener { openCamera() }
        binding.fabGallery.setOnClickListener { openGallery() }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            loadImages()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun loadImages() {
        lifecycleScope.launch {
            val loadedImages = repository.loadImages()
            images.clear()
            images.addAll(loadedImages)
            adapter.submitList(images.toList())
        }
    }

    private fun openFullscreenImage(uri: Uri) {
        val intent = Intent(this, FullscreenActivity::class.java).apply {
            putExtra("image_uri", uri.toString())
        }
        startActivity(intent)
    }

    private fun showDeleteDialog(item: ImageItem) {
        AlertDialog.Builder(this)
            .setTitle("Delete Image")
            .setMessage("Are you sure you want to delete this image?")
            .setPositiveButton("Delete") { _, _ -> deleteImage(item) }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteImage(item: ImageItem) {
        lifecycleScope.launch {
            if (repository.deleteImage(item.uri)) {
                images.remove(item)
                adapter.submitList(images.toList())
                Toast.makeText(
                    this@MainActivity,
                    "Image deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun openCamera() {
        // Реализация камеры будет ниже
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        galleryLauncher.launch(intent)
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    // Обработка выбранных изображений
                }
            } ?: run {
                result.data?.data?.let { uri ->
                    // Обработка одного выбранного изображения
                }
            }
            // Перезагрузка изображений после добавления
            loadImages()
        }
    }
}
