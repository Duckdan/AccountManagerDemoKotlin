package com.study.yang.accoutmanagerdemokotlin.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.study.yang.accoutmanagerdemokotlin.R

class MyCursorAdapter(context: Context?, cursor: Cursor?) : CursorAdapter(context, cursor, true) {

    /**
     * 创建View的实例时调用
     */
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, null)
        var viewHolder = ViewHolder()
        viewHolder.tv_name = view.findViewById(R.id.tv_name)
        viewHolder.tv_age = view.findViewById(R.id.tv_age)
        viewHolder.tv_description = view.findViewById(R.id.tv_description)
        view.tag = viewHolder
        return view
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val viewHolder = view?.tag as ViewHolder
        val email = cursor?.getString(cursor?.getColumnIndex("email"))
        val pwd = cursor?.getString(cursor?.getColumnIndex("pwd"))

        viewHolder.tv_name.text = email
        viewHolder.tv_age.text = pwd

    }

    class ViewHolder {
        lateinit var tv_name: TextView
        lateinit var tv_age: TextView
        lateinit var tv_description: TextView

    }
}