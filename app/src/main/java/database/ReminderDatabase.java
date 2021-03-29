package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import reminder.Reminder;

@Database(entities = {Reminder.class}, version = 1, exportSchema = false)
public abstract class ReminderDatabase extends RoomDatabase {
    private static ReminderDatabase reminderDatabase;
    public synchronized static ReminderDatabase getInstance(Context context) {
        if (reminderDatabase == null) {
            reminderDatabase = Room
                    .databaseBuilder(context, ReminderDatabase.class, "reminder_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return reminderDatabase;
    }

    public abstract reminderDao reminderDao();
}
