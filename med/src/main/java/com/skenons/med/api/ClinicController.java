//package com.skenons.med.api;
//
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//import javax.xml.ws.spi.http.HttpContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.skenons.med.data.Clinic;
//import com.skenons.med.service.ClinicService;
//
//@RestController
//@RequestMapping("/clinic")
//public class ClinicController {
//	@Autowired
//	private ClinicService clinicService;
//	
//	HttpContext https;
//	HttpSession h;
//	
//	//@PreAuthorize("hasRole('USER')")
//	@GetMapping()
//	public ResponseEntity<List<Clinic>> getAll(){
//		if(clinicService.getAll() == null) {
//			return new ResponseEntity<List<Clinic>>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<List<Clinic>>(clinicService.getAll(),HttpStatus.OK);
//	}
//	
//	//@PreAuthorize("hasRole('USER')")
//	@GetMapping(value = "/{id}")
//	public ResponseEntity<Clinic> getById(@PathVariable Long id, HttpSession session){
//		if(clinicService.getById(id) == null) {
//			return new ResponseEntity<Clinic>(HttpStatus.NO_CONTENT);
//		}
//		session.getServletContext().setAttribute("S", clinicService.getById(id));
//		System.out.println(session.getAttribute("S"));
//		return new ResponseEntity<Clinic>(clinicService.getById(id),HttpStatus.OK);
//	}
//	
//	@PostMapping()
//	public ResponseEntity<String> create(@RequestBody Clinic Clinic){
//		if(clinicService.save(Clinic)) {
//			return new ResponseEntity<String>("Item is created!",HttpStatus.CREATED);
//		}else {
//			return new ResponseEntity<String>("Item already exist!",HttpStatus.CONFLICT);
//		}
//			
//	}
//	
//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<String> delete(@PathVariable Long id){
//		if(clinicService.delete(id)) {
//			return new ResponseEntity<String>("Item is deleted!",HttpStatus.OK);
//		}else {
//			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	@PutMapping("/{id}")
//	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Clinic Clinic){
//		if(clinicService.update(id,Clinic)) {
//			return new ResponseEntity<String>("Item is updated!",HttpStatus.OK);
//		}else {
//			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
//		}
//	}
//}
