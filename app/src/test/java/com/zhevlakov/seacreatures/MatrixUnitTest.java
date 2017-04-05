package com.zhevlakov.seacreatures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by aleksey on 24.03.17.
 */

public class MatrixUnitTest {
    @Test
    public void check_isNechet() throws Exception {

        assertEquals(true, Matrix.isNechet(1));
        assertEquals(true, Matrix.isNechet(3));
        assertEquals(true, Matrix.isNechet(5));
        assertEquals(false, Matrix.isNechet(2));
        assertEquals(false, Matrix.isNechet(4));
        assertEquals(false, Matrix.isNechet(6));
        assertEquals(false, Matrix.isNechet(8));
    }
}
