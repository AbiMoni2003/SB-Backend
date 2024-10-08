package com.example.backend.controller;

import com.example.backend.exception.NotfountException;
import com.example.backend.model.Employee;
import com.example.backend.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    public EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    //create employee API
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
    //get employee API
    @GetMapping("{id}")
   public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id){
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new NotfountException("Id Not Found"+ id));
               return ResponseEntity.ok(employee);
   }
   //update employee API
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id,@RequestBody Employee employeedetails){
        Employee updateEmployee=employeeRepository.findById(id)
                .orElseThrow(()->new NotfountException("Id not Found"+id));

        updateEmployee.setName(employeedetails.getName());
        updateEmployee.setAddress(employeedetails.getAddress());
        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }
    //delete employee API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Integer id){
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new NotfountException("Id is not found"+id));
                    employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
