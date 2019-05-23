package hr.ferit.brunozoric.taskie.ui.adapters

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.view.*

class TaskHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(task: Task, onItemSelected: (Task) -> Unit) {

        containerView.setOnClickListener { onItemSelected(task) }

        containerView.taskTitle.text = task.title

        val drawable = containerView.taskPriority.drawable
        val wrapDrawable = DrawableCompat.wrap(drawable)

        var color = when(task.priority){
            Priority.HIGH.name -> Priority.HIGH.getColor()
            Priority.MEDIUM.name -> Priority.MEDIUM.getColor()
            Priority.LOW.name -> Priority.LOW.getColor()
            else -> Priority.LOW.getColor()
        }
        Log.d("BOJANJE HOLDER", color.toString())
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(containerView.context, color))

    }


}