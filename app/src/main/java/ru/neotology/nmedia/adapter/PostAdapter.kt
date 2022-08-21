package ru.neotology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.neotology.nmedia.R
import ru.neotology.nmedia.data.imp.InMemoryPostRepository
import ru.neotology.nmedia.databinding.PostBinding
import ru.neotology.nmedia.dto.Post

internal class PostAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.option_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likeIcon.setOnClickListener { listener.onLikeClicked(post) }
            binding.shareIcon.setOnClickListener { listener.onShareClicked(post) }
        }

        fun bind(post: Post) {

            this.post = post

            with(binding) {
                title.text = post.title
                content.text = post.content
                date.text = post.date
                countLikes.text = viewCounts(post.countLikes)
                countShares.text = viewCounts(post.countShares)
                likeIcon.setImageResource(getLikeIconResId(post.likedByMe))
                options.setOnClickListener { popupMenu.show() }
            }
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24

        private fun viewCounts(counts: Int): String {
            var finalCount = "0"
            when (counts) {
                in 0..999 -> finalCount = counts.toString()
                in 1000..9999 -> finalCount = "${counts / 1000}.${(counts / 1000) / 100}K"
                in 10000..99999 -> finalCount = "${counts / 10000}K"
            }
            return finalCount
        }
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}