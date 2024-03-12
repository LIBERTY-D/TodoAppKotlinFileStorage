package com.daniel.todoappkoitlinfilestorage

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daniel.todoappkoitlinfilestorage.Models.TodoModel
import com.daniel.todoappkoitlinfilestorage.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var editBinding: ActivityEditBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editBinding =  ActivityEditBinding.inflate(layoutInflater)
        var view: View =  editBinding.root
        setContentView(view)


        var data:Intent =  intent//getIntent
        var todoModel:TodoModel? = data.getSerializableExtra("todoItem",TodoModel::class.java)
        var pos:Int  =  data.getIntExtra("pos",0)
         editBinding.editTodoA.setText(todoModel?.title)

        editBinding.btnEdit.setOnClickListener {

            var todoModel:TodoModel? = todoModel
            var editTodo:String = editBinding.editTodoA.text.toString()
            todoModel!!.title = editTodo
            var resIntent:Intent = Intent()
            resIntent.putExtra("todoItem",todoModel)
            resIntent.putExtra("pos",pos)
            setResult(Activity.RESULT_OK,resIntent)
            finish()
        }

        editBinding.btnEditTodoActivity.setOnClickListener {

            finish()
        }


    }
}