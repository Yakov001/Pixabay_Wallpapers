package com.example.pixabaywallpapers.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabaywallpapers.model.CategoryResponse
import com.example.pixabaywallpapers.model.PixabayApi
import com.example.pixabaywallpapers.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var categoryResponse = MutableStateFlow<Resource<CategoryResponse>>(Resource.Idle())
        private set

    fun requestCategory(category: String) {
        categoryResponse.value = Resource.Loading()
        viewModelScope.launch {
            val response = try {
                PixabayApi.getInstance().requestCategory(category)
            } catch (e: Exception) {
                Log.e("retrofit exception", e.message.toString())
                categoryResponse.value = Resource.Error(e.message.toString())
                return@launch
            }

            if (response.isSuccessful) categoryResponse.value = Resource.Success(response.body()!!)
            else {
                response.errorBody().toString().also {
                    Log.d("response", it)
                    categoryResponse.value = Resource.Error(message = it)
                }
            }
        }
    }
}