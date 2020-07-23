package com.harry.example.filternameassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.userdetails_layout.view.*
import java.lang.NumberFormatException

class UserAdapter(private val context: Context, private val users: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewGolder>(), Filterable {
    private val copyUsersList: ArrayList<User> = arrayListOf()

    init {
        copyUsersList.addAll(users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewGolder {
        return UserViewGolder(
            LayoutInflater.from(context).inflate(R.layout.userdetails_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        if (users.size == 0) {
            return users.size + 1
        } else {
            return users.size
        }
    }


    override fun onBindViewHolder(holder: UserViewGolder, position: Int) {
        if (users.size > 0) {
            if (holder.no_user.isVisible) {
                holder.group.visibility = View.VISIBLE
                holder.no_user.visibility = View.GONE
            }
            val user = users.get(position)
            holder.id.text = user.id.toString()
            holder.name.text = user.name
        } else {
            holder.group.visibility = View.GONE
            holder.no_user.visibility = View.VISIBLE
        }
    }

    inner class UserViewGolder(private val views: View) : RecyclerView.ViewHolder(views) {
        val id: TextView
        val name: TextView
        val no_user: TextView
        val group: Group

        init {
            id = views.user_id
            name = views.user_name
            no_user = views.no_user_found
            group = views.details
        }
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredUsers = arrayListOf<User>()
                val filteredResult = FilterResults()
                when {
                    (constraint.isNullOrEmpty() || constraint.isNullOrBlank()) -> {
                        filteredUsers.addAll(copyUsersList)
                    }
                    else -> {
                        copyUsersList.forEach {
                            try {
                                val id = constraint.toString().toInt()
                                if (it.id == id) {
                                    filteredUsers.add(it)
                                }
                            } catch (exception: NumberFormatException) {
                                if (it.name.contains(constraint.toString(), true)) {
                                    filteredUsers.add(it)
                                }
                            }
                        }
                    }
                }
                filteredResult.values = filteredUsers
                filteredResult.count = filteredUsers.size
                return filteredResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                users.clear()
                users.addAll(results?.values as ArrayList<User>)
                notifyDataSetChanged()
            }
        }
        return filter
    }
}