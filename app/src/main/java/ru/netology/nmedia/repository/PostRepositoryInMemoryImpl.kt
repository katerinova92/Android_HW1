package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет! Это новая Нетология! Привет! Это новая Нетология!",
        published = "21 мая в 18:36",
        likes = 1099,
        share = 999999,
        likedByMe = false,
        sharedByMe = false
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        if(post.likedByMe == true) post.copy(likes = post.likes++) else post.copy(likes = post.likes--)
        data.value = post
    }

    override fun share() {
        post = post.copy(sharedByMe = !post.sharedByMe)
        if(post.sharedByMe == true) post.copy(share = post.share++) else post.copy(share = post.share--)
        data.value = post
    }
}