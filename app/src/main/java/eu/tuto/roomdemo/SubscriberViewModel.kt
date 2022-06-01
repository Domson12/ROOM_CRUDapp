package eu.tuto.roomdemo

import androidx.lifecycle.*
import eu.tuto.roomdemo.db.Subscriber
import eu.tuto.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val inputName = MutableLiveData<String?>()

    val inputEmail = MutableLiveData<String?>()

    val saveOrUpdateButtonText = MutableLiveData<String>()

    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputName.value = null
        inputEmail.value = null
    }

    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        repository.insert(subscriber)
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch {
        repository.update(subscriber)
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun getSaveSubscribers() = liveData {
        repository.subscribers.collect {
            emit(it)
        }
    }
}