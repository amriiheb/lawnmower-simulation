import com.mowitnow.mower.batch.SingleLineItemReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import java.io.ByteArrayInputStream;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class SingleLineItemReaderTest {

    @Mock
    private Resource mockResource;

    private SingleLineItemReader itemReader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemReader = new SingleLineItemReader(mockResource);
    }

    @Test
    void read_WhenCalledFirstTime_ReturnsContent() throws Exception {
        String expectedContent = "Test content";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(expectedContent.getBytes());
        when(mockResource.getInputStream()).thenReturn(inputStream);

        String content = itemReader.read();
        assertEquals(expectedContent, content, "The read content should match the expected content.");
    }

    @Test
    void read_WhenCalledSubsequentTimes_ReturnsNull() throws Exception {
        String content = "Subsequent call content";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
        when(mockResource.getInputStream()).thenReturn(inputStream);

        assertNotNull(itemReader.read(), "First call to read() should return content.");
        assertNull(itemReader.read(), "Second call to read() should return null to indicate the end of the resource.");
    }

}
