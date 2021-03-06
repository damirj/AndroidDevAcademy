package hr.ferit.brunozoric.taskie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.ferit.brunozoric.taskie.model.Task

@Database(entities = [Task::class], version = 1)
abstract class NewDaoProvider: RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object{
        private var instance: NewDaoProvider? = null

        fun getInstance(context: Context): NewDaoProvider {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewDaoProvider::class.java,
                    "NewTaskDb"
                ).allowMainThreadQueries().build()
            }
            return instance as NewDaoProvider
        }
    }
}