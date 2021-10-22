package info.kulikov.singleactivityapp.database

import info.kulikov.singleactivityapp.App
import info.kulikov.singleactivityapp.domain.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DatabaseManager {


    fun loadUsersRepos(dao: AppDao) =
        dao.loadUserList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun saveUsersRepos(dao: AppDao, userList: List<User>?) =
        userList?.let {
            dao.saveUserList(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }!!

}