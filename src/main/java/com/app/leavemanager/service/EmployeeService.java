package com.app.leavemanager.service;

import com.app.leavemanager.configurations.security.model.token.Token;
import com.app.leavemanager.configurations.security.model.token.TokenType;
import com.app.leavemanager.configurations.security.repository.TokenSpringRepository;
import com.app.leavemanager.configurations.security.repository.UserSpringRepository;
import com.app.leavemanager.configurations.security.service.JwtService;
import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.Role;
import com.app.leavemanager.domain.employee.user.User;
import com.app.leavemanager.dto.EmployeeDTO;
import com.app.leavemanager.dto.RegistrationEmployeeResponseDTO;
import com.app.leavemanager.repository.dao.EmployeeRepository;
import com.app.leavemanager.repository.spring.EmployeeSpringRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeSpringRepository employeeSpringRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenSpringRepository tokenSpringRepository;
    private final EmployeeRepository employeeRepository;
    private final UserSpringRepository userSpringRepository;
    @Value("${api.admin.email}")
    private String adminEmail;
    @Value("${api.admin.password}")
    private String adminPassword;

    @Transactional
    public Long createEmployee(EmployeeDTO employeeDTO) {

        return Employee.create(
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth(),
                employeeRepository
        ).getId();
    }

    private void revokeAllUserTokens(User user) {

        List<Token> allValidTokensByUser = tokenSpringRepository.findAllValidTokensByUser(user.getId());
        if (allValidTokensByUser.isEmpty()) {
            return;
        }
        allValidTokensByUser.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenSpringRepository.saveAll(allValidTokensByUser);
    }

    @Transactional
    public List<EmployeeDTO> getAllEmployee() {
        return employeeSpringRepository.findAll()
                .stream()
                .map(employee ->
                        EmployeeDTO.builder()
                                .id(employee.getId())
                                .firstname(employee.getFirstname())
                                .lastname(employee.getLastname())
                                .dateOfBirth(employee.getDateOfBirth())
                                .build()
                )
                .toList();
    }

    @Transactional
    public void updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {

        Employee employee = employeeSpringRepository.findById(employeeId).orElseThrow();
        employee.update(
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth(),
                employeeRepository
        );
    }

    @Transactional
    public void deleteEmployee(Long employeeId) {
        employeeSpringRepository.deleteById(employeeId);
    }

    @Transactional
    public EmployeeDTO getEmployeeById(Long employeeId) {
        return employeeSpringRepository.findById(employeeId)
                .map(employee -> EmployeeDTO.builder()
                        .id(employee.getId())
                        .firstname(employee.getFirstname())
                        .lastname(employee.getLastname())
                        .dateOfBirth(employee.getDateOfBirth())
                        .build())
                .orElseThrow();
    }

    @Transactional
    public RegistrationEmployeeResponseDTO createSuperAdmin(EmployeeDTO employeeDTO) {

        if (employeeRepository.isUserExistsByRole(Role.SUPER_ADMIN)) {
            throw new Error("Super admin is already created");
        }
        User user = User.builder()
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.SUPER_ADMIN)
                .build();
        User savedUser = userSpringRepository.save(user);

        String jwtToken = createUserToken(savedUser);
        Employee employee = Employee.createSuperAdmin(
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth(),
                user,
                employeeRepository
        );

        return RegistrationEmployeeResponseDTO.builder()
                .employeeId(employee.getId())
                .token(jwtToken)
                .build();
    }

    private String createUserToken(User user) {
        String jwtToken = jwtService.generateToken(user);
        Token token = Token.builder()
                .token(jwtToken)
                .type(TokenType.BEARER)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();
        revokeAllUserTokens(user);
        tokenSpringRepository.save(token);
        return jwtToken;
    }
}
