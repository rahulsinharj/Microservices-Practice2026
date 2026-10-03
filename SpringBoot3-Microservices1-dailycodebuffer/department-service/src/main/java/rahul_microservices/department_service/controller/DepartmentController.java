package rahul_microservices.department_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rahul_microservices.department_service.client.EmployeeClient;
import rahul_microservices.department_service.model.Department;
import rahul_microservices.department_service.repository.DepartmentRepository;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public Department add(@RequestBody Department department) {
        System.out.println("Adding department: " + department);
        LOGGER.info("Adding department: {}", department);
        return departmentRepository.addDepartment(department);
    }

    @GetMapping
    public List<Department> findAll() {
        LOGGER.info("Finding all departments");
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable("id") Long id) {
        LOGGER.info("Finding department by id: {}", id);
        return departmentRepository.findById(id);
    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees() {
        LOGGER.info("Finding all departments with employees");
        List<Department> departments = departmentRepository.findAll();
        departments.forEach(
                department -> department.setEmployees(
                        employeeClient.findByDepartment(department.getId())));
        return departments;
    }
}
