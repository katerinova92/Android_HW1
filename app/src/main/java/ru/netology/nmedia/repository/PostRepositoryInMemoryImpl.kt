package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode
import java.text.DecimalFormat

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет! Это новая Нетология! Привет! Это новая Нетология!",
        published = "21 мая в 18:36",
        likes = 1199,
        share = 999999,
        likedByMe = false
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        data.value = post
    }
}