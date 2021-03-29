package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import reminder.Reminder;
@Dao
public interface reminderDao {
    @Query("SELECT * FROM reminder_table")
    List<Reminder> getReminders();

    @Query("SELECT * FROM reminder_table WHERE id =:id")
    Reminder getReminder(int id);

    @Query("DELETE FROM reminder_table")
    void deleteReminders();

    @Insert
    void addReminder(Reminder reminder);

    @Update
    void updateReminder(Reminder reminder);

    @Delete
    void deleteReminder(Reminder reminder);
}
