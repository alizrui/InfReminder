package com.example.infreminder.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import com.example.infreminder.pojo.Reminder;
@Dao
public interface ReminderDao {
    /**
     * Selecciona todos los reminder de la BD
     *
     * @return
     */
    @Query("SELECT * FROM reminder_table")
    List<Reminder> getReminders();

    /**
     * Selecciona un reminder que coincide con la ID
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM reminder_table WHERE id =:id")
    Reminder getReminder(int id);

    /**
     * Borra todos los reminders de la BD
     *
     */
    @Query("DELETE FROM reminder_table")
    void deleteReminders();

    /**
     * Selecciona aquellos reminders que coinciden en un intérvalo de fechas
     * @param minTime
     * @param maxTime
     * @return
     */
    @Query("SELECT * FROM reminder_table WHERE reminder_date BETWEEN :minTime AND :maxTime")
    List<Reminder> loadBetweenDate(long minTime, long maxTime);

    /**
     * Inserta un reminder en la BD
     *
     */
    @Insert
    void addReminder(Reminder reminder);

    /**
     * Actualiza un reminder en la BD
     *
     * @param reminder
     */
    @Update
    void updateReminder(Reminder reminder);

    /**
     * Elimina un reminder de la BD
     *
     * @param reminder
     */
    @Delete
    void deleteReminder(Reminder reminder);

}
