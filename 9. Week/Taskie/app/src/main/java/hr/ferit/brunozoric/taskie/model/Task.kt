package hr.ferit.brunozoric.taskie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey (autoGenerate = true)
    var taskDbId: Long? = null,
    var id: String,
    val userId: String = "",
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val taskPriority: Int,
    val isCompleted: Boolean = false,
    val isSent: Boolean = true
)