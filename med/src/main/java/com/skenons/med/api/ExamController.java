package com.skenons.med.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skenons.med.data.Exam;
import com.skenons.med.service.ExamService;

@RestController
@RequestMapping("/exam")
public class ExamController {
	@Autowired
	private ExamService examService;

	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping()
	public ResponseEntity<List<Exam>> getAll(){
		if(examService.getAll() == null) {
			return new ResponseEntity<List<Exam>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Exam>>(examService.getAll(),HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Exam> getById(@PathVariable Long id){
		if(examService.getById(id) == null) {
			return new ResponseEntity<Exam>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Exam>(examService.getById(id),HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<String> create(@RequestBody Exam Exam){
		if(examService.save(Exam)) {
			return new ResponseEntity<String>("Item is created!",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Item already exist!",HttpStatus.CONFLICT);
		}
			
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		if(examService.delete(id)) {
			return new ResponseEntity<String>("Item is deleted!",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Exam Exam){
		if(examService.update(id,Exam)) {
			return new ResponseEntity<String>("Item is updated!",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
		}
	}
}
