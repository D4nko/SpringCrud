
package pl.kurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.exceptions.ImportStatusNotFoundException;
import pl.kurs.model.ImportStatus;
import pl.kurs.repository.ImportStatusRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImportStatusFacadeTest {

    @Mock
    private ImportStatusRepository importStatusRepository;

    @InjectMocks
    private ImportStatusFacade importStatusFacade;

    @Test
    void shouldSaveAndFlushImportStatus() {
        ImportStatus importStatus = new ImportStatus("testfile.csv");

        when(importStatusRepository.saveAndFlush(importStatus)).thenReturn(importStatus);

        ImportStatus savedStatus = importStatusFacade.saveAndFlush(importStatus);

        assertNotNull(savedStatus);
        verify(importStatusRepository).saveAndFlush(importStatus);
    }

    @Test
    void shouldUpdateToProcessing() {
        ImportStatus status = new ImportStatus("testfile.csv");
        status.setId(1);
        when(importStatusRepository.findById(1)).thenReturn(Optional.of(status));

        importStatusFacade.updateToProcessing(1);

        assertEquals(ImportStatus.Status.PROCESSING, status.getStatus());
        assertNotNull(status.getStartDate());
        verify(importStatusRepository).saveAndFlush(status);
    }

    @Test
    void shouldUpdateProgress() {
        ImportStatus status = new ImportStatus("testfile.csv");
        status.setId(1);
        when(importStatusRepository.findById(1)).thenReturn(Optional.of(status));

        importStatusFacade.updateProgress(1, 50);

        assertEquals(50, status.getProcessed());
        verify(importStatusRepository).saveAndFlush(status);
    }

    @Test
    void shouldUpdateToSuccess() {
        ImportStatus status = new ImportStatus("testfile.csv");
        status.setId(1);
        when(importStatusRepository.findById(1)).thenReturn(Optional.of(status));

        importStatusFacade.updateToSuccess(1, 100);

        assertEquals(ImportStatus.Status.SUCCESS, status.getStatus());
        assertEquals(100, status.getProcessed());
        assertNotNull(status.getFinishDate());
        verify(importStatusRepository).saveAndFlush(status);
    }

    @Test
    void shouldUpdateToFail() {
        ImportStatus status = new ImportStatus("testfile.csv");
        status.setId(1);
        when(importStatusRepository.findById(1)).thenReturn(Optional.of(status));

        importStatusFacade.updateToFail(1, "Error", 50);

        assertEquals(ImportStatus.Status.FAILED, status.getStatus());
        assertEquals("Error", status.getFailedReason());
        assertEquals(50, status.getProcessed());
        assertNotNull(status.getFinishDate());
        verify(importStatusRepository).saveAndFlush(status);
    }

    @Test
    void shouldThrowImportStatusNotFoundExceptionWhenStatusNotFound() {
        int invalidId = 999;
        when(importStatusRepository.findById(invalidId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ImportStatusNotFoundException.class, () -> {
            importStatusFacade.updateToProcessing(invalidId);
        });

        assertNotNull(exception);
    }





}
