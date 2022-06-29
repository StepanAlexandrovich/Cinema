package java.android.cinema.view.viewmovies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.android.cinema.model.Repository
import java.android.cinema.model.RepositoryLocalImpl
import java.android.cinema.model.RepositoryRemoteImpl
import java.android.cinema.viewmodel.AppState
import java.lang.IllegalStateException

class SelectedMoviesViewModel(private val liveData:MutableLiveData<AppState> = MutableLiveData<AppState>()):ViewModel() {

    private var isConnect = false // временно

    private lateinit var repository: Repository

    fun getLiveData():MutableLiveData<AppState>{
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() = if(isConnection()){
        repository = RepositoryRemoteImpl()
    }else{
        //repository = RepositoryLocalImpl()
        repository = RepositoryRemoteImpl()
    }

    fun sentRequest(){
        liveData.value = AppState.Loading

        if((0..3).random() == 1){
            liveData.postValue( AppState.Error( IllegalStateException("Что то пошло не по плану")))
        }else{
            liveData.postValue( AppState.Success(repository.getMovie()) )
        }

    }

    private fun isConnection(): Boolean {
        isConnect = !isConnect
        return isConnect
    }

}