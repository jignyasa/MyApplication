package com.example.myapplication.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.model.DataItem
import com.example.myapplication.view.adapter.EmployeeAdapter
import com.example.myapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: EmployeeAdapter
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, com.example.myapplication.R.layout.activity_main)
        initViewModel()
        initView()
    }

    /**
     * initialize views
     */
    private fun initView() {
        adapter = EmployeeAdapter()
        binding.rvEmployee.adapter = adapter
        binding.rvEmployee.layoutManager = LinearLayoutManager(this)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        binding.ivFilter.setOnClickListener {
            val popup = PopupMenu(this@MainActivity, it)
            popup.setOnMenuItemClickListener(this@MainActivity)
            popup.inflate(com.example.myapplication.R.menu.popup_menu)
            popup.show()
        }

    }

    /**
     * initialize viewmodel
     */
    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel._data.observe(this, Observer {
            when (it.first) {
                "ERROR" -> Toast.makeText(
                    this@MainActivity,
                    it.second.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                "DATA_LOADED" -> {
                    var dataList=ArrayList<DataItem>()
                    dataList.addAll(it.second as ArrayList<DataItem>)
                    adapter.addData(viewModel.list)
                }
            }
        })
        if (MainActivity.isOnline(this)) {
            viewModel.getDataApi()
        } else {
            Toast.makeText(
                this@MainActivity,"no internet available",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.fetchUserDataInDb()
        }
    }

    companion object{
        fun isOnline(context: Context): Boolean {
            val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
                if (networkCapabilities == null) {
                    return false
                } else {
                    return true
                }
            } else {
                // below Marshmallow
                val activeNetwork = connManager.activeNetworkInfo
                if (activeNetwork?.isConnectedOrConnecting == true && activeNetwork.isAvailable) {
                    return true
                } else {
                    return false
                }
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            com.example.myapplication.R.id.asce->{
                viewModel.list.sortBy {dataItem: DataItem? -> dataItem?.employeeName }
                adapter.addData(viewModel.list)
            }
            com.example.myapplication.R.id.desc->{
                viewModel.list.sortByDescending {dataItem: DataItem? -> dataItem?.employeeName }
                adapter.addData(viewModel.list)
            }
        }
        return false
    }
}