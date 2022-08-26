package com.example.newbagshop.models

import android.os.Parcel
import android.os.Parcelable

data class  ShoppingModel( val delivery_charge: String?,
                           val name: String?,
                           val image: String?,
                           var menus: List<Menus?>?) :

    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Menus)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(delivery_charge)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeTypedList(menus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingModel> {
        override fun createFromParcel(parcel: Parcel): ShoppingModel {
            return ShoppingModel(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class Menus(
    val delivery_charge: String?,
    val name: String?,
    val price: Float?, val url: String?, var totalInCart:Int) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(delivery_charge)
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(url)
        parcel.writeInt(totalInCart)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Menus> {
        override fun createFromParcel(parcel: Parcel): Menus {
            return Menus(parcel)
        }

        override fun newArray(size: Int): Array<Menus?> {
            return arrayOfNulls(size)
        }
    }
}