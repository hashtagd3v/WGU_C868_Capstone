package android.jmichalek.jaymichalek_capstone.All.Entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class TermTest {

    public static final int termID = 0;
    public static final String termName = "Term 1";
    public static final String termNameExceedMax = "Term 123456789101112131415161718192021222324252627282930";
    public static final String valid_startDate = "01/01/22";
    public static final String valid_endDate = "01/02/22";
    public static final String invalid_startDateSequence = "01/31/22";
    public static final String invalid_EndDateSequence = "01/20/22";
    public static final String invalid_startDateFormat = "";
    public static final String invalid_endDateFormat = "0/1/2";

    @Test
    public void testValidUserInput() {
        /* This test checks if user input are valid. Asserts TRUE that given input are valid.*/

        Date currentDateTime = Calendar.getInstance().getTime();
        String created_date = currentDateTime.toString();
        Term term = new Term(termID, termName, valid_startDate, valid_endDate, created_date);

        assertTrue(term.validUserInput(termName, valid_startDate, valid_endDate));
        System.out.println("Test 1: testValidUserInput() ---> Asserts TRUE that given input are valid.");

    }

    @Test
    public void testInvalidNameUserInput() {
        /* This test checks that user input are invalid due to character > MAX_LENGTH.
        *   Asserts FALSE that user input are valid.*/

        Date currentDateTime = Calendar.getInstance().getTime();
        String created_date = currentDateTime.toString();
        Term term = new Term(termID, termNameExceedMax, valid_startDate, valid_endDate, created_date);

        assertFalse(term.validUserInput(termNameExceedMax, valid_startDate, valid_endDate));
        System.out.println("Test 2: testInvalidNameUserInput() ---> Asserts FALSE that user input are valid.");

    }

    @Test
    public void testInvalidSequenceDateUserInput() {
        /* This test asserts that user input is invalid due to start date occurs after end date.
         *   Asserts FALSE that user input are valid.*/

        Date currentDateTime = Calendar.getInstance().getTime();
        String created_date = currentDateTime.toString();
        Term term = new Term(termID, termName, invalid_startDateSequence, invalid_EndDateSequence, created_date);

        assertFalse(term.validUserInput(termName, invalid_startDateSequence, invalid_EndDateSequence));
        System.out.println("Test 3: testInvalidSequenceDateUserInput() ---> Asserts FALSE that user input are valid.");

    }

    @Test
    public void testInvalidDateFormatUserInput() {
        /* This test checks that user input is invalid due to invalid start and end date format.
        *   Asserts FALSE that user input are valid.*/

        Date currentDateTime = Calendar.getInstance().getTime();
        String created_date = currentDateTime.toString();
        Term term = new Term(termID, termName, invalid_startDateFormat, invalid_endDateFormat, created_date);

        assertFalse(term.validUserInput(termName, invalid_startDateFormat, invalid_endDateFormat));
        System.out.println("Test 4: testInvalidDateFormatUserInput() ---> Asserts FALSE that user input are valid.");

    }

}