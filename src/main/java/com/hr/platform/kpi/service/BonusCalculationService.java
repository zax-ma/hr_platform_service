package com.hr.platform.kpi.service;

import com.hr.platform.kpi.exception.BonusTypeNotFoundException;
import com.hr.platform.kpi.exception.KPICalculationException;
import com.hr.platform.kpi.model.KPIRecord;
import com.hr.platform.kpi.strategy.BonusSchemeStrategy;
import com.hr.platform.kpi.strategy.BonusSchemeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BonusCalculationService {
    private final BonusStrategyFactory bonusFactory;
    private final EmployeeBonusSchemeService employeeService;

    public long calculateBonus(UUID employeeId, List<KPIRecord> report) {
        if(employeeId == null || report.isEmpty()){
            throw new IllegalArgumentException("Номер сотрудника и отчет не могут быть пустыми");
        }
        BonusSchemeType bonusType = employeeService.getBonusTypeByEmployeeId(employeeId);
        if(bonusType == null){
            throw new BonusTypeNotFoundException("Тип премии для " + employeeId + " не найден");
        }
        BonusSchemeStrategy bonusSchemeStrategy = bonusFactory.getBonusStrategy(bonusType);
        long result = 0;
        try{
            result = bonusSchemeStrategy.calculate(report);
        } catch (RuntimeException e){
            throw new KPICalculationException("Ошибка в методе расчета премии:", e.getCause());
        }
        return result;
    }
}
