package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.ServiceRequestDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.repository.VehicleServiceRepository;
import edu.miu.cse.vsms.service.VehicleService;
import lombok.RequiredArgsConstructor;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleServiceRepository vehicleServiceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public VehicleServiceResponseDto assignService(ServiceRequestDto request) {
        // Write your code here

        VService vService = new VService(
                request.serviceName(),
                request.cost(),
                request.vehicleType(),
                fetchEmployee(request.employeeId())

        );

        VService savedVservice = vehicleServiceRepository.save(vService);
        return new VehicleServiceResponseDto(
                savedVservice.getId(),
                savedVservice.getServiceName(),
                savedVservice.getCost(),
                savedVservice.getVehicleType()
        );
    }

    private Employee fetchEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empoyee not found with id " + id));
    }
}
