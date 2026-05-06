package com.hr.platform.kpi.service;

import com.hr.platform.kpi.model.KPIRecord;
import com.hr.platform.kpi.strategy.BonusSchemeType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeBonusSchemeServiceImpl implements EmployeeBonusSchemeService {
    @Override
    public List<KPIRecord> getKPIByEmployeeId(UUID employeeId) {
        return List.of();
    }

    @Override
    public BonusSchemeType getBonusTypeByEmployeeId(UUID employeeId) {
        return null;// TODO реализация с БД
    }
}
