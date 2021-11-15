package android.jmichalek.jaymichalek_capstone.All.Database;

import android.app.Application;
import android.jmichalek.jaymichalek_capstone.All.DAO.AssessmentDAO;
import android.jmichalek.jaymichalek_capstone.All.DAO.CourseDAO;
import android.jmichalek.jaymichalek_capstone.All.DAO.ObjectiveAssessmentDAO;
import android.jmichalek.jaymichalek_capstone.All.DAO.PerformanceAssessmentDAO;
import android.jmichalek.jaymichalek_capstone.All.DAO.TermDAO;
import android.jmichalek.jaymichalek_capstone.All.Entities.Assessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.Course;
import android.jmichalek.jaymichalek_capstone.All.Entities.ObjectiveAssessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.PerformanceAssessment;
import android.jmichalek.jaymichalek_capstone.All.Entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private PerformanceAssessmentDAO performanceAssessmentDAO;
    private ObjectiveAssessmentDAO objectiveAssessmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<PerformanceAssessment> mAllPerformances;
    private List<ObjectiveAssessment> mAllObjectives;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        performanceAssessmentDAO = db.performanceAssessmentDAO();
        objectiveAssessmentDAO = db.objectiveAssessmentDAO();
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

    public List<Course> getCoursesByTermId(final int termId) {
        databaseWriteExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getCoursesByTermID(termId);
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

    public List<PerformanceAssessment> getAllPerformanceAssessments() {
        databaseWriteExecutor.execute(() -> {
            mAllPerformances = performanceAssessmentDAO.getAllPerformanceAssessments();
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllPerformances;
    }

    public List<ObjectiveAssessment> getAllObjectiveAssessments() {
        databaseWriteExecutor.execute(() -> {
            mAllObjectives = objectiveAssessmentDAO.getAllObjectiveAssessments();
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllObjectives;
    }

    public List<Assessment> getAssessmentsByCourseID(final int courseId) {
        databaseWriteExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAssessmentsByCourseID(courseId);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public List<PerformanceAssessment> getPerformanceAssessmentsByCourseID(final int courseID) {
        databaseWriteExecutor.execute(() -> {
            mAllPerformances = performanceAssessmentDAO.getPerformanceAssessmentsByCourseID(courseID);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllPerformances;
    }

    public List<ObjectiveAssessment> getObjectiveAssessmentsByCourseID(final int courseID) {
        databaseWriteExecutor.execute(() -> {
            mAllObjectives = objectiveAssessmentDAO.getObjectiveAssessmentsByCourseID(courseID);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllObjectives;
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

    public void insert(PerformanceAssessment performanceAssessment) {
        databaseWriteExecutor.execute(() -> {
            performanceAssessmentDAO.insert(performanceAssessment);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(ObjectiveAssessment objectiveAssessment) {
        databaseWriteExecutor.execute(() -> {
            objectiveAssessmentDAO.insert(objectiveAssessment);
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

    public void update(PerformanceAssessment performanceAssessment) {
        databaseWriteExecutor.execute(() -> {
            performanceAssessmentDAO.update(performanceAssessment);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(ObjectiveAssessment objectiveAssessment) {
        databaseWriteExecutor.execute(() -> {
            objectiveAssessmentDAO.update(objectiveAssessment);
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

    public void delete(PerformanceAssessment performanceAssessment) {
        databaseWriteExecutor.execute(() -> {
            performanceAssessmentDAO.delete(performanceAssessment);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(ObjectiveAssessment objectiveAssessment) {
        databaseWriteExecutor.execute(() -> {
            objectiveAssessmentDAO.delete(objectiveAssessment);
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
