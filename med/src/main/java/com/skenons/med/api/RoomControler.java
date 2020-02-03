package com.skenons.med.api;

import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.skenons.med.data.Clinic;
import com.skenons.med.data.Room;
import com.skenons.med.service.RoomService;

@RestController
@RequestMapping("/clinic/{id}/room")
public class RoomControler {
	@Autowired
	private RoomService roomService;

	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping()
	public ResponseEntity<List<Room>> getAll( HttpSession session){
		if(roomService.getAll() == null) {
			return new ResponseEntity<List<Room>>(HttpStatus.NO_CONTENT);
		}
		System.out.println(((Clinic)session.getServletContext().getAttribute("S")).getId() + "SD");

		return new ResponseEntity<List<Room>>(roomService.getAll(),HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Room> getById(@PathVariable Long id, HttpSession session){
		if(roomService.getById(id) == null) {
			return new ResponseEntity<Room>(HttpStatus.NO_CONTENT);
		}
		System.out.println(session.getServletContext().getAttribute("S") + "SD");
		return new ResponseEntity<Room>(roomService.getById(id),HttpStatus.OK);
	}
	
	
	
	@PostMapping()
	public ResponseEntity<String> create(@RequestBody Room Room){
		if(roomService.save(Room)) {
			return new ResponseEntity<String>("Item is created!",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("Item already exist!",HttpStatus.CONFLICT);
		}
			
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		if(roomService.delete(id)) {
			return new ResponseEntity<String>("Item is deleted!",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Room Room){
		if(roomService.update(id,Room)) {
			return new ResponseEntity<String>("Item is updated!",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
		}
	}
}
