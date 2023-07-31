package com.example.myapplication2

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val animalRepository: AnimalRepository = AnimalRepository(application)

    private var currentPage: Int = 1

    val allAnimals: LiveData<List<Animal>> = animalRepository.getAllAnimals()

    fun loadNextPage() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://shibe.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ShibeApiService::class.java)
        val call = service.getShibeData(currentPage)

        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val shibeData = response.body()
                    if (shibeData != null) {
                        currentPage++

                        val animals = shibeData.map { Animal(imageFilePath = it) }
                        viewModelScope.launch(Dispatchers.IO) {
                            downloadBitmapsAndSave(animals)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                val errorMessage = "Error: ${t.message}"
                Toast.makeText(getApplication(), errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private suspend fun downloadBitmapsAndSave(animals: List<Animal>) {
        withContext(Dispatchers.IO) {
            for (animal in animals) {
                val bitmap = downloadBitmapFromUrl(animal.imageFilePath)
                if (bitmap != null) {
                    val fileName = "image_${System.currentTimeMillis()}.jpg"
                    val filePath = savePhotoToInternalStorage(bitmap, fileName, getApplication())
                    if (filePath != null) {
                        animal.imageFilePath = filePath
                    }
                }
            }
            animalRepository.insertAnimals(animals)
        }
    }

    private fun downloadBitmapFromUrl(imageUrl: String): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun savePhotoToInternalStorage(bitmap: Bitmap, fileName: String, context: Context): String? {
        val dir = File(context.filesDir, "images")
        if (!dir.exists()) {
            dir.mkdir()
        }

        val file = File(dir, fileName)
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()

            Log.d("SavePhoto", "Saved image: ${file.absolutePath}")
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            outputStream?.close()
        }
        return file.absolutePath
    }
}
