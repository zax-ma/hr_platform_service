package com.hr.platform.kpi.service;

import com.hr.platform.kpi.strategy.BonusSchemeStrategy;
import com.hr.platform.kpi.strategy.BonusSchemeType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BonusStrategyFactory {
    private final Map<BonusSchemeType, BonusSchemeStrategy> strategies;

    public BonusStrategyFactory(List<BonusSchemeStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(BonusSchemeStrategy::getStrategyType,
                            strategy -> strategy));
    }

    public BonusSchemeStrategy getBonusStrategy(BonusSchemeType type){
        return strategies.getOrDefault(type, events -> 0L);
    }
}
