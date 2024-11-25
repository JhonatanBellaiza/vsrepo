package edu.miu.cse.vsms;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cse.vsms.controller.EmployeeController;
import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.lang.runtime.ObjectMethods;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {objectMapper = new ObjectMapper();}

    @Test
    void testGetAllEmployees() {
        List<EmployeeResponseDto> employees = Arrays.asList(
                new EmployeeResponseDto(1L ,"John", "j@gmail.com", "9804032024", LocalDate.of(2023,2,3), Collections.emptyList()),
                new EmployeeResponseDto(2L, "Danna", "d@gmail.com", "45323454567", LocalDate.of(2021,3,1), Collections.emptyList())
        );
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<EmployeeResponseDto>> response = employeeController.getAllEmployees();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        verify(employeeService, times(1)).getAllEmployees();

    }

    @Test
    void testGetEmployeeId() {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto(1L,"John", "ema@john.co", "3123131",LocalDate.of(2023,2,2), Collections.emptyList());
        when(employeeService.getEmployeeById(1L)).thenReturn(employeeResponseDto);

        ResponseEntity<EmployeeResponseDto> response = employeeController.getEmployeeById(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().name()).isEqualTo("John");
        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void testCreateEmployee() {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("John", "john@gmail.com", "98036724567", LocalDate.of(2021,2,2));
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto(1L, "John", "john@gmail.com", "98036724567", LocalDate.of(2021,2,2), Collections.emptyList());
        when(employeeService.addEmployee(any())).thenReturn(employeeResponseDto);

        ResponseEntity<EmployeeResponseDto> response = employeeController.addEmployee(employeeRequestDto);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody().name()).isEqualTo("John");
        verify(employeeService, times(1)).addEmployee(any());
    }
}
