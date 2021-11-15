package android.jmichalek.jaymichalek_capstone.All.DAO;

import android.jmichalek.jaymichalek_capstone.All.Entities.ObjectiveAssessment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ObjectiveAssessmentDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(ObjectiveAssessment objectiveAssessment);

    @Update
    void update(ObjectiveAssessment objectiveAssessment);

    @Delete
    void delete(ObjectiveAssessment objectiveAssessment);

    @Query("DELETE FROM objective_assessment")
    void deleteAllObjectiveAssessments();

    @Query("SELECT * FROM objective_assessment")
    List<ObjectiveAssessment> getAllObjectiveAssessments();

    @Query("SELECT * FROM objective_assessment WHERE courseID = :courseID")
    List<ObjectiveAssessment> getObjectiveAssessmentsByCourseID(final int courseID);

}
