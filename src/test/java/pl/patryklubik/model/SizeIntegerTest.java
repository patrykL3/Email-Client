package pl.patryklubik.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Create by Patryk Łubik on 16.01.2021.
 */

public class SizeIntegerTest {

    @Test
    public void testToStringValue(){
        SizeInteger sizeInteger1 = new SizeInteger(0);
        assertEquals(sizeInteger1.toString(), "0");

        SizeInteger sizeInteger2 = new SizeInteger(500);
        assertEquals(sizeInteger2.toString(), "500 B");

        SizeInteger sizeInteger3 = new SizeInteger(2050);
        assertEquals(sizeInteger3.toString(), "2 kB");

        SizeInteger sizeInteger4 = new SizeInteger(3348576);
        assertEquals(sizeInteger4.toString(), "3 MB");
    }

    @Test
    public void testCompareResults(){
        SizeInteger sizeInteger1 = new SizeInteger(1000);
        SizeInteger sizeInteger2 = new SizeInteger(2000);

        assertEquals(sizeInteger1.compareTo(sizeInteger2), -1);
        assertEquals(sizeInteger2.compareTo(sizeInteger1), 1);
        assertEquals(sizeInteger2.compareTo(sizeInteger2), 0);
    }
}
