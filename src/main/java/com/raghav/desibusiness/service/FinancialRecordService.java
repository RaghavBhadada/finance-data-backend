package com.raghav.desibusiness.service;

import com.raghav.desibusiness.dto.FinancialRecordRequestDto;
import com.raghav.desibusiness.entity.*;
import com.raghav.desibusiness.repository.FinancialRecordRepository;
import com.raghav.desibusiness.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public FinancialRecordService(FinancialRecordRepository recordRepository,
                                  UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }


    public void createRecord(FinancialRecordRequestDto dto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FinancialRecord record = FinancialRecord.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .category(dto.getCategory())
                .date(dto.getDate())
                .description(dto.getDescription())
                .user(user)
                .build();

        recordRepository.save(record);
    }


    public List<FinancialRecord> getAllRecords() {
        return recordRepository.findAll();
    }


    public void updateRecord(Long id, FinancialRecordRequestDto dto) {

        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setAmount(dto.getAmount());
        record.setCategory(dto.getCategory());
        record.setType(dto.getType());
        record.setDate(dto.getDate());
        record.setDescription(dto.getDescription());

        recordRepository.save(record);
    }

    public void deleteRecord(Long id) {

        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        recordRepository.delete(record);
    }
}
