package fts.ahmed.diaryapp.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "diaries_table")
 class Diary(var title: String, var text: String, var alarm: Long?) {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0

    constructor(title: String,text: String) : this(title,text,null) {
        this.text=text
        this.title=title
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Diary

        if (title != other.title) return false
        if (text != other.text) return false
        if (alarm != other.alarm) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + (alarm?.hashCode() ?: 0)
        result = 31 * result + id
        return result
    }

}
