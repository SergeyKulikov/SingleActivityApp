package info.kulikov.singleactivityapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User (
   @PrimaryKey
   var id: Int,
   var email: String,
   var first_name: String,
   var last_name: String,
   var avatar: String

) : Serializable
