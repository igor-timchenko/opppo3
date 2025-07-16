import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ImageAdapter(
    private val onItemClick: (ImageItem) -> Unit,
    private val onItemLongClick: (ImageItem) -> Boolean
) : ListAdapter<ImageItem, ImageAdapter.ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(ItemImageBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageItem) {
            Glide.with(binding.root.context)
                .load(item.uri)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageView)

            binding.root.setOnClickListener { onItemClick(item) }
            binding.root.setOnLongClickListener { onItemLongClick(item) }
        }
    }
}

class ImageDiffCallback : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem == newItem
    }
}
