package one.empty3.feature.app.maxSdk29.pro


import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Comment
import java.util.UUID

@Entity
data class Picture(
    val filepath : String,
    val idPrevious : Int,
    val uuid: UUID,
    val version: String,
    val comment: String,
    @PrimaryKey
    var id:Int = 0
)
