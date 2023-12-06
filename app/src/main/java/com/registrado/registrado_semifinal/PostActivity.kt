package com.registrado.registrado_semifinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.registrado.registrado_semifinal.constants.Constants
import com.registrado.registrado_semifinal.databinding.ActivityPostBinding
import com.registrado.registrado_semifinal.models.Post
import com.registrado.registrado_semifinal.networks.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.deleteButton.setOnClickListener {
            handleDelete()
        }

    }

    override fun onResume() {
        super.onResume()
        getPost()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun getPost(){
        val id = intent.getStringExtra(Constants.PARAM_ID) ?: return
        RetrofitClient.apiService.getPostById(id).enqueue(object: Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val data: Post? = response.body()
                    if(data != null) {
                        binding.name.text = data.name
                        binding.description.text = data.description
                        binding.progress.visibility = View.GONE
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                showError()
            }
        })
    }

    private fun handleDelete() {
        val id = intent.getStringExtra(Constants.PARAM_ID) ?: return


        RetrofitClient.apiService.deletePost(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PostActivity, "Post deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@PostActivity, "Post deleted unsuccessfully", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showError()
            }
        })
    }

    private fun showError() {
        Toast.makeText(this, "Failed to load data.", Toast.LENGTH_SHORT).show()
    }
}