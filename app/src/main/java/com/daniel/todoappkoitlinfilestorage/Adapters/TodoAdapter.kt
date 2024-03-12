package com.daniel.todoappkoitlinfilestorage.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daniel.todoappkoitlinfilestorage.Models.TodoModel
import com.daniel.todoappkoitlinfilestorage.R
import com.daniel.todoappkoitlinfilestorage.customEvents.OnItemClickListener

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>{
     private var todos:ArrayList<TodoModel>;
     private var  context:Context;
    private lateinit var onItemClickListener:OnItemClickListener;

    constructor(todos: ArrayList<TodoModel>, context: Context) : super() {
        this.todos = todos
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var view:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo,parent, false)
        return  TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
       return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
     var todoItem:TodoModel = todos[position]
        holder.tv_title.text = todoItem.title
        holder.tv_date.text =  todoItem.date
        if(todoItem.archieved == true){
            holder.img_not_done.setImageResource(R.drawable.done_marker)
            holder.tv_archieved.text ="archieved"
        }else{
            holder.tv_archieved.text ="not archieved"
            holder.img_not_done.setImageResource(R.drawable.uncheck)
        }

        holder.img_more_btn.setOnClickListener{
             onItemClickListener.onItemClick(position,holder.img_more_btn)

        }

    }
    public fun setOnclickListener(onItemClickListener: OnItemClickListener):Unit{
        this.onItemClickListener = onItemClickListener
    }


    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        var img_more_btn = itemView.findViewById<ImageView>(R.id.img_more_btn)
        var tv_archieved  =  itemView.findViewById<TextView>(R.id.tv_archieved)
        var tv_date =  itemView.findViewById<TextView>(R.id.tv_date)
        var  img_not_done=  itemView.findViewById<ImageView>(R.id.img_done)




    }




}