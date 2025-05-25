package com.fullstack.controller;

import com.fullstack.exception.RecordNotFoundException;
import com.fullstack.model.Employee;
import com.fullstack.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@Slf4j
public class EmployeeController {

    @Autowired

    private EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<Employee> signUp(@RequestBody Employee employee) {

        log.info("#########Trying to save data for Employee: " +employee.getEmpName());
        return ResponseEntity.ok(employeeService.signUp(employee));
    }

    @GetMapping("/signin/{empMailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empMailId, @PathVariable String empPassword) {

        return ResponseEntity.ok(employeeService.signIn(empMailId, empPassword));
    }

    @GetMapping("/findbyid")
    public ResponseEntity<Optional<Employee>> findById(@RequestParam int empId) {

        return ResponseEntity.ok(employeeService.findById(empId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/findbyname")
    public ResponseEntity<List<Employee>> findByName(@RequestParam String empName) {
        return ResponseEntity.ok(employeeService.findByName(empName));
    }

    /*@GetMapping("/findbycontactnumber/{empContactNumber}")
    public ResponseEntity <Employee> findByContactNumber(@PathVariable long empContactNumber) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp->emp.getEmpContactNumber() == empContactNumber).findFirst().get());
    }*/

    @GetMapping("/findbycontactnumber/{empContactNumber}")
    public ResponseEntity <Employee> findByContactNumber(@PathVariable long empContactNumber) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp->emp.getEmpContactNumber() == empContactNumber).toList().get(0));
    }

    @GetMapping("/findbyemailid/{empEmailId}")
    public ResponseEntity<Employee> findByEmailId(@PathVariable String empEmailId) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp->emp.getEmpEmailId() == empEmailId).toList().get(0));
    }

    @GetMapping("/findbyinput/{input}")
    public ResponseEntity<List<Employee>> findByInput(@PathVariable String input) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp ->emp.getEmpName().equals(input)
        || String.valueOf(emp.getEmpId()).equals(input)
        || String.valueOf(emp.getEmpContactNumber()).equals(input)
        || emp.getEmpEmailId().equals(input)
        || simpleDateFormat.format(emp.getEmpDOB()).equals(input)).toList());

       }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList());
    }

    @GetMapping("/sortbysalary")
    public ResponseEntity<List<Employee>> sortBySalary() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpSalary)).toList());
    }

    @GetMapping("/sortbydob")
    public ResponseEntity<List<Employee>> sortByDOB() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpDOB)).toList());
    }

    @GetMapping("/checkloaneligibility/{empId}")
    public ResponseEntity<String> checkLoanEligibility(@PathVariable int empId) {

        String msg="";

        Employee employee=employeeService.findById(empId).orElseThrow(()->new RecordNotFoundException("Employee ID is NOT Exist"));

        if (employee.getEmpSalary()>=50000.0){

            msg="Eligible for LOAN";
        }else{

            msg="NOT Eligible for LOAN";
        }


        return ResponseEntity.ok(msg);

    }
    @PostMapping("/updateid/{empId}")
    public ResponseEntity<Employee> update(@PathVariable int empId, @RequestBody Employee employee) {

        Employee employee1= employeeService.findById(empId).orElseThrow(()->new RecordNotFoundException("Employee ID is NOT Exist"));

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpPassword(employee.getEmpPassword());

        return ResponseEntity.ok(employeeService.update(employee));

    }

    @PatchMapping("/changeaddess/{empId}/{empAddress}")
    public ResponseEntity<Employee> changeAddress(@PathVariable int empId, @PathVariable String empAddress) {
        Employee employee=employeeService.findById(empId).orElseThrow(()->new RecordNotFoundException("Employee ID is NOT Exist"));
        employee.setEmpAddress(empAddress);
        return ResponseEntity.ok(employeeService.update(employee));

    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {
        employeeService.deleteById(empId);
        return ResponseEntity.ok("Data Deleted Succesfully");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        employeeService.deleteAll();
        return ResponseEntity.ok("All Data Deleted Succesfully");
    }




}
