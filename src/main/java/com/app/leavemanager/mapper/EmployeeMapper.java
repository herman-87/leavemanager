package com.app.leavemanager.mapper;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.User;
import com.app.leavemanager.dto.EmployeeDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Component
public interface EmployeeMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "firstname")
    @Mapping(target = "lastname")
    @Mapping(target = "dateOfBirth")
    @Mapping(target = "activated", source = "activated")
    @Mapping(target = "email", source = "user", qualifiedByName = "getUserEmail")
    EmployeeDTO toDTO(Employee employee);

    @Named("getUserEmail")
    default String getUserEmail(User user) {
        return user.getEmail();
    }

}
