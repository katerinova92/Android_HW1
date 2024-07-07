package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode
import java.text.DecimalFormat

typealias OnLikeListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            postText.text = post.content
            likesNumber.text = countChange(post.likes)
            sharedNumber.text = countChange(post.share)

            icLiked.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )

            icLiked.setOnClickListener{
                onLikeListener(post)
            }
        }
    }
}

fun countChange(count: Int): String {
    var countToPost = count.toString()
    val decimalFormat = DecimalFormat("#.#")
    decimalFormat.roundingMode = RoundingMode.FLOOR

    when {
        count > 1099999 && (count % 1000000 != 0) -> countToPost =
            decimalFormat.format(count.toDouble() / 1000000) + "M"

        count > 999999 -> countToPost = (count / 1000000).toString() + "M"
        count > 9999 && (count % 1000 != 0) -> countToPost = (count / 1000).toString() + "K"
        count > 1099 && (count % 1000 != 0) -> countToPost =
            decimalFormat.format(count.toDouble() / 1000) + "K"

        count > 999 -> countToPost = (count / 1000).toString() + "K"
    }
    return countToPost
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}