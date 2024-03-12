package com.daniel.todoappkoitlinfilestorage.Models

import java.io.Serializable

data class TodoModel(var title: String, var uid: String, var archieved: Boolean?, var date: String?) :Serializable {


}