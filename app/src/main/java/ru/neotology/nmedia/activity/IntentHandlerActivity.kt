package ru.neotology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.neotology.nmedia.databinding.IntentHandlerActivityBinding

class IntentHandlerActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = IntentHandlerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        when (intent.action) {
            Intent.ACTION_SEND -> {
                val text = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (text.isNullOrBlank()) return

                Snackbar.make(binding.root, text, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) { finish() }
                    .show()
            }
            Intent.ACTION_VIEW -> {
                Snackbar.make(binding.root, "Show video in:", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) { finish() }
                    .show()
            }
        }

    }
}