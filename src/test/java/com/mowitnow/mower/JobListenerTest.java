package com.mowitnow.mower;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

import static org.mockito.Mockito.*;

public class JobListenerTest {

    private JobCompletionNotificationListener listener;
    private JobExecution jobExecution;

    @BeforeEach
    public void setUp() {
        listener = new JobCompletionNotificationListener();
        jobExecution = mock(JobExecution.class);
        JobInstance jobInstance = new JobInstance(1L, "testJob");
        when(jobExecution.getJobInstance()).thenReturn(jobInstance);
    }

    @Test
    public void beforeJob() {
        listener.beforeJob(jobExecution);
        verify(jobExecution).getJobInstance();
    }

    @Test
    public void afterJobCompleted() {
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        listener.afterJob(jobExecution);
        verify(jobExecution).getStatus();
    }

    @Test
    public void afterJobFailed() {
        when(jobExecution.getStatus()).thenReturn(BatchStatus.FAILED);
        listener.afterJob(jobExecution);
        verify(jobExecution, times(2)).getStatus();
    }
}