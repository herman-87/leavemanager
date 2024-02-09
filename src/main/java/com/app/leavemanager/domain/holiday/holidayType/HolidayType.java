package com.app.leavemanager.domain.holiday.holidayType;


import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.repository.dao.HolidayRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "t_holiday_config")
public class HolidayType {

    @Id
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_name")
    private String name;
    @Column(name = "c_description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "c_created_by")
    private Employee createdBy;
    @OneToMany(mappedBy = "type")
    private List<Holiday> holidays;

    public void update(String name,
                       String description,
                       HolidayRepository holidayRepository) {
        this.name = name;
        this.description = description;
        holidayRepository.save(this);
    }
}
