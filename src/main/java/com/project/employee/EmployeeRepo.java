package com.project.employee;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, Integer> {
	List<Employee> findByName(String Name);
	Optional<Employee> findByEmployeeId(String Id);

}
