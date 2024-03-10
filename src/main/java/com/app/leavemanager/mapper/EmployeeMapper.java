package com.app.leavemanager.mapper;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.User;
import com.leavemanager.openapi.model.EmployeeDTO;
import com.leavemanager.openapi.model.RoleDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                CommonMapper.class
        }
)
@Component
public interface EmployeeMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "firstname")
    @Mapping(target = "lastname")
    @Mapping(target = "dateOfBirth")
    @Mapping(target = "isActivated", source = "activated")
    @Mapping(target = "email", source = "user", qualifiedByName = "getUserEmail")
    @Mapping(target = "role", source = "user", qualifiedByName = "getUserRole")
    EmployeeDTO toDTO(Employee employee);

    @Named("getUserEmail")
    default String getUserEmail(User user) {
        return user.getEmail();
    }

    @Named("getUserRole")
    default RoleDTO getUserRole(User user) {
        return RoleDTO.fromValue(user.getRole().name());
    }
}
