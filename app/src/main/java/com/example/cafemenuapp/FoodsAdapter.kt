package com.example.cafemenuapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FoodsAdapter(list: List<Food>) : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {
    var listFood: ArrayList<Food> = ArrayList(list)
    var context: Context? = null

  //  fun FoodsAdapter(context: Context?, listFood: List<Food?>) {
  //      this.context = context
  //      this.listFood = listFood as ArrayList<Food>
   // }

    /** init {
        listFood = list as ArrayList<Food>
    }

    fun FoodsAdapter(list: java.util.ArrayList<Food?>?) {
        listFood = list
    } **/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food: Food = listFood[position]
        holder.Name.setText(food.Name)
        holder.Price.setText(food.Price)
        holder.image.setImageDrawable(food.image)
    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val Name: TextView = itemView.findViewById(R.id.food_name)
        // val Description: TextView = itemView.findViewById(R.id.description)
        val image: ImageView = itemView.findViewById(R.id.image_photo)
        val Price: TextView = itemView.findViewById(R.id.price)


        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(view: View) {
            val selected = layoutPosition
            val intent = Intent(view.context, DetailActivity::class.java)
            intent.putExtra("id", selected)
            view.context.startActivity(intent)
        }
    }
}