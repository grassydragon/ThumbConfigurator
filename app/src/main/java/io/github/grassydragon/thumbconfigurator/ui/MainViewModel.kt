package io.github.grassydragon.thumbconfigurator.ui

import android.app.Application
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.grassydragon.thumbconfigurator.data.Editor
import io.github.grassydragon.thumbconfigurator.data.Parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val contentResolver = getApplication<Application>().contentResolver

    private var uri: Uri? = null

    private val _message = mutableStateOf("")
    val message: State<String>
        get() = _message

    private val _values = mutableStateOf(emptyMap<Parameter, Int>())
    val values: State<Map<Parameter, Int>>
        get() = _values

    fun onFileSelected(uri: Uri) {
        this.uri = uri

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _values.value = Editor.loadValues(contentResolver, uri)
            } catch (e: Exception) {
                val message = "Unable to load the values"
                Log.e(TAG, message, e)
                _message.value = message
            }
        }
    }

    fun onFileSaved() {
        val uri = uri ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Editor.saveValues(contentResolver, uri, _values.value)
                _message.value = "Saved the values successfully"
            } catch (e: Exception) {
                val message = "Unable to save the values"
                Log.e(TAG, message, e)
                _message.value = message
            }
        }
    }

    fun onValueChanged(parameter: Parameter, value: Int) {
        val values = _values.value.toMutableMap()
        values[parameter] = value
        _values.value = values
    }

    fun onMessageShown() {
        _message.value = ""
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}
