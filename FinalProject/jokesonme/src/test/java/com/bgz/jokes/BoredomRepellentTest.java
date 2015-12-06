package com.bgz.jokes;

import org.junit.Test;
/**
 * Created by andrei on 06.12.2015.
 */
public class BoredomRepellentTest {

    @Test
    public void testRandomJoke() {
        BoredomRepellent br;

        br = new BoredomRepellent();

        for (int i = 0; i < 20; i++) {
            String joke=br.getNextJoke();

            assert joke!=null;
            assert !joke.equals("");
        }


    }
}
