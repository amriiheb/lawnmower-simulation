import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AdvancedBatchConfigurationTest {

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private StepBuilderFactory stepBuilderFactory;

    @Mock
    private Environment environment;

    @Mock
    private ResourceLoader resourceLoader;

    private com.mowitnow.mower.configurations.AdvancedBatchConfiguration configuration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        configuration = new com.mowitnow.mower.configurations.AdvancedBatchConfiguration(jobBuilderFactory, stepBuilderFactory, environment, resourceLoader);
    }

    @Test
    void singleLineItemReader_ValidInputFile_ReturnsReader() {
        when(environment.getProperty("input.file")).thenReturn("classpath:mowers.txt");
        // Assume resourceLoader.getResource() works correctly for simplicity.

        assertNotNull(configuration.singleLineItemReader());
    }

    @Test
    void singleLineItemReader_MissingInputFile_ThrowsException() {
        when(environment.getProperty("input.file")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> configuration.singleLineItemReader());
    }


}
