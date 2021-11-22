package android.jmichalek.jaymichalek_capstone.All.Database;

import android.content.Context;
import android.jmichalek.jaymichalek_capstone.All.DAO.AssessmentDAO;
import android.jmichalek.jaymichalek_capstone.All.DAO.CourseDAO;
import android.jmichalek.jaymichalek_capstone.All.DAO.TermDAO;
import android.jmichalek.jaymichalek_capstone.All.Entities.Assessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.Course;
import android.jmichalek.jaymichalek_capstone.All.Entities.Term;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 15, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "MyDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;

    }

}
