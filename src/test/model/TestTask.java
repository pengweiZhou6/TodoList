package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTask {

    private Task t1, t2, t3,t4;
    private Task tt1;

    @BeforeEach
    void runBefore() {
        t1 = new Task("testToString");
        tt1 = new Task("testToString");
        t2 = new Task("idc");
        t3 = new Task("Description ## ");
        t4 = new Task("123");

    }

    @Test
    void testException(){
        t1.setStatus(Status.UP_NEXT);
        assertFalse(t1.equals(tt1));
        try {
            Task tt1 = new Task ("");
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }
        try {
            t1.containsTag("");
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }
        String s = null;
        try {
            t1.containsTag(s);
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }
        Tag tttt = null;
        try {
            t1.containsTag(tttt);
            fail("I was not expecting to reach this line of code!");
        } catch (NullArgumentException e) {

        }

        try {
            Task tt1 = new Task (null);
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }

        try {
            Task tt1 = new Task ("555");

        } catch (EmptyStringException e) {
            fail("I was not expecting to reach this line of code!");
        }

        try {
            t1.addTag("");
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }

        try {
            t1.addTag("");
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }

        try {
            t1.addTag("55");

        } catch (EmptyStringException e) {
            fail("I was not expecting to reach this line of code!");
        }

        try {
            t1.removeTag("");
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }

        try {
            t1.removeTag("");
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }

        try {
            t1.removeTag("55");

        } catch (EmptyStringException e) {
            fail("I was not expecting to reach this line of code!");
        }

        try {
            t1.setPriority(null);
            fail("I was not expecting to reach this line of code!");
        } catch (NullArgumentException e) {

        }

        try {
            Priority priority = new Priority(1);
            t1.setPriority(priority);

        } catch (NullArgumentException e) {
            fail("I was not expecting to reach this line of code!");
        }

        try {
            t1.setStatus(null);
            fail("I was not expecting to reach this line of code!");
        } catch (NullArgumentException e) {

        }

        try {
            t1.setStatus(Status.IN_PROGRESS);

        } catch (NullArgumentException e) {
            fail("I was not expecting to reach this line of code!");
        }

        try {
            t1.setDescription(null);
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }

        try {
            t1.setDescription("DAHJDKJ ## TAG1");

        } catch (EmptyStringException e) {
            fail("I was not expecting to reach this line of code!");
        }


        try {
            t1.setDescription("");
            fail("I was not expecting to reach this line of code!");
        } catch (EmptyStringException e) {

        }


    }

    @Test
    void testToString(){
        assertTrue(t1.equals(tt1));
        assertTrue(t1.equals(t1));
        assertFalse(t1.equals(t2));
        assertFalse(t1.equals(2));

        String sTest30= "\n{"+"\n\tDescription: Description " +
                "\n\tDue date: " +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"DEFAULT" +
                "\n\tTags:  "+"\n}" ;
        assertEquals(sTest30,t3.toString());

        Priority p1 = new Priority(1);
        t1.setPriority(p1);
        assertFalse(t1.equals(tt1));
        Priority ppp1 = new Priority(4);
        t1.setPriority(ppp1);
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2018,11,30,13,30);

        Date d1 = cal1.getTime();
        DueDate dd1 = new DueDate(d1);

        t1.setPriority(p1);
        assertEquals(p1,t1.getPriority());
        assertEquals("testToString",t1.getDescription());
        t1.setDueDate(dd1);
        assertEquals(dd1,t1.getDueDate());
        t1.setStatus(Status.TODO);
        assertEquals(t1.getStatus().getDescription(),t1.getStatus().toString());

        String sTest1= "\n{"+"\n\tDescription: testToString" +
                "\n\tDue date: 星期日 十二月 30 2018 01:30 下午" +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"IMPORTANT & URGENT" +
                "\n\tTags:  "+"\n}" ;

        t1.setPriority(ppp1);
        assertFalse(t1.equals(tt1));
        t1.setPriority(p1);
        assertEquals(sTest1,t1.toString());
        assertFalse(t1.equals(tt1));
        assertEquals(t4.getDescription(),"123");
        String sTest11= "\n{"+"\n\tDescription: 123" +
                "\n\tDue date: " +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"DEFAULT" +
                "\n\tTags:  "+"\n}" ;

        assertEquals(sTest11,t4.toString());



        assertEquals(0,t1.getTags().size());
        t1.addTag("Tag1");
        assertTrue(t1.containsTag("Tag1"));
        t1.addTag("Tag2");
        t1.addTag("Tag3");
        assertEquals(3,t1.getTags().size());

        String sTest2="\n{"+"\n\tDescription: testToString" +
                "\n\tDue date: 星期日 十二月 30 2018 01:30 下午" +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"IMPORTANT & URGENT" +
                "\n\tTags: #Tag2, #Tag1, #Tag3"+"\n}" ;

        assertEquals(sTest2,t1.toString());

        t1.addTag("Tag1");
        assertEquals(3,t1.getTags().size());
        String sTest3= "\n{"+"\n\tDescription: testToString" +
                "\n\tDue date: 星期日 十二月 30 2018 01:30 下午" +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"IMPORTANT & URGENT" +
                "\n\tTags: #Tag2, #Tag1, #Tag3"+"\n}" ;
        assertEquals(sTest3,t1.toString());

        t1.removeTag("Tag1");
        String sTest4= "\n{"+"\n\tDescription: testToString" +
                "\n\tDue date: 星期日 十二月 30 2018 01:30 下午" +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"IMPORTANT & URGENT" +
                "\n\tTags: #Tag2, #Tag3"+"\n}" ;
        assertEquals(2,t1.getTags().size());
        assertEquals(sTest4,t1.toString());

        t1.removeTag("Tag5");
        String sTest5= "\n{"+"\n\tDescription: testToString" +
                "\n\tDue date: 星期日 十二月 30 2018 01:30 下午" +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"IMPORTANT & URGENT" +
                "\n\tTags: #Tag2, #Tag3"+"\n}" ;
        assertEquals(2,t1.getTags().size());
        assertEquals(sTest5,t1.toString());

        t1.removeTag("Tag2");
        String sTest6= "\n{"+"\n\tDescription: testToString" +
                "\n\tDue date: 星期日 十二月 30 2018 01:30 下午" +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"IMPORTANT & URGENT" +
                "\n\tTags: #Tag3"+"\n}" ;
        assertEquals(1,t1.getTags().size());
        assertEquals(sTest6,t1.toString());

        t1.removeTag("Tag3");
        String sTest7= "\n{"+"\n\tDescription: testToString" +
                "\n\tDue date: 星期日 十二月 30 2018 01:30 下午" +
                "\n\tStatus: TODO" +
                "\n\tPriority: "+"IMPORTANT & URGENT" +
                "\n\tTags:  "+"\n}" ;
        assertEquals(0,t1.getTags().size());
        assertEquals(sTest7,t1.toString());

        t1.removeTag("Tag3");
        assertEquals(0,t1.getTags().size());
        assertEquals(sTest7,t1.toString());
    }


}