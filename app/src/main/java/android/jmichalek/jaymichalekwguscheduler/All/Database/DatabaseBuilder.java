package android.jmichalek.jaymichalekwguscheduler.All.Database;

import android.content.Context;
import android.jmichalek.jaymichalekwguscheduler.All.DAO.AssessmentDAO;
import android.jmichalek.jaymichalekwguscheduler.All.DAO.CourseDAO;
import android.jmichalek.jaymichalekwguscheduler.All.DAO.TermDAO;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Assessment;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Course;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Term;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 3, exportSchema = false) //TODO: Change version when making changes to database.
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
