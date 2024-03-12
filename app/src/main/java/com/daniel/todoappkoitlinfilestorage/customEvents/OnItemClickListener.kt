package com.daniel.todoappkoitlinfilestorage.customEvents

import android.view.View
import android.widget.PopupMenu

interface OnItemClickListener {
    fun onItemClick(pos:Int, view: View):Unit
}