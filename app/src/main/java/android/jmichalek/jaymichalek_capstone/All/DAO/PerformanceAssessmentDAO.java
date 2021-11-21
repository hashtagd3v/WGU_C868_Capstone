package android.jmichalek.jaymichalek_capstone.All.DAO;

import android.jmichalek.jaymichalek_capstone.All.Entities.PerformanceAssessment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PerformanceAssessmentDAO {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insert(PerformanceAssessment performanceAssessment);

    @Update
    void update(PerformanceAssessment performanceAssessment);

    @Delete
    void delete(PerformanceAssessment performanceAssessment);

    @Query("SELECT * FROM performance_assessment")
    List<PerformanceAssessment> getAllPerformanceAssessments();

    @Query("SELECT * FROM performance_assessment WHERE courseID = :courseID")
    List<PerformanceAssessment> getPerformanceAssessmentsByCourseID(final int courseID);

}
