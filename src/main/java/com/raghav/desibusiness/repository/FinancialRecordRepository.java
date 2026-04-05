package com.raghav.desibusiness.repository;


import com.raghav.desibusiness.entity.FinancialRecord;
import com.raghav.desibusiness.entity.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type = 'INCOME'")
    Double getTotalIncome();
    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type = 'EXPENSE'")
    Double getTotalExpense();
    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r GROUP BY r.category")
    List<Object[]> getCategoryWiseTotals();
    List<FinancialRecord> findTop5ByOrderByDateDesc();
    @Query("""
    SELECT MONTH(r.date),
           SUM(CASE WHEN r.type = 'INCOME' THEN r.amount ELSE 0 END),
           SUM(CASE WHEN r.type = 'EXPENSE' THEN r.amount ELSE 0 END)
    FROM FinancialRecord r
    GROUP BY MONTH(r.date)
""")
    List<Object[]> getMonthlyIncomeExpense();

    List<FinancialRecord> findByCategory(String category);

    List<FinancialRecord> findByType(RecordType type);

    List<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
