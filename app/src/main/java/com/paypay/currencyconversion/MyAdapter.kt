package com.paypay.currencyconversion

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.paypay.currencyconversion.MyAdapter.MyViewHolder

class MyAdapter// Provide a suitable constructor (depends on the kind of dataset)
    (private val mDataset: Map<String, String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(var textView: TextView) : RecyclerView.ViewHolder(textView)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_holder,
            parent,
            false
        ) as TextView

        return MyViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = mDataset.values.toTypedArray()[position]

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        Log.i("count", "count " + mDataset.values.size)
        return mDataset.values.size
    }
}
