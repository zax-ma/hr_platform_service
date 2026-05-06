package com.hr.platform.kpi.strategy;

import com.hr.platform.kpi.configuration.SharedBonusSchemeConfig;
import com.hr.platform.kpi.model.IndicatorName;
import com.hr.platform.kpi.model.KPIRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SharedBonusSchemeStrategy implements BonusSchemeStrategy{
    private final SharedBonusSchemeConfig config;
    @Value("${bonus.shared.base}")
    private long baseAmount;

    @Override
    public long calculate(List<KPIRecord> report) {
        long sum = 0;
        for (KPIRecord kpiRecord : report) {
            IndicatorName name = kpiRecord.getName();
            double target = (double) config.getShared().getOrDefault(name, 0L) / 100;
            double actual = (double) kpiRecord.getActualValue() / 100;
            long bonus = (long) (baseAmount * target * actual);
            kpiRecord.setBonus(bonus);
            sum += bonus;
        }
        return sum;
    }

    @Override
    public BonusSchemeType getStrategyType() {
        return BonusSchemeType.SHARED;
    }
}
