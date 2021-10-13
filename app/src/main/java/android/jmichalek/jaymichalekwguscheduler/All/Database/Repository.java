package android.jmichalek.jaymichalekwguscheduler.All.Database;

import android.app.Application;
import android.jmichalek.jaymichalekwguscheduler.All.DAO.AssessmentDAO;
import android.jmichalek.jaymichalekwguscheduler.All.DAO.CourseDAO;
import android.jmichalek.jaymichalekwguscheduler.All.DAO.TermDAO;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Assessment;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Course;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
    }

    public List<Term> getAllTerms() {
        databaseWriteExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(3000); //Change to 3000 if acting weird.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Course> getAllCourses() {
        databaseWriteExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Assessment> getAllAssessments() {
        databaseWriteExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Term term) {
        databaseWriteExecutor.execute(() -> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Course course) {
        databaseWriteExecutor.execute(() -> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Assessment assessment) {
        databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseWriteExecutor.execute(() -> {
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseWriteExecutor.execute(() -> {
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        databaseWriteExecutor.execute(() -> {
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseWriteExecutor.execute(() -> {
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
