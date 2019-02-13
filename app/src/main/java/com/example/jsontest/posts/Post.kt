package com.example.jsontest.posts

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(@PrimaryKey val id : Int,
                @ColumnInfo(name = "user_id") val userId : Int,
                @ColumnInfo(name = "title") val title : String,
                @ColumnInfo(name = "body") val body : String) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id)
        dest.writeInt(this.userId)
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
