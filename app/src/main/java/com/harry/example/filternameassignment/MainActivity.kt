package com.harry.example.filternameassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var userAdapter: UserAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = applicationContext.resources.getString(R.string.names)
        val listType = object : TypeToken<ArrayList<User>>() {}.type
        val users: ArrayList<User> = Gson().fromJson<ArrayList<User>>(data, listType)
        userAdapter = UserAdapter(applicationContext, users)
        setUpRecyclerView()
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i("TAG", "onTextChanged: ${s.toString()}")
                userAdapter?.filter?.filter(s)
            }

        })
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(applicationContext)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = userAdapter
    }
}