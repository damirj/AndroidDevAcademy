package hr.ferit.brunozoric.taskie.ui.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.Priority
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.view.*

class TaskHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(task: BackendTask, onItemSelected: (BackendTask) -> Unit) {

        containerView.setOnClickListener { onItemSelected(task) }

        containerView.taskTitle.text = task.title

        val drawable = containerView.taskPriority.drawable
        val wrapDrawable = DrawableCompat.wrap(drawable)

        val color = when(task.taskPriority){
            3 -> Priority.HIGH.getColor()
            2 -> Priority.MEDIUM.getColor()
            else -> Priority.LOW.getColor()
        }
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(containerView.context, color))
    }


}