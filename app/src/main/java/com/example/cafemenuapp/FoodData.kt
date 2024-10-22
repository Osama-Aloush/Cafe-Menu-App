package com.example.cafemenuapp

import android.content.Context
import java.util.ArrayList
import com.example.cafemenuapp.Food

class FoodData {

    companion object {
        fun getData(context: Context): ArrayList<Food> {
            val list: ArrayList<Food> = ArrayList<Food>()
            list.add(
                Food(
                    "Fried noodles",
                    "food originating from Indonesia that is popular and also popular in Malaysia and Singapore. Fried noodles are made from yellow noodles fried in a little cooking oil, and added with garlic, shallots, shrimp and chicken or beef, sliced meatballs, chilies, vegetables, tomatoes, chicken eggs and pickles.",
                    "15 Pounds",
                    context.getDrawable(R.drawable.mie_goreng)
                )
            )
            list.add(
                Food(
                    "Cireng",
                    "Cireng is a snack originating from the Sundanese region which is made by frying a mixture of dough made from starch or tapioca. This snack is very popular in the Priangan area, and is sold in various forms and flavor variations. This food was quite famous in the 80s.",
                    "7 Pounds",
                    context.getDrawable(R.drawable.cireng)
                )
            )
            list.add(
                Food(
                    "Cappuccino",
                    "Kapucino is a typical Italian drink made from espresso and milk, but there are also other references which say that cappuccino originated from Turkish army coffee beans left behind after the war led by Kara Mustapha Pasha in Vienna, Austria against the joint Polish-Germania army.",
                    "5 Pounds",
                    context.getDrawable(R.drawable.cappuchino)
                )
            )
            list.add(
                Food(
                    "Donut",
                    "Donuts are fried snacks, made from a mixture of wheat flour, sugar, eggs and butter. The most common donuts are ring-shaped donuts with a hole in the middle and round donuts with sweet fillings, such as jam, jelly, cream and custard.",
                    "4 Pounds",
                    context.getDrawable(R.drawable.donut)
                )
            )
            list.add(
                Food(
                    "Sparkling Tea",
                    "This drink is guaranteed to make you fresh. The combination of the sour taste of fresh strawberries mixed with tea and soda. Can't you imagine the fresh sensation? Let's try making it! This drink can be served during the day during your break, or as a menu for breaking your fast after Maghrib. Good luck!",
                    "5 Pounds",
                    context.getDrawable(R.drawable.sparkling_tea)
                )
            )
            list.add(
                Food(
                    "Fried rice",
                    "Fried rice is a food in the form of rice that is fried and stirred in cooking oil, margarine or butter. Usually added with sweet soy sauce, shallots, garlic, tamarind, pepper and other spices; such as eggs, chicken, and crackers.",
                    "10 Pounds",
                    context.getDrawable(R.drawable.nasigoreng)
                )
            )
            return list
        }

        fun getImage(): java.util.ArrayList<Int>? {
            val drawables = java.util.ArrayList<Int>()
            drawables.add(R.drawable.mie_goreng)
            drawables.add(R.drawable.cireng)
            drawables.add(R.drawable.cappuchino)
            drawables.add(R.drawable.donut)
            drawables.add(R.drawable.sparkling_tea)
            drawables.add(R.drawable.nasigoreng)
            return drawables
        }
    }
}