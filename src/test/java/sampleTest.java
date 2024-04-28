import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class sampleTest {

    @Test
    public void testSum(){
        int expected = 3;
        int actual = test.addition(1, 2);
        assertEquals(expected, actual);
    }
}
