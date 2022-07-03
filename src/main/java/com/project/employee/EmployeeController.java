package com.project.employee;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("engineer")
public class EmployeeController {
	
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	
	@Autowired
	private EmployeeRepo repo;

	// GET all employees
	@GetMapping(path = "/all")
	public ResponseEntity getAll() {
		log.trace("Fetching all employees");
		List<Employee> employees = repo.findAll();
		// check if no employees found
		if (employees.size() <= 0) {
			log.info("No employees found");
			ErrorBody body = new ErrorBody();
			body.setStatusCode(HttpStatus.NOT_FOUND);
			body.setMessage("No employees found");
			return new ResponseEntity<ErrorBody>(body, HttpStatus.NOT_FOUND);
		}
		log.info("Employees fetched successfully");
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	// ADD new employee
	@PostMapping(path = "/add-profile")
	public ResponseEntity addNew(@RequestBody Employee employee) throws Exception {
		try {
			log.trace("Creating new employee");
			// validate details
			if(employee.getName().length() > 30) {
				log.info("Name must be less than 30");
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.BAD_REQUEST);
				body.setMessage("Name must be less than 30");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.NOT_FOUND);
			}
			if(employee.getName().length() < 5) {
				log.info("Name must be more than 5");
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.BAD_REQUEST);
				body.setMessage("Name must be more than 5");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.NOT_FOUND);
			}

			HashMap<String, Integer> skills = employee.getSkills();
			AtomicReference<Boolean> flag = new AtomicReference<>(true);
			skills.forEach((k, v) -> {
				// skills must lie between 0-20
				if(v > 20 || v < 0) {
					flag.set(false);
					return;
				}
			});
			if(!flag.get()) {
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.BAD_REQUEST);
				body.setMessage("Skills must lie between 0-20");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.BAD_REQUEST);
			}

			// validate email
			String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
			//Compile regular expression to get the pattern
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(employee.getEmail());
			if(!matcher.matches()) {
				log.error("Invalid email");
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.BAD_REQUEST);
				body.setMessage("Invalid E-mail");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.BAD_REQUEST);
			}

			// validate phone
			Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
			Matcher match = ptrn.matcher(employee.getMobile());
			if(!match.matches()) {
				log.error("Invalid phone number");
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.BAD_REQUEST);
				body.setMessage("Invalid phone number");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.BAD_REQUEST);
			}

			// create employee
			employee.setCreatedAt(LocalDateTime.now());
			Employee et = repo.save(employee);

			return new ResponseEntity(et, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Something went wrong.");
			return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Update employee detail
	@PutMapping("/update-profile/{employeeId}")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable String employeeId) throws NotFoundException {
		try {
			log.trace("Updating employee details");
			Employee existEmployee = repo.findByEmployeeId(employeeId).orElse(null);
			// if no employee found with give ID
			if(existEmployee == null) {
				log.error("Employee not found with given ID: {} ", employeeId);
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.NOT_FOUND);
				body.setMessage("Employee not found with given ID - "+ employeeId);
				return new ResponseEntity<ErrorBody>(body, HttpStatus.NOT_FOUND);
			}

			// only edit after 10 days
			if(!existEmployee.getCreatedAt().isBefore(LocalDateTime.now().minusDays(10))) {
				log.error("Please wait 10 days to edit your details ");
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.BAD_REQUEST);
				body.setMessage("Please wait 10 days to edit your details ");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.BAD_REQUEST);
			}

			// update skills
			HashMap<String, Integer> skills = employee.getSkills();
			AtomicReference<Boolean> flag = new AtomicReference<>(true);
			skills.forEach((k, v) -> {
				// skills must lie between 0-20
				if(v > 20 || v < 0) {
					flag.set(false);
					return;
				}
				else {existEmployee.getSkills().put(k, v);}
			});
			if(!flag.get()) {
				ErrorBody body = new ErrorBody();
				body.setStatusCode(HttpStatus.BAD_REQUEST);
				body.setMessage("Skills must lie between 0-20");
				return new ResponseEntity<ErrorBody>(body, HttpStatus.BAD_REQUEST);
			}

			existEmployee.setUpdatedAt(LocalDateTime.now());
			existEmployee.setUserId(employee.getUserId());
			log.info("Employee updated successfully");
			repo.deleteByUserId(employee.getUserId());
			repo.save(existEmployee);
			return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
