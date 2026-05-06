package com.hr.platform.kpi.service;

import com.hr.platform.kpi.model.KPIRecord;
import com.hr.platform.kpi.strategy.BonusSchemeType;

import java.util.List;
import java.util.UUID;


public interface EmployeeBonusSchemeService {
    List<KPIRecord> getKPIByEmployeeId(UUID employeeId);
    BonusSchemeType getBonusTypeByEmployeeId(UUID employeeId);
}
