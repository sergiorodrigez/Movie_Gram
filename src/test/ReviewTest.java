package test;

import main.java.domain.Review;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {

    private Review review;

    @Before
    public void setUp() throws Exception {
        review = new Review("sergio", 180, "La mejor serie de la historia", 5);
    }

    @Test
    public void getUser() {
        String user = review.getUser();
        assertEquals("sergio", user);
    }

    @Test
    public void getId_p() {
        int id_p = review.getId_p();
        assertEquals(180, id_p);
    }

    @Test
    public void getVal() {
        int val = review.getVal();
        assertEquals(5, val);
    }

    @Test
    public void getComent() {
        String coment = review.getComent();
        assertEquals("La mejor serie de la historia", coment);
    }
}