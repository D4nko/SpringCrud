package pl.kurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.model.ImportStatus;
import pl.kurs.repository.ImportStatusRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportStatusFacadeTest {

    @Mock
    private ImportStatusRepository importStatusRepository;

    @InjectMocks
    private ImportStatusFacade importStatusFacade;

    @Test
    void shouldSaveAndFlush() {
        ImportStatus importStatus = new ImportStatus();

        when(importStatusRepository.saveAndFlush(importStatus)).thenReturn(importStatus);
        ImportStatus savedImportStatus = importStatusFacade.saveAndFlush(importStatus);

        assertEquals(importStatus, savedImportStatus);
    }

    @Test
    void shouldFindByIdExistingIdAndReturnsImportStatus() {
        int id = 1;
        ImportStatus expectedImportStatus = new ImportStatus();

        when(importStatusRepository.findById(id)).thenReturn(Optional.of(expectedImportStatus));
        Optional<ImportStatus> foundImportStatus = importStatusFacade.findById(id);

        assertTrue(foundImportStatus.isPresent());
        assertEquals(expectedImportStatus, foundImportStatus.get());
    }


    @Test
    void shouldupdateToProcessing() {
        int id = 1;
        ImportStatus importStatus = new ImportStatus();
        when(importStatusRepository.findById(id)).thenReturn(Optional.of(importStatus));

        importStatusFacade.updateToProcessing(id);

        assertEquals(ImportStatus.Status.PROCESSING, importStatus.getStatus());
        assertNotNull(importStatus.getStartDate());
        verify(importStatusRepository, times(1)).saveAndFlush(importStatus);
    }

    @Test
    void shouldUpdateToProcessingExistingIdAndStatusUpdatedToProcessing() {
        int id = 1;
        ImportStatus importStatus = new ImportStatus();

        when(importStatusRepository.findById(id)).thenReturn(Optional.of(importStatus));

        importStatusFacade.updateToProcessing(id);

        verify(importStatusRepository).saveAndFlush(importStatus);
        assertEquals(ImportStatus.Status.PROCESSING, importStatus.getStatus());
        assertNotNull(importStatus.getStartDate());
    }

    @Test
    void shouldUpdateProgressExistingIdAndProgressUpdated() {
        int id = 1;
        int progress = 50;
        ImportStatus importStatus = new ImportStatus();

        when(importStatusRepository.findById(id)).thenReturn(Optional.of(importStatus));

        importStatusFacade.updateProgress(id, progress);

        verify(importStatusRepository).saveAndFlush(importStatus);
        assertEquals(progress, importStatus.getProcessed());
    }

    @Test
    void updateToSuccessExistingIdAndStatusUpdatedToSuccess() {
        int id = 1;
        int processed = 100;
        ImportStatus importStatus = new ImportStatus();

        when(importStatusRepository.findById(id)).thenReturn(Optional.of(importStatus));

        importStatusFacade.updateToSuccess(id, processed);

        verify(importStatusRepository).saveAndFlush(importStatus);
        assertEquals(ImportStatus.Status.SUCCESS, importStatus.getStatus());
        assertEquals(processed, importStatus.getProcessed());
        assertNotNull(importStatus.getFinishDate());
    }

    @Test
    void updateToFailExistingIdAndStatusUpdatedToFailed() {
        int id = 1;
        String message = "Failed to import";
        int processed = 50;
        ImportStatus importStatus = new ImportStatus();

        when(importStatusRepository.findById(id)).thenReturn(Optional.of(importStatus));

        importStatusFacade.updateToFail(id, message, processed);

        verify(importStatusRepository).saveAndFlush(importStatus);
        assertEquals(ImportStatus.Status.FAILED, importStatus.getStatus());
        assertEquals(message, importStatus.getFailedReason());
        assertEquals(processed, importStatus.getProcessed());
        assertNotNull(importStatus.getFinishDate());
    }


}
