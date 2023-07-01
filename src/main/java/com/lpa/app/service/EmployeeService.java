package com.lpa.app.service;

import com.lpa.app.entities.EmployeeEntity;

import java.util.List;

public interface EmployeeService {

    List<EmployeeEntity> findAll();

    EmployeeEntity findById(Long id);

    void createEmployee(EmployeeEntity employeeEntity);

    void deleteEmployee(Long id);
}