package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет! Это новая Нетология! Привет! Это новая Нетология!",
            published = "21 мая в 18:36",
            likes = 1199,
            share = 999999,
            likedByMe = false
        )

        with(binding) {
            author.text = post.author
            published.text= post.published
            postText.text = post.content
            likesNumber.text = countChange(post.likes)
            sharedNumber.text = countChange(post.share)


            if(post.likedByMe) {
                icLiked?.setImageResource(R.drawable.ic_liked_24)
            }

            icLiked.setOnClickListener {
                if(post.likedByMe) post.likes-- else post.likes++
                post.likedByMe = !post.likedByMe
                icLiked.setImageResource(
                    if(post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                likesNumber.text = countChange(post.likes)
            }

            shared.setOnClickListener {
                post.share++
                sharedNumber.text = countChange(post.share)
            }
        }
    }

    fun countChange(count: Int): String {
        var countToPost = count.toString()
        val decimalFormat = DecimalFormat("#.#")
        decimalFormat.roundingMode = RoundingMode.FLOOR

        when {
            count > 1099999 && (count%1000000!=0) -> countToPost = decimalFormat.format(count.toDouble()/1000000) + "M"
            count > 999999 -> countToPost = (count/1000000).toString() + "M"
            count > 9999 && (count%1000!=0) -> countToPost = (count/1000).toString() + "K"
            count > 1099 && (count%1000!=0) -> countToPost = decimalFormat.format(count.toDouble()/1000) + "K"
            count > 999 -> countToPost = (count/1000).toString() + "K"
        }
        return countToPost
    }
    }