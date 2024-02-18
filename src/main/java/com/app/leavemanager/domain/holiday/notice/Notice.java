package com.app.leavemanager.domain.holiday.notice;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.holiday.Holiday;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "t_notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "c_type")
    private NoticeType type;
    @Column(name = "c_description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "c_holiday", referencedColumnName = "c_id")
    private Holiday holiday;
    @ManyToOne
    @JoinColumn(name = "c_employee", referencedColumnName = "c_id")
    private Employee createdBy;
}
