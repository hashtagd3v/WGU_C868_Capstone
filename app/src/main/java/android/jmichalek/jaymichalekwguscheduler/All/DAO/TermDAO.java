package android.jmichalek.jaymichalekwguscheduler.All.DAO;

import android.jmichalek.jaymichalekwguscheduler.All.Entities.Term;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM term_table")
    void deleteAllTerm();

    @Query("SELECT * FROM term_table ORDER BY termID ASC")
    List<Term> getAllTerms();

}
