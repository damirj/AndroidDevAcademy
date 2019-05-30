package hr.ferit.brunozoric.taskie.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import hr.ferit.brunozoric.taskie.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = IGNORE)
    fun insertTask(task: Task)

    @Query("SELECT * FROM Task")
    fun getAllTasks(): MutableList<Task>

    @Delete
    fun deleteTask(task: Task)

    @Query("UPDATE Task SET title= :newTaskTitle, content= :newTaskDescription, taskPriority = :newTaskPriority WHERE id = :id")
    fun editTask(id: String, newTaskTitle: String, newTaskDescription: String, newTaskPriority: Int)

    @Query("SELECT * FROM Task WHERE id= :id")
    fun getTask(id: String): Task

    @Query("DELETE FROM Task")
    fun deleteAllTasks()

    @Query("SELECT * FROM Task WHERE isSent= :isSent")
    fun getAllNotSent(isSent: Boolean = false): MutableList<Task>

    @Query("UPDATE Task SET isSent= :isSent WHERE isSent= :isSentBefore")
    fun updateNotSentTasks(isSent: Boolean = true, isSentBefore: Boolean = false)
}
