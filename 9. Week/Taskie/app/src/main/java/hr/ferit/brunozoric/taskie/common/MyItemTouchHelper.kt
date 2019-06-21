package hr.ferit.brunozoric.taskie.common

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.brunozoric.taskie.R

class MyItemTouchHelper(private val deleteIcon: Drawable) {

    private val swipeBackground: ColorDrawable = ColorDrawable(Color.RED)
    private var swipeListener: SwipeListener? = null

    interface SwipeListener{
        fun onSwiped(viewHolder: RecyclerView.ViewHolder?)
    }

    fun setSwipeListener(listener: SwipeListener){
        swipeListener = listener
    }

    fun setUpItemTouchHelper(): ItemTouchHelper.SimpleCallback {
        //deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_sweep_black_24dp)!!
        return object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                swipeListener?.onSwiped(viewHolder)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val itemView = viewHolder.itemView

                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                swipeBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth, itemView.top + iconMargin, itemView.right - iconMargin,
                    itemView.bottom - iconMargin)
                swipeBackground.draw(c)
                c.save()
                c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                deleteIcon.draw(c)
                c.restore()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        /*
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        */
    }
}