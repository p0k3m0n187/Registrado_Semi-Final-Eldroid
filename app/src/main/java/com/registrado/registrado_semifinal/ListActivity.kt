package com.registrado.registrado_semifinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.registrado.registrado_semifinal.adapters.PostAdapter
import com.registrado.registrado_semifinal.databinding.ActivityListBinding
import com.registrado.registrado_semifinal.models.Post
import com.registrado.registrado_semifinal.networks.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity:: class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getPost()
    }

    private fun getPost() {
        val activity = this
        RetrofitClient.apiService.getPostList().enqueue(object: Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val data: List<Post>? = response.body()
                    if(data != null) {
                        binding.postList.layoutManager = LinearLayoutManager(activity)
                        binding.postList.adapter = PostAdapter(activity, data)
                        binding.progress.visibility = View.GONE
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                showError()
            }
        })
    }

    private fun showError() {
        Toast.makeText(this, "Failed to load data.", Toast.LENGTH_SHORT).show()
    }
}