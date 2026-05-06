package com.hr.platform.kpi.strategy;

import com.hr.platform.kpi.configuration.ThresholdBonusSchemeConfig;
import com.hr.platform.kpi.model.IndicatorName;
import com.hr.platform.kpi.model.KPIRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ThresholdBonusSchemeStrategy implements BonusSchemeStrategy{
    private final ThresholdBonusSchemeConfig config;
    @Value("${bonus.threshold.base}")
    private long baseAmount;

    @Override
    public long calculate(List<KPIRecord> report) {
        long sum = 0;
        for (KPIRecord kpiRecord : report) {
            IndicatorName name = kpiRecord.getName();
            long target = config.getThreshold().getOrDefault(name,  0L);
            long actual = kpiRecord.getActualValue();
            if(actual >= target){
                sum += baseAmount;
            }
        }
        return sum;
    }

    @Override
    public BonusSchemeType getStrategyType() {
        return BonusSchemeType.THRESHOLD;
    }
}
