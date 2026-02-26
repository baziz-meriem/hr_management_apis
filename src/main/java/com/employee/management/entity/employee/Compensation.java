package com.employee.management.entity.employee;

import com.employee.management.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;

/***
 *
 * Assumption: Compensation is representing the monthly compensation which varries based on bonuses ,raises,
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Compensation
        extends BaseEntity {

    @Id
    @UuidGenerator
    private String id;

    private BigDecimal salary;
    private BigDecimal bonus;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

}
