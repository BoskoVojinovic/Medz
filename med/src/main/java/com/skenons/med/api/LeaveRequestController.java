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

import com.skenons.med.data.LeaveRequest;
import com.skenons.med.service.LeaveRequestService;
import com.skenons.med.service.LeaveRequestService;

@RestController
@RequestMapping("/leaveRequest")
public class LeaveRequestController {
	@Autowired
	private LeaveRequestService leaveRequestService;

	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping()
	public ResponseEntity<List<LeaveRequest>> getAll(){
		if(leaveRequestService.getAll() == null) {
			return new ResponseEntity<List<LeaveRequest>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<LeaveRequest>>(leaveRequestService.getAll(),HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<LeaveRequest> getById(@PathVariable Long id){
		if(leaveRequestService.getById(id) == null) {
			return new ResponseEntity<LeaveRequest>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<LeaveRequest>(leaveRequestService.getById(id),HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<String> create(@RequestBody LeaveRequest LeaveRequest){
		if(leaveRequestService.save(LeaveRequest)) {
			return new ResponseEntity<String>("Item is created!",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Item already exist!",HttpStatus.CONFLICT);
		}
			
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		if(leaveRequestService.delete(id)) {
			return new ResponseEntity<String>("Item is deleted!",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody LeaveRequest LeaveRequest){
		if(leaveRequestService.update(id,LeaveRequest)) {
			return new ResponseEntity<String>("Item is updated!",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
		}
	}
}
