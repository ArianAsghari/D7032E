package ltu;

import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ltu.PaymentImpl;
import ltu.ICalendar;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

public class PaymentTest {

    private PaymentImpl payment;

    private static class MockCalendar implements ICalendar {
        @Override
        public Date getDate() {
            return new Date();  // for now, just return the current date
        }
    }

    @Before
    public void setUp() throws IOException {
        payment = new PaymentImpl(new MockCalendar());
    }

    //--------------------------------------------------------------------------
    // Test ID: 101
    //--------------------------------------------------------------------------
    @Test
    public void test_Loan_and_subsidy_For_19() {
        int loanAmount = payment.getMonthlyAmount("20040101-1234", 0, 100, 100);
        assertEquals(0, loanAmount);
    }
    
    @Test
    public void test_Loan_and_subsidy_For_20() {
        int loanAmount = payment.getMonthlyAmount("20030101-1234", 0, 100, 100);
        assertEquals(9904, loanAmount);
    }
    //--------------------------------------------------------------------------
    // Test ID: 102
    //--------------------------------------------------------------------------
    @Test
    public void Subsidiary_55_Years_old() {
        int loanAmount = payment.getMonthlyAmount("19680101-1234", 0, 100, 100);
        assertEquals(2816, loanAmount);
    }
    public void Subsidiary_57_Years_old() {
        int loanAmount = payment.getMonthlyAmount("19660101-1234", 0, 100, 100);
        assertEquals(0, loanAmount);
    }
    //--------------------------------------------------------------------------
    // Test ID: 103
    //--------------------------------------------------------------------------
    @Test
    public void test_Loan_For_47_Years_old() {
        int loanAmount = payment.getMonthlyAmount("19680101-1234", 0, 100, 100);
        assertEquals(2816, loanAmount);
    }
    public void test_Loan_For_46_Years_old() {
        int loanAmount = payment.getMonthlyAmount("19660101-1234", 0, 100, 100);
        assertEquals(9904, loanAmount);
    }
    //--------------------------------------------------------------------------
    // Test ID: 201
    //--------------------------------------------------------------------------
    @Test
    public void test_studyrate_for_50procent() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 50, 100);
        assertEquals(4960, loanAmount);
    }
    public void test_studyrate_for_under_50procent() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 49, 100);
        assertEquals(0, loanAmount);
    }
    //--------------------------------------------------------------------------
    // Test ID: 202/203
    //--------------------------------------------------------------------------
    @Test
    public void test_for_fulltime() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 100, 100);
        assertEquals(9904, loanAmount);
    }
    public void test_less_then_fulltime() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 99, 100);
        assertEquals(4960, loanAmount);
    }
    //--------------------------------------------------------------------------
    // Test ID: 301
    //--------------------------------------------------------------------------
    @Test
    public void test_for_more_then_85813() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 85814, 100, 100);
        assertEquals(0, loanAmount);
    }
    public void test_for_85813() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 85813, 100, 100);
        assertEquals(9904, loanAmount);
    }
    //--------------------------------------------------------------------------
    // Test ID: 302
    //--------------------------------------------------------------------------
    @Test
    public void test_for_more_then_128722() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 128723, 99, 100);
        assertEquals(0, loanAmount);
    }
    public void test_for_128722() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 128722, 99, 100);
        assertEquals(4960, loanAmount);
    }
    //--------------------------------------------------------------------------
    // Test ID: 401
    //--------------------------------------------------------------------------
    @Test
    public void test_for_50procent_completion_rate() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 100, 50);
        assertEquals(9904, loanAmount);
    }
    public void test_for_49procent_completion_rate() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 100, 49);
        assertEquals(0, loanAmount);
    }
    //--------------------------------------------------------------------------
    // Test ID: 501
    //--------------------------------------------------------------------------
    @Test
    public void test_for_fulltime_student_loan() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 100, 100);
        int subsidiary= 2816;
        assertEquals(7088, loanAmount-subsidiary);
    }

    //--------------------------------------------------------------------------
    // Test ID: 502
    //--------------------------------------------------------------------------
    public void test_for_fulltime_student_subsidiary() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 100, 100);
        int loan= 7088;
        assertEquals(2816, loanAmount-loan);
    }
    
    //--------------------------------------------------------------------------
    // Test ID: 503
    //--------------------------------------------------------------------------
    @Test
    public void test_for_50time_student_loan() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 50, 100);
        int subsidiary= 1396;
        assertEquals(3564, loanAmount-subsidiary);
    }

    //--------------------------------------------------------------------------
    // Test ID: 504
    //--------------------------------------------------------------------------
    @Test
    public void test_for_50time_student_subsidiary() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 50, 100);
        int loan= 3564;
        assertEquals(1396, loanAmount-loan);
    }
    //--------------------------------------------------------------------------
    // Test ID: 505
    //--------------------------------------------------------------------------
    @Test
    public void test_for_fulltime_student_loan1() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 100, 100);
        int subsidiary= 2816;
        assertEquals(7088, loanAmount-subsidiary);
    }
    
    @Test
    public void test_for_halftime_student_loan() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 50, 100);
        int subsidiary= 1396;
        assertEquals(3564, loanAmount-subsidiary);
    }
    
    @Test
    public void test_for_notime_student_loan() {
        int loanAmount = payment.getMonthlyAmount("19970101-1234", 0, 0, 100);
        int subsidiary= 0;
        assertEquals(0, loanAmount-subsidiary);
    }
    
    
    //--------------------------------------------------------------------------
    // Test ID: 506
    //--------------------------------------------------------------------------
    @Test
    public void Fuultime_calender() {
        String calender = payment.getNextPaymentDay();
        assertEquals("20230929",calender);
    }
    
    
    //	Full_loan		7088
    //	Full_subsidy	2816
    //	Half_loan 		3564
    //	Half_subsidy 	1396
    



}
