package com.hr.platform.kpi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KPIRecord {
    UUID id;
    @Enumerated(EnumType.STRING)
    IndicatorName name;
    long actualValue;
    long bonus;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KPIRecord kpiRecord = (KPIRecord) o;
        return Double.compare(actualValue, kpiRecord.actualValue) == 0 && Objects.equals(id, kpiRecord.id) && name == kpiRecord.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, actualValue);
    }

    @Override
    public String toString() {
        return "KPIRecord{" +
                "id=" + id +
                ", name=" + name +
                ", actualValue=" + actualValue +
                ", bonus=" + bonus +
                '}';
    }
}
