package com.fullstack.service;

import com.fullstack.model.Employee;
import com.fullstack.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;



    public Employee signUp(Employee employee) {

        return employeeRepository.save(employee);
    }

    public boolean signIn(String empEmailId, String empPassword) {

        boolean success = false;

        Employee employee= employeeRepository.findByEmpEmailIdAndEmpPassword(empEmailId, empPassword);
        if(employee != null) {
            success = true;
        }
        return success;

    }

    public Optional<Employee> findById(int empId){

        return employeeRepository.findById(empId);
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee update(Employee employee){

        return employeeRepository.save(employee);
    }

    public void delete(int empId){
        employeeRepository.deleteById(empId);
    }

    public void deleteAll(){

        employeeRepository.deleteAll();
    }

    public List<Employee> findByName(String empName) {
        return employeeRepository.findByEmpName(empName);
    }

    public List<Employee> saveAll(List<Employee> employeeList) {
        return employeeRepository.saveAll(employeeList);
    }

    public void deleteById(int empId) {
        employeeRepository.deleteById(empId);
    }
}
