package com.paypay.currencyconversion

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.paypay.currencyconversion.Activity.MainActivity

class RatesAdapter(private val myDataset: Map<String, String>, private val mainActivity: MainActivity) : RecyclerView.Adapter<RatesAdapter.MyViewHolder>() {

    class MyViewHolder(val root: LinearLayout) : RecyclerView.ViewHolder(root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesAdapter.MyViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_holder, parent, false) as LinearLayout

        return MyViewHolder(root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var leftEntity = CountryRate(myDataset.keys.toList().get(position * 3).substring(3));//, myDataset.values.get(position * 3))
        var middleEntity = CountryRate(myDataset.keys.toList().get(position * 3 + 1).substring(3));//, myDataset.values.toList().get(position * 3))
        var rightEntity = CountryRate(myDataset.keys.toList().get(position * 3 + 2).substring(3));//, myDataset.values.toList().get(position * 3))

        (holder.root.findViewById(R.id.left) as CustomView).setCountryRate(leftEntity, mainActivity)
        (holder.root.findViewById(R.id.middle) as CustomView).setCountryRate(middleEntity, mainActivity)
        (holder.root.findViewById(R.id.right) as CustomView).setCountryRate(rightEntity, mainActivity)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.values.size / 3
}
