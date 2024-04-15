import com.mowitnow.mower.batch.MowerSequenceProcessor;
import com.mowitnow.mower.exceptions.FileFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class MowerSequenceProcessorTest {

    private MowerSequenceProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new MowerSequenceProcessor();
    }

    @Test
    void process_ValidInput_ReturnsCorrectPosition() throws Exception {
        String input = "5 5 1 2 N GAGAGAGAA";
        String expected = "1 3 N";
        assertEquals(expected, processor.process(input));
    }

    @Test
    void process_InvalidInput_ThrowsFileFormatException() {
        String input = "invalid input";
        assertThrows(FileFormatException.class, () -> processor.process(input));
    }

    @Test
    void process_MovementWithinBounds_ReturnsUpdatedPosition() throws Exception {
        String input = "5 5 1 2 N AAAAA";
        String expected = "1 5 N"; // Assuming the lawn's upper limit prevents further movement
        assertEquals(expected, processor.process(input));
    }

    @Test
    void process_RotationInstructions_ReturnsCorrectOrientation() throws Exception {
        String input = "5 5 1 2 N DADADAD";
        String expected = "1 1 N";
        assertEquals(expected, processor.process(input));
    }


}
