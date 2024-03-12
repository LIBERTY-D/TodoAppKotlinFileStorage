package com.daniel.todoappkoitlinfilestorage.Helpers

import android.content.Context
import com.daniel.todoappkoitlinfilestorage.Constants
import com.daniel.todoappkoitlinfilestorage.Models.TodoModel
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {

    companion object{

        fun writeFile(context: Context,todos:ArrayList<TodoModel>):Unit{
            try {
                var fos:FileOutputStream = context.openFileOutput(Constants.FILE_STORAGE_NAME,Context.MODE_PRIVATE)
                var ous:ObjectOutputStream = ObjectOutputStream(fos)
                ous.writeObject(todos)
                ous.close()
                fos.close()
            }catch (e:IOException){
                println(e.message)
            }


        }

        fun readFile(context: Context): ArrayList<TodoModel>{
            var ois:ObjectInputStream?=null
            try {
                var fis: FileInputStream = context.openFileInput(Constants.FILE_STORAGE_NAME)
                ois = ObjectInputStream(fis)

            }catch (e:IOException){
                println(e.message)
            }
            if (ois != null) {
                return ois.readObject() as ArrayList<TodoModel>
            }else{
                return ArrayList<TodoModel>()
            }
        }
    }
}