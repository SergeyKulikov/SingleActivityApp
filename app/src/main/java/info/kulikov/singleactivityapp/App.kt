package info.kulikov.singleactivityapp

import android.app.Application
import androidx.multidex.MultiDexApplication
import info.kulikov.singleactivityapp.database.AppRoomDatabase

class App : MultiDexApplication() {
   val database by lazy { AppRoomDatabase.getDatabase(this) }
   val dao by lazy { database.appDao() }

/*
     var appDatabase: AppRoomDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        //instance =


        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppRoomDatabase::class.java, packageName + "_db"
        ).build()
    }

    companion object {
        private var instance: App? = null
        fun getInstance(): App? {
            if (instance == null) {
                instance = App()
            }
            return instance
        }
    }

     */
}
