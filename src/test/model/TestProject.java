package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {
    private Project p1, p2, p3, p4;
    private Task t1, t2, t3, t4,t5,t6,t7,t8;
    private Project pp1;
    private Task tt1;

    @BeforeEach
    void runBefore() {
        p1 = new Project("project1");
        p2 = new Project("project2");
        p3 = new Project("project3");
        p4 = new Project("project4");
        pp1 = new Project("project1");
        t1 = new Task("task1");
        tt1 = new Task("task1");
        t2 = new Task("task2");
        t3 = new Task("task3");
        t4 = new Task("task4");
        t5 = new Task("task5");
        t6 = new Task("task6");
        t7 = new Task("task7");
        t8 = new Task("task8");
        Priority pp1 = new Priority(1);
        Priority ppp1 = new Priority(1);
        Priority pp2 = new Priority(2);
        Priority pp3 = new Priority(3);
        Priority pp4 = new Priority(4);
        Calendar cal1 = Calendar.getInstance();
        Calendar call1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        Calendar cal4 = Calendar.getInstance();
        cal1.set(2018, 11, 30, 13, 30);
        call1.set(2018, 11, 30, 13, 30);
        cal2.set(2019, 11, 30, 13, 30);
        cal3.set(2020, 11, 30, 13, 30);
        cal4.set(2017, 11, 30, 13, 30);
        Date d1 = cal1.getTime();
        DueDate dd1 = new DueDate(d1);
        Date d11 = cal1.getTime();
        DueDate dd11 = new DueDate(d11);
        Date d2 = cal2.getTime();
        DueDate dd2 = new DueDate(d2);
        Date d3 = cal3.getTime();
        DueDate dd3 = new DueDate(d3);
        Date d4 = cal1.getTime();
        DueDate dd4 = new DueDate(d4);

        t1.setPriority(pp1);
        t1.setDueDate(dd1);
        t1.setStatus(Status.TODO);

        tt1.setPriority(ppp1);
        tt1.setDueDate(dd11);
        tt1.setStatus(Status.TODO);

        t2.setPriority(pp2);
        t2.setDueDate(dd2);
        t2.setStatus(Status.DONE);

        t3.setPriority(pp3);
        t3.setDueDate(dd3);
        t3.setStatus(Status.IN_PROGRESS);

        t4.setPriority(pp4);
        t4.setDueDate(dd4);
        t4.setStatus(Status.UP_NEXT);
    }
    @Test
    void testExample1() {
        p1.add(t1);
        p1.add(t2);
        p1.add(t3);
        assertEquals(0,p1.getProgress());
        t1.setProgress(100);
        assertEquals(33,p1.getProgress());
        t2.setProgress(50);
        t3.setProgress(25);
        assertEquals(58,p1.getProgress());
        p2.add(t4);
        p2.add(p1);
        assertEquals(29,p2.getProgress());
        p2.add(p3);
        assertEquals(19,p2.getProgress());

    }
    @Test
    void testExample2() {
        p1.add(t1);
        p1.add(t2);
        p1.add(t3);
        assertEquals(0,p1.getEstimatedTimeToComplete());
        t1.setEstimatedTimeToComplete(8);
        assertEquals(8,p1.getEstimatedTimeToComplete());
        t2.setEstimatedTimeToComplete(2);
        t3.setEstimatedTimeToComplete(10);
        assertEquals(20,p1.getEstimatedTimeToComplete());
        t4.setEstimatedTimeToComplete(4);
        p2.add(t4);
        p2.add(p1);
        assertEquals(24,p2.getEstimatedTimeToComplete());
    }
    @Test
    void testExample3() {
        Priority pp1 = new Priority(1);
        Priority pp2 = new Priority(2);
        Priority pp3 = new Priority(3);
        Priority pp4 = new Priority(4);
        t1.setPriority(pp3);
        t2.setPriority(pp2);
        t3.setPriority(pp1);
        t4.setPriority(pp2);
        t5.setPriority(pp3);
        t6.setPriority(pp4);
        t7.setPriority(pp1);
        t8.setPriority(pp4);

        p2.add(t4);
        p1.add(p2);
        p1.add(p3);
        p1.add(p4);
        p1.add(t1);
        p1.add(t2);
        p1.add(t3);
        p1.add(t4);
        p1.add(t5);
        p1.add(t6);
        p1.add(t7);
        p1.add(t8);


        for(Todo t : p1){
            System.out.println(t.getDescription());
        }
    }


    @Test
    void testException() {
        try {
            Project tt1 = new Project("");
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }

        try {
            Project tt1 = new Project("555");

        } catch (EmptyStringException e) {
            fail("I was not expecting to reach this line of code!");
        }

        try {
            String s = null;
            Project tt2 = new Project(s);
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }

        try {
            p1.add(t1);

        } catch (NullArgumentException e) {
            fail("I was not expecting to reach this line of code!");
        }


        try {
            p1.add(null);
            fail("I was not expecting to reach this line of code!");
        } catch (NullArgumentException e) {

        }


        try {
            p1.remove(t1);

        } catch (NullArgumentException e) {
            fail("I was not expecting to reach this line of code!");
        }

        try {
            p1.remove(null);
            fail("I was not expecting to reach this line of code!");
        } catch (NullArgumentException e) {

        }


        try {
            p1.contains(t1);

        } catch (NullArgumentException e) {
            fail("I was not expecting to reach this line of code!");
        }

        try {
            p1.contains(null);
            fail("I was not expecting to reach this line of code!");
        } catch (NullArgumentException e) {

        }

    }


    @Test
    void testTaskSet() {
        assertTrue(p1.equals(pp1));
        assertTrue(p1.equals(p1));
        assertFalse(p1.equals(t1));
        assertFalse(p1.equals(p2));
        assertTrue(p1.hashCode()==pp1.hashCode());
        assertEquals("project1", p1.getDescription());
        assertEquals("project2", p2.getDescription());
        assertEquals("project3", p3.getDescription());

        assertFalse(p1.isCompleted());
        assertEquals(0, p1.getNumberOfTasks());
        p1.add(t1);
        assertEquals(1, p1.getNumberOfTasks());
        p1.add(t1);
        assertEquals(1, p1.getNumberOfTasks());
        assertEquals(0, p1.getProgress());
        p1.add(t2);
        assertEquals(2, p1.getNumberOfTasks());
        p1.add(t3);
        assertEquals(3, p1.getNumberOfTasks());
        p1.add(t4);
        assertEquals(4, p1.getNumberOfTasks());
        p1.remove(t1);
        assertEquals(3, p1.getNumberOfTasks());
        p1.remove(t1);
        assertEquals(3, p1.getNumberOfTasks());
        p1.add(t1);
        assertEquals(4, p1.getNumberOfTasks());

//        assertEquals(25, p1.getProgress());
//        assertFalse(p1.isCompleted());
//        p1.removeLeaf(t1);
//        assertEquals(33, p1.getProgress());
//        p1.removeLeaf(t3);
//        assertEquals(50, p1.getProgress());
//        p1.removeLeaf(t4);
//        assertEquals(100, p1.getProgress());
//        assertTrue(p1.isCompleted());
//        assertEquals(100, p4.getProgress());
        p1.add(p1);
        assertEquals(4, p1.getNumberOfTasks());
    }
}