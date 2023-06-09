package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemEmployeeBinding
import com.example.myapplication.repository.model.DataItem

class EmployeeAdapter() : Adapter<UserViewHolder>(),Filterable {
    private var list = ArrayList<DataItem>()
    private var listData = ArrayList<DataItem>()
    private lateinit var binding: ItemEmployeeBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_employee,
            parent,
            false
        )
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        var data = list.get(position)
        binding.tvSalary.text = data.employeeSalary.toString()
        binding.tvName.text = data.employeeName
        Glide.with(holder.itemView.context).load(data.profileImage).transform(CircleCrop()).placeholder(R.mipmap.ic_launcher)
            .into(binding.ivEmployee)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(dataList:ArrayList<DataItem>){
        list=dataList
        listData=dataList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrBlank()) {
                    listData
                } else {
                    list.filter {
                        it.employeeName?.contains(constraint, true) == true
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results?.values as? ArrayList<DataItem>
                    ?: ArrayList<DataItem>()
                notifyDataSetChanged()
            }
        }
    }
}

class UserViewHolder(view: View) : ViewHolder(view)
