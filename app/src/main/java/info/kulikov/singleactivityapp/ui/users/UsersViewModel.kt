package info.kulikov.singleactivityapp.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import info.kulikov.singleactivityapp.api.ApiManager
import info.kulikov.singleactivityapp.database.AppDao
import info.kulikov.singleactivityapp.database.AppRoomDatabase
import info.kulikov.singleactivityapp.database.DatabaseManager
import info.kulikov.singleactivityapp.domain.User
import io.reactivex.disposables.Disposable
import java.util.*


class UsersViewModel: ViewModel() {

    private val _userListLiveData = MutableLiveData<List<User>>().apply {
        value = listOf();
    }
    val userListLiveData: LiveData<List<User>> = _userListLiveData

    private val userList: ArrayList<User> = arrayListOf()

    lateinit var appDao: AppDao

    fun loadApiRepositories(): Disposable {
        return ApiManager.loadUsersRepos()
            .subscribe(
                { value ->
                    val list = arrayListOf(
                        *Gson().fromJson(
                            value.getAsJsonArray("data"),
                            Array<User>::class.java
                        )
                    )

                    userList.clear()
                    userList.addAll(list)

                    // postValue - потому что не в основном потоке.
                    _userListLiveData.postValue(userList)
                },
                { error -> println("Error: $error")
                    loadDatabaseRepositories()
                },    // onError
                {
                    DatabaseManager.saveUsersRepos(appDao, userList)
                    println("Completed!")
                }                 // onComplete
            )
    }

    fun loadDatabaseRepositories(): Disposable {
        return DatabaseManager.loadUsersRepos(appDao)
            .subscribe(
                { value ->
                    userList.clear();
                    userList.addAll(value)
                },
                { error -> println("Error: $error") },    // onError
                {
                    // postValue - потому что не в основном потоке.
                    _userListLiveData.postValue(userList)
                    println("Completed!")                 // onComplete
                }
            )
    }


        /*
        ApiManager.loadOrganizationRepos()
            //.doOnError { mView?.showMessage(it.toString()) }
            .subscribe( Consumer { mView?.showOrganizations(it) },
                GeneralErrorHandler(mView, true, { throwable, errorBody, isNetwork -> mView?.showError(throwable.message) })
            )

         */


}


/*
class SomeViewModel(private val someUseCase: SomeUseCase) : ViewModel() {

    private val loanListLiveData = MutableLiveData<List<Deposit>>()

    fun onRefreshButtonClickedRx() {
        someUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { depositList ->
                //activity.runOnUiThread {
                loanListLiveData.postValue(depositList)
                //}
            }
    }

    fun onRefreshButtonClicked() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val depositList = someUseCase.execute()
            }
            loanListLiveData.postValue(depositList)
        }
    }

    //companion object {
    //    lateinit var activity: Activity
    //}

}
*/

