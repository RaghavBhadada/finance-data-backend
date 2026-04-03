package com.raghav.desibusiness.service;


import com.raghav.desibusiness.entity.FinancialRecord;
import com.raghav.desibusiness.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    private final FinancialRecordRepository repository;

    public DashboardService(FinancialRecordRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> getDashboardSummary() {

        Double totalIncome = repository.getTotalIncome();
        Double totalExpense = repository.getTotalExpense();

        if (totalIncome == null) totalIncome = 0.0;
        if (totalExpense == null) totalExpense = 0.0;

        Double netBalance = totalIncome - totalExpense;

        List<Object[]> categoryData = repository.getCategoryWiseTotals();
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Object[] row : categoryData) {
            categoryTotals.put((String) row[0], (Double) row[1]);
        }

        List<FinancialRecord> recent = repository.findTop5ByOrderByDateDesc();

        Map<String, Object> response = new HashMap<>();
        response.put("totalIncome", totalIncome);
        response.put("totalExpense", totalExpense);
        response.put("netBalance", netBalance);
        response.put("categoryTotals", categoryTotals);
        response.put("recentActivity", recent);

        return response;
    }

    public List<Map<String, Object>> getMonthlyTrends() {

        List<Object[]> data = repository.getMonthlyIncomeExpense();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : data) {

            Integer month = ((Number) row[0]).intValue();
            Double income = ((Number) row[1]).doubleValue();
            Double expense = ((Number) row[2]).doubleValue();

            Double net = income - expense;

            Map<String, Object> map = new HashMap<>();
            map.put("month", month);
            map.put("income", income);
            map.put("expense", expense);
            map.put("net", net);

            result.add(map);
        }

        return result;
    }
}
