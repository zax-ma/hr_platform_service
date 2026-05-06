package com.hr.platform.kpi.strategy;

import com.hr.platform.kpi.model.KPIRecord;

import java.util.List;

public interface BonusSchemeStrategy {
    long calculate(List<KPIRecord> report);

    default BonusSchemeType getStrategyType() {
        return null;
    }
}
