package com.example.infreminder.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.infreminder.pojo.Reminder;

/**
 * Crea instancia de la base de datos.
 */
@Database(entities = {Reminder.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ReminderDatabase extends RoomDatabase {
    private static ReminderDatabase reminderDatabase;
    public synchronized static ReminderDatabase getInstance(Context context) {
        if (reminderDatabase == null) {
            reminderDatabase = Room
                    .databaseBuilder(context, ReminderDatabase.class, "reminder_database")
                    .build();
        }
        return reminderDatabase;
    }

    /**
     *
     * @return
     */
    public abstract ReminderDao reminderDao();

}
