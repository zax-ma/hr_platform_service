package com.hr.platform.kpi.configuration;

import com.hr.platform.kpi.model.IndicatorName;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "bonus.types")
@Data
public class ThresholdBonusSchemeConfig {
    private Map<IndicatorName, Long> threshold;
}
