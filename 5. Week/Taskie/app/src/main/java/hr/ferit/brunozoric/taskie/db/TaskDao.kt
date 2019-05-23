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

    @Query("UPDATE Task SET title= :newTaskTitle, description= :newTaskDescription, priority = :newTaskPriority WHERE taskDbId = :taskId")
    fun editTask(taskId: Long, newTaskTitle: String, newTaskDescription: String, newTaskPriority: String)

    @Query("SELECT * FROM Task WHERE taskDbId= :taskId")
    fun getTask(taskId: Long): Task

    @Query("DELETE FROM Task")
    fun deleteAllTasks()
}
