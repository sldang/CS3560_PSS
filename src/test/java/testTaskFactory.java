import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testTaskFactory {

    @Test
    public void testTranslation(){
        String expected = "RecurringTask";
        String actual = TaskFactory.getTranslation("Class");
        assertEquals(expected, actual);

        expected = "TransientTask";
        actual = TaskFactory.getTranslation("Shopping");
        assertEquals(expected, actual);

        expected = "AntiTask";
        actual = TaskFactory.getTranslation("Cancellation");
        assertEquals(expected, actual);
    }
}
