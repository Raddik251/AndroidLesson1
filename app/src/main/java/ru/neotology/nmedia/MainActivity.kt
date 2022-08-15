package ru.neotology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*
import ru.neotology.nmedia.databinding.ActivityMainBinding
import ru.neotology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnClickListener{
            println("binding.root clicked")
        }

/*        binding.avatar?.setOnClickListener{
            println("avatar clicked")
        }*/

        var likedByMe = false
        var likeCounts = 999
        var shareCounts = 0
        var imageResId = R.drawable.ic_baseline_favorite_border_24

        binding.likeIcon?.setOnClickListener{
            likedByMe = !likedByMe
            if (likedByMe) {
                imageResId = R.drawable.ic_baseline_favorite_24
                with(binding) {
                        countLikes.text = viewCounts(likeCounts + 1)
                }
            }
            else {
                imageResId = R.drawable.ic_baseline_favorite_border_24
                with(binding) {
                    countLikes.text = viewCounts(likeCounts)
                }
            }
            binding.likeIcon.setImageResource(imageResId)
        }

        binding.shareIcon?.setOnClickListener{
            shareCounts += 1
                with(binding) {
                    countShares.text = viewCounts(shareCounts)
                }
        }

    }

    private fun viewCounts (counts:Int) : String {
        var finalCount = "0"
        when (counts) {
            in 0..999 -> finalCount = counts.toString()
            in 1000..9999 -> finalCount = "${counts / 1000}.${(counts / 1000) / 100}K"
            in 10000..99999 -> finalCount = "${counts / 10000}K"
        }
        return finalCount
    }
}