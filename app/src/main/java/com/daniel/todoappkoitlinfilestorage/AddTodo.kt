package com.daniel.todoappkoitlinfilestorage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.daniel.todoappkoitlinfilestorage.Models.TodoModel
import com.daniel.todoappkoitlinfilestorage.databinding.ActivityAddTodoBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class AddTodo : AppCompatActivity() {
    lateinit var binding:ActivityAddTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityAddTodoBinding.inflate(layoutInflater)
        var view: View = binding.root
        setContentView(view)




        binding.btnAdd.setOnClickListener {view->
            val title:String =  binding.editTodo.text.toString()
            val uid:String = UUID.randomUUID().toString()
            val date:Long =  binding.calendarView.date;
            val sdf: SimpleDateFormat = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())// Format the date and return the formatted string
            val todoModel:TodoModel =  TodoModel(title,uid,false,sdf.format(date))
            var resultIntent = Intent()
            resultIntent.putExtra("todo",todoModel)
            setResult(Activity.RESULT_OK,resultIntent)
            finish()

        }


//close  activity
        binding.btnCloseTodoActivity.setOnClickListener {

            finish()
        }
    }
}