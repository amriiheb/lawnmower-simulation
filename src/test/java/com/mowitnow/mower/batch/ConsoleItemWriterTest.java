import com.mowitnow.mower.batch.ConsoleItemWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleItemWriterTest {

    private ConsoleItemWriter writer;

    @BeforeEach
    void setUp() {
        writer = new ConsoleItemWriter();
    }

    @Test
    void write_SingleItem_ListContainsItem() throws Exception {
        List<String> positions = Arrays.asList("1 2 N");
        writer.write(positions);

        assertEquals(1, writer.getMowerPositions().size());
        assertEquals("1 2 N", writer.getMowerPositions().get(0));
    }

    @Test
    void write_MultipleItems_ListContainsAllItems() throws Exception {
        List<String> positions = Arrays.asList("1 2 N", "3 4 E");
        writer.write(positions);

        assertEquals(2, writer.getMowerPositions().size());
        assertTrue(writer.getMowerPositions().containsAll(positions));
    }

    @Test
    void write_EmptyList_ListRemainsEmpty() throws Exception {
        writer.write(Arrays.asList());

        assertTrue(writer.getMowerPositions().isEmpty());
    }



}
