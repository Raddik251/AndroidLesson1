package ru.neotology.nmedia.data.imp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.neotology.nmedia.R
import ru.neotology.nmedia.databinding.PostBinding
import ru.neotology.nmedia.dto.Post
import kotlin.properties.Delegates

internal class PostAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallBack) {

/*    var posts: List<Post> by Delegates.observable(emptyList()) { _, _, _, ->
         notifyDataSetChanged()
    }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

/*    override fun getItemCount() = posts.size*/

    inner class ViewHolder(
        private val binding: PostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            title.text = post.title
            content.text = post.content
            date.text = post.date
            countLikes.text = viewCounts(post.countLikes)
            countShares.text = viewCounts(post.countShares)
            likeIcon?.setImageResource(getLikeIconResId(post.likedByMe))
            likeIcon?.setOnClickListener { onLikeClicked(post) }
            shareIcon?.setOnClickListener { onShareClicked(post) }
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