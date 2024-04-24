package org.example.ebookstore.services.interfaces;

public interface ScheduledTasksService {
    void getLatestFxRatesAndUpdateBookPrices();
    void backupDatabase();
}
