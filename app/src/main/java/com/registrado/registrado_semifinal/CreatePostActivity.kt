package com.registrado.registrado_semifinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.registrado.registrado_semifinal.databinding.ActivityCreatePostBinding
import com.registrado.registrado_semifinal.models.Post
import com.registrado.registrado_semifinal.networks.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.save.setOnClickListener {
            if(!binding.description.text.isNullOrBlank()) {
                createPost()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createPost() {
        val post = Post(
            id = "",
            name = binding.name.text.toString(),
            description = binding.description.text.toString()
        )
        RetrofitClient.apiService.createPost(post).enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    finish()
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                showError()
            }
        })
    }

    private fun showError() {
        Toast.makeText(this, "Failed to save data.", Toast.LENGTH_SHORT).show()
    }
}