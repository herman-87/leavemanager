package com.app.leavemanager.service;

import com.app.leavemanager.configurations.security.model.token.Token;
import com.app.leavemanager.configurations.security.model.token.TokenType;
import com.app.leavemanager.configurations.security.repository.TokenSpringRepository;
import com.app.leavemanager.configurations.security.repository.UserSpringRepository;
import com.app.leavemanager.configurations.security.service.JwtService;
import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.EmployeeRepository;
import com.app.leavemanager.domain.employee.user.Scope;
import com.app.leavemanager.domain.employee.user.User;
import com.app.leavemanager.mapper.EmployeeMapper;
import com.app.leavemanager.repository.spring.EmployeeSpringRepository;
import com.leavemanager.openapi.model.EmployeeDTO;
import com.leavemanager.openapi.model.RegistrationDTO;
import com.leavemanager.openapi.model.RegistrationResponseDTO;
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
    private final EmployeeMapper employeeMapper;

    @Value("${default.super.admin.email}")
    private String defaultSuperAdminEmail;
    @Value("${default.super.admin.password}")
    private String defaultSuperAdminPassword;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;
    @Value("${default.employee.password}")
    private String defaultEmployeePassword;
    @Value("${default.email.suffix}")
    private String defaultEmailSuffix;

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
    public List<EmployeeDTO> getAllEmployees() {
        return employeeSpringRepository
                .findAll()
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    @Transactional
    public void updateEmployee(EmployeeDTO employeeDTO, Long employeeId) {

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
                .map(employeeMapper::toDTO)
                .orElseThrow();
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

    @Transactional
    public RegistrationResponseDTO createSuperAdmin(RegistrationDTO registrationDTO) {

        if (employeeRepository.existsByRole(Scope.SUPER_ADMIN)) {
            log.info("Super admin already created");
            return null;
        }
        User user = User.builder()
                .email(defaultSuperAdminEmail)
                .password(passwordEncoder.encode(defaultSuperAdminPassword))
                .role(Scope.SUPER_ADMIN)
                .build();
        User savedUser = userSpringRepository.save(user);

        String jwtToken = createUserToken(savedUser);
        Employee employee = Employee.createSuperAdmin(
                registrationDTO.getFirstname(),
                registrationDTO.getLastname(),
                registrationDTO.getDateOfBirth(),
                user,
                employeeRepository
        );

        return new RegistrationResponseDTO()
                .employeeId(employee.getId())
                .accessToken(jwtToken);
    }

    @Transactional
    public Long createAdmin(RegistrationDTO registrationDTO, String currentUsername) {

        Employee admin = getEmployeeByUsername(currentUsername);
        User user = createUser(registrationDTO, Scope.ADMIN);
        return admin.createEmployee(
                registrationDTO.getFirstname(),
                registrationDTO.getLastname(),
                registrationDTO.getDateOfBirth(),
                user,
                employeeRepository
        ).getId();
    }

    private User createUser(RegistrationDTO registrationDTO, Scope role) {
        User user = User.builder()
                .email(
                        Employee.generatedEmail(
                                registrationDTO.getFirstname(),
                                registrationDTO.getLastname(),
                                defaultEmailSuffix
                        )
                )
                .role(role)
                .build();
        if (Scope.EMPLOYEE.equals(role)) {
            user.setPassword(passwordEncoder.encode(defaultEmployeePassword));
        } else if (Scope.ADMIN.equals(role)) {
            user.setPassword(passwordEncoder.encode(defaultAdminPassword));
        }
        return user;
    }

    private Employee getEmployeeByUsername(String currentUsername) {
        return employeeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new Error("the current user is not present in database"));
    }

    @Transactional
    public Long createEmployee(RegistrationDTO registrationDTO, String currentUsername) {

        Employee admin = getEmployeeByUsername(currentUsername);
        User user = createUser(registrationDTO, Scope.EMPLOYEE);
        return admin.createEmployee(
                registrationDTO.getFirstname(),
                registrationDTO.getLastname(),
                registrationDTO.getDateOfBirth(),
                user,
                employeeRepository
        ).getId();
    }
}
