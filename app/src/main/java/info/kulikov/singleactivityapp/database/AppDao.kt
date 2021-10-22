package info.kulikov.singleactivityapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.kulikov.singleactivityapp.domain.User
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface AppDao {
    @Query("SELECT * FROM `User` ORDER BY `first_name`, `last_name`")
    fun loadUserList(): Observable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserList(users: List<User>): Maybe<List<Long>>
}