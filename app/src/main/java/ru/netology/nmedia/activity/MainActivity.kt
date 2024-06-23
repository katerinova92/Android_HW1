package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
//import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                postText.text = post.content
                likesNumber.text = countChange(post.likes)
                sharedNumber.text = countChange(post.share)

                icLiked.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )

                shared.setOnClickListener {
                    viewModel.share() }

                icLiked.setOnClickListener {
                    viewModel.like() }
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
}