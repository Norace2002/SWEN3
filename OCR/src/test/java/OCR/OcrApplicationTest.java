package OCR;

import OCR.rabbitmq.RabbitMqSender;
import OCR.service.ElasticSearchService;
import OCR.service.MinIOService;
import OCR.service.OcrService;
import net.sourceforge.tess4j.Tesseract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OcrServiceTest {

    @InjectMocks
    private OcrService ocrService;

    @Mock
    private MinIOService minIOService;

    @Mock
    private ElasticSearchService elasticSearchService;

    @Mock
    private RabbitMqSender rabbitMqSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPerformOCR() throws Exception {
        // Arrange
        File mockFile = File.createTempFile("test", ".png");
        String expectedText = "Test OCR Result";

        Tesseract tesseractMock = mock(Tesseract.class);
        when(tesseractMock.doOCR(mockFile)).thenReturn(expectedText);

        OcrService spyOcrService = spy(ocrService);

        // Act
        String result = spyOcrService.performOCR(Arrays.asList(mockFile));

        // Assert
        assertEquals(expectedText + "\n", result);
        mockFile.delete();
    }

    @Test
    void testConvertPdfToImage() throws Exception {
        // Arrange
        byte[] pdfBytes = Files.readAllBytes(new File("OCR/src/test/resources/Test.pdf").toPath());

        // Act
        List<File> files = ocrService.convertPdfToImage(pdfBytes);

        // Assert
        assertNotNull(files);
        assertFalse(files.isEmpty());

        for (File file : files) {
            assertTrue(file.exists());
            file.delete(); // Clean up
        }
    }

    @Test
    void testReturnFileContent() throws Exception {
        // Arrange
        String fileIdentifier = "testFileId";
        byte[] mockPdfBytes = "mock pdf content".getBytes();
        String mockOcrResult = "Test OCR Text";

        when(minIOService.download(fileIdentifier)).thenReturn(mockPdfBytes);
        OcrService spyOcrService = spy(ocrService);

        doReturn(Arrays.asList(File.createTempFile("test", ".png"))).when(spyOcrService).convertPdfToImage(mockPdfBytes);
        doReturn(mockOcrResult).when(spyOcrService).performOCR(anyList());

        // Act
        spyOcrService.returnFileContent(fileIdentifier);

        // Assert
        verify(elasticSearchService).indexDocument(fileIdentifier, mockOcrResult);
        verify(rabbitMqSender).returnFileContent(fileIdentifier);
    }

    @Test
    void testReturnFileContentWithException() {
        // Arrange
        String fileIdentifier = "testFileId";
        when(minIOService.download(fileIdentifier)).thenThrow(new RuntimeException("Mock Exception"));

        // Act & Assert
        assertDoesNotThrow(() -> ocrService.returnFileContent(fileIdentifier));
        verifyNoInteractions(elasticSearchService);
        verifyNoInteractions(rabbitMqSender);
    }
}
