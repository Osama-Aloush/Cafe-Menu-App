package com.example.cafemenuapp

import android.graphics.drawable.Drawable


class Food(Name: String, Description: String, Price: String, drawable: Drawable?) {
    var Name: String = Name
    private set

    var Description: String = Description
        private set

    var Price: String = Price
        private set

    var image: Drawable? = drawable

    /** fun Food(Name: String, Description: String, Price: String, image: Drawable?) {
        this.Name = Name
        this.Description = Description
        this.Price = Price
        this.image = image
    } **/

 /**   fun Food(mDrinkName: String, mDrinkDetail: String, mDrinkPhoto: Drawable) {
        Name = mDrinkName
        Description = mDrinkDetail
        image = mDrinkPhoto
    }

    fun getmDrinkName(): String? {
        return Name
    }

    fun getmDrinkDetail(): String? {
        return Description
    }

    fun getmDrinkPhoto(): Drawable? {
        return image
    } **/

}