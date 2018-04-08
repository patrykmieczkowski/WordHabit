package com.mieczkowskidev.wordhabit.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Patryk Mieczkowski on 02.04.2018
 */
data class MyNotification(var primaryLangWord: String?, var primaryLangDescription: String?,
                          var secondaryLangWord: String?, var secondaryLangDescription: String?,
                          var image: String?, var translateType: TranslateType) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            TranslateType.valueOf(parcel.readString()))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(primaryLangWord)
        parcel.writeString(primaryLangDescription)
        parcel.writeString(secondaryLangWord)
        parcel.writeString(secondaryLangDescription)
        parcel.writeString(image)
        parcel.writeString(translateType.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyNotification> {
        override fun createFromParcel(parcel: Parcel): MyNotification {
            return MyNotification(parcel)
        }

        override fun newArray(size: Int): Array<MyNotification?> {
            return arrayOfNulls(size)
        }
    }
}