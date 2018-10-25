package com.example.g015c1153.jsonpotipoti

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    //private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://toridge.com/post_json.php"
        val handler = Handler()

        JsonButton.setOnClickListener {
            val request = Request.Builder()
                .addHeader("Content-Type", "text/plain; charset=utf-8")
                .url(url)
                .build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    val responseText: String? = response.body()?.string()
                    handler.post {
                        JsonText.text = responseText
                    }
                }
            })
        }

        JsonPush.setOnClickListener {

            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()!!
            val loginAdapter = moshi.adapter(LoginData::class.java)!!
            val id :String = jsonId.text.toString()
            val pw :String = jsonPw.text.toString()
            val loginData = LoginData(id,pw)
            val loginJson = loginAdapter.toJson(loginData)
            val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), loginJson)
            val request = Request.Builder()
                .addHeader("Content-Type", "text/plain; charset=utf-8")
                .url(url)
                .post(postBody)
                .build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    val responseText: String? = response.body()?.string()
                    handler.post {
                        JsonText.text = responseText
                    }
                }
            })
        }
    }
}
