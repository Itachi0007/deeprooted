package com.project.admin;

import com.project.employee.Employee;
import com.project.employee.EmployeeRepo;
import com.project.employee.ErrorBody;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("admin")
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private EmployeeRepo repo;

	// search employees
	@GetMapping(path = "/{field}/{value}")
	public ResponseEntity find(@PathVariable String field, @PathVariable String value) {
		if(field == "name") {
			List<Employee> employees = repo.findByName(value);
			if (employees.size() <= 0) {
				log.info("No employees found");
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.NOT_FOUND);
				body.setMessage("No employees found");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
		}
		else if(field =="id") {
			Employee employee = repo.findByEmployeeId(value).orElse(null);
			if (employee == null) {
				log.info("No employees found");
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.NOT_FOUND);
				body.setMessage("No employees found");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.NOT_FOUND);
			}

			log.info("Employee fetched successfully");
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid search criteria", HttpStatus.OK);
	}
}
