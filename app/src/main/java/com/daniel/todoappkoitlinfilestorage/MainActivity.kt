package com.daniel.todoappkoitlinfilestorage

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.daniel.todoappkoitlinfilestorage.Adapters.TodoAdapter
import com.daniel.todoappkoitlinfilestorage.Helpers.FileHelper
import com.daniel.todoappkoitlinfilestorage.Models.TodoModel
import com.daniel.todoappkoitlinfilestorage.customEvents.OnItemClickListener
import com.daniel.todoappkoitlinfilestorage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    lateinit var activity:ActivityMainBinding
    lateinit var todos:ArrayList<TodoModel>
    lateinit var todoAdapter:TodoAdapter
    lateinit var registerforResults:ActivityResultLauncher<Intent>
    lateinit var registerforResultEdit:ActivityResultLauncher<Intent>
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity =  ActivityMainBinding.inflate(layoutInflater)
        val view:View = activity.root
        setContentView(view)
        registerForResultsContact();
        registerforResultEditMethod();
        todos =  FileHelper.readFile(applicationContext)
//        todos.add(TodoModel("Todo1","1",false,null))
//        todos.add(TodoModel("Todo2","2",false,null))
//        todos.add(TodoModel("Todo3","3",false,null))
//        todos.add(TodoModel("Todo4","4",false,null))

         todoAdapter= TodoAdapter(todos,applicationContext)
        var myLayout:GridLayoutManager =  GridLayoutManager(applicationContext,2)
        activity.rvTodos.adapter = todoAdapter
        activity.rvTodos.layoutManager=  myLayout
        todoAdapter.notifyDataSetChanged()



//        custom click listener
        todoAdapter.setOnclickListener(object :OnItemClickListener{
            override fun onItemClick(pos: Int,view: View) {
                val popupMenu = PopupMenu(applicationContext, view,Gravity.END,0,R.style.MyPopupMenuStyle)
                popupMenu.inflate(R.menu.popup)
                popupMenu.show()
//                popupMenu click
                popupMenu.setOnMenuItemClickListener { menuItem->
                   val id:Int =  menuItem.itemId
                    when(id){
                        R.id.edit->{
                            var todoModel:TodoModel = todos[pos]
                            var editIntent:Intent =  Intent(this@MainActivity,EditActivity::class.java)
                            editIntent.putExtra("todoItem",todoModel)
                            editIntent.putExtra("pos",pos)
                            registerforResultEdit.launch(editIntent)

                            true
                        }
                        R.id.delete->{
                           var alert:AlertDialog =  AlertDialog.Builder(this@MainActivity).
                           setTitle("Delete").setMessage("You Sure you want to delete this").
                           setPositiveButton("Yes") { dialog, _ ->
                               todos.removeAt(pos)
                               FileHelper.writeFile(this@MainActivity,todos)
                               todoAdapter.notifyDataSetChanged()
                               dialog.dismiss()
                           }.setNegativeButton("No") { dialog, _ ->
                                 dialog.dismiss()

                           }.create();
                            alert.show()
                            true
                        }
                        R.id.archieve->{
                            var todoModel:TodoModel = todos[pos]
                           // check if its already archieved
                            if(todoModel.archieved==true){
                                todoModel.archieved = false
                            }else{
                                // check if its not
                                todoModel.archieved = true
                            }
                            todos[pos] =  todoModel

                            FileHelper.writeFile(this@MainActivity,todos)
                            todoAdapter.notifyDataSetChanged()

                            true
                        }
                        else->true
                    }

                }
            }

        })


//        addTodoActivity
        activity.btnFloat.setOnClickListener {
            var addToIntent:Intent = Intent(this@MainActivity,AddTodo::class.java)
            registerforResults.launch(addToIntent)

        }

    }

    /**
     * register for todoActivity results
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun registerForResultsContact():Unit{
        registerforResults = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){res->
           if(res.resultCode == Activity.RESULT_OK){
               val data:Intent? = res.data
               if(data!=null){
                   val todoModel:TodoModel? = data.getSerializableExtra("todo",TodoModel::class.java)
                   todos.add(0,todoModel!!)
                   FileHelper.writeFile(applicationContext,todos)
                  todoAdapter.notifyDataSetChanged()
               }
           }


        }
    }

    /**
     * register for  registerResultEdit results
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private  fun  registerforResultEditMethod():Unit{

        registerforResultEdit =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){res->
            if(res.resultCode == Activity.RESULT_OK){
                val data:Intent? = res.data
                if(data!=null){
                    val todoModel:TodoModel? = data.getSerializableExtra("todoItem",TodoModel::class.java)
                    val pos:Int =  data.getIntExtra("pos",0)
                    todos[pos] =  todoModel!!
                    FileHelper.writeFile(applicationContext,todos)
                    todoAdapter.notifyDataSetChanged()
                }
            }


        }
    }


}