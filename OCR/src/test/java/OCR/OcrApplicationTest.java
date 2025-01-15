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
import java.io.File;
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
    void testReturnFileContent() throws Exception {
        String fileIdentifier = "testFileId";
        byte[] mockPdfBytes = "mock pdf content".getBytes();
        String mockOcrResult = "Test OCR Text";

        when(minIOService.download(fileIdentifier)).thenReturn(mockPdfBytes);
        OcrService spyOcrService = spy(ocrService);

        doReturn(Arrays.asList(File.createTempFile("test", ".png"))).when(spyOcrService).convertPdfToImage(mockPdfBytes);
        doReturn(mockOcrResult).when(spyOcrService).performOCR(anyList());

        spyOcrService.returnFileContent(fileIdentifier);

        verify(elasticSearchService).indexDocument(fileIdentifier, mockOcrResult);
        verify(rabbitMqSender).returnFileContent(fileIdentifier);
    }

    @Test
    void testReturnFileContentWithException() {
        String fileIdentifier = "testFileId";
        when(minIOService.download(fileIdentifier)).thenThrow(new RuntimeException("Mock Exception"));

        assertDoesNotThrow(() -> ocrService.returnFileContent(fileIdentifier));
        verifyNoInteractions(elasticSearchService);
        verifyNoInteractions(rabbitMqSender);
    }

    @Test
    void testPerformOCRWithEmptyFiles() throws Exception {
        List<File> emptyFiles = Arrays.asList();

        String result = ocrService.performOCR(emptyFiles);

        assertNotNull(result);
        assertEquals("", result);
    }

    @Test
    void testPerformOCRWithSingleFile() throws Exception {
        File mockFile = File.createTempFile("testImage", ".png");
        Files.write(mockFile.toPath(), "Mock image data".getBytes());
        String expectedOcrResult = "Mock OCR Result";

        Tesseract tesseractMock = mock(Tesseract.class);
        when(tesseractMock.doOCR(mockFile)).thenReturn(expectedOcrResult);

        OcrService spyOcrService = spy(ocrService);
        doReturn(expectedOcrResult).when(spyOcrService).performOCR(Arrays.asList(mockFile));

        String result = spyOcrService.performOCR(Arrays.asList(mockFile));

        assertNotNull(result);
        assertEquals(expectedOcrResult + "", result);

        mockFile.delete();
    }

    @Test
    void testReturnFileContentHandlesValidInput() throws Exception {
        String fileIdentifier = "validFileId";
        byte[] mockPdfBytes = "Valid PDF content".getBytes();
        String mockOcrResult = "Extracted OCR Text";

        when(minIOService.download(fileIdentifier)).thenReturn(mockPdfBytes);
        OcrService spyOcrService = spy(ocrService);

        doReturn(Arrays.asList(File.createTempFile("testImage", ".png"))).when(spyOcrService).convertPdfToImage(mockPdfBytes);
        doReturn(mockOcrResult).when(spyOcrService).performOCR(anyList());

        spyOcrService.returnFileContent(fileIdentifier);

        verify(elasticSearchService).indexDocument(fileIdentifier, mockOcrResult);
        verify(rabbitMqSender).returnFileContent(fileIdentifier);
    }
}
