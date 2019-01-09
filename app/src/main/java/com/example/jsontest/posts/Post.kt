package com.example.jsontest.posts

import android.os.Parcel
import android.os.Parcelable

data class Post(val userId : Int, val id : Int, val title : String, val body : String) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.userId)
        dest.writeInt(this.id)
        dest.writeString(this.title)
        dest.writeString(this.body)
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    companion object CREATOR: Parcelable.Creator<Post>  {
        override fun createFromParcel(source: Parcel): Post {
            return Post(source)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}
