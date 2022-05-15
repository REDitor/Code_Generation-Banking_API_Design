package io.swagger.repository;

import io.swagger.model.entity.Employee;
import io.swagger.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface employeeRepository extends JpaRepository<Employee, UUID> {


}
