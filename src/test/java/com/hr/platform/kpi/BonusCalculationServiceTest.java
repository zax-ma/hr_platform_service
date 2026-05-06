package com.hr.platform.kpi;

import com.hr.platform.kpi.exception.BonusTypeNotFoundException;
import com.hr.platform.kpi.exception.KPICalculationException;
import com.hr.platform.kpi.model.KPIRecord;
import com.hr.platform.kpi.service.BonusCalculationService;
import com.hr.platform.kpi.service.BonusStrategyFactory;
import com.hr.platform.kpi.service.EmployeeBonusSchemeServiceImpl;
import com.hr.platform.kpi.strategy.BonusSchemeStrategy;
import com.hr.platform.kpi.strategy.BonusSchemeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BonusCalculationServiceTest {
    @Mock
    private EmployeeBonusSchemeServiceImpl employeeBonusSchemeService;

    @Mock
    private BonusStrategyFactory bonusStrategyFactory;

    @Mock
    private BonusSchemeStrategy strategy;

    @InjectMocks
    private BonusCalculationService service;

    private UUID employeeId;
    private List<KPIRecord> report;

    @BeforeEach
    void setup(){
        employeeId = UUID.randomUUID();
        report = List.of(new KPIRecord());
    }

    @Test
    void shouldCalculateBonusBySharedType(){
        BonusSchemeType type = BonusSchemeType.SHARED;
        when(employeeBonusSchemeService.getBonusTypeByEmployeeId(employeeId)).thenReturn(type);
        when(bonusStrategyFactory.getBonusStrategy(type)).thenReturn(strategy);
        when(strategy.calculate(report)).thenReturn(88_000L);

        long result = service.calculateBonus(employeeId, report);

        assertEquals(88_000L, result);
        verify(strategy).calculate(report);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeIdIsNull(){
        assertThrows(IllegalArgumentException.class, () ->
                service.calculateBonus(null, report));
    }

    @Test
    void shouldThrowExceptionWhenBonusTypeNotFound(){
        when(employeeBonusSchemeService.getBonusTypeByEmployeeId(employeeId)).thenReturn(null);

        assertThrows(BonusTypeNotFoundException.class, () -> service.calculateBonus(employeeId, report));
    }

    @Test
    void shouldThrowExceptionWhenKPICalculationError(){
        BonusSchemeType type = BonusSchemeType.SHARED;
        when(employeeBonusSchemeService.getBonusTypeByEmployeeId(employeeId)).thenReturn(type);
        when(bonusStrategyFactory.getBonusStrategy(type)).thenReturn(strategy);
        when(strategy.calculate(report)).thenThrow(new RuntimeException("Error"));

        KPICalculationException e = assertThrows(
                KPICalculationException.class, () -> service.calculateBonus(employeeId, report)
        );

        assertEquals("Ошибка в методе расчета премии:", e.getMessage());
    }
}
