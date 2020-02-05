package com.skenons.med.api;
//package com.skenons.med.api;
//
//import java.util.List;
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
//import com.skenons.med.data.ExamType;
//import com.skenons.med.service.ExamTypeService;
//
//@RestController
//@RequestMapping("/examType")
//public class ExamTypeController {
//	@Autowired
//	private ExamTypeService examTypeService;
//
//	
//	//@PreAuthorize("hasRole('USER')")
//	@GetMapping()
//	public ResponseEntity<List<ExamType>> getAll(){
//		if(examTypeService.getAll() == null) {
//			return new ResponseEntity<List<ExamType>>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<List<ExamType>>(examTypeService.getAll(),HttpStatus.OK);
//	}
//	
//	//@PreAuthorize("hasRole('USER')")
//	@GetMapping(value = "/{id}")
//	public ResponseEntity<ExamType> getById(@PathVariable Long id){
//		if(examTypeService.getById(id) == null) {
//			return new ResponseEntity<ExamType>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<ExamType>(examTypeService.getById(id),HttpStatus.OK);
//	}
//	
//	@PostMapping()
//	public ResponseEntity<String> create(@RequestBody ExamType ExamType){
//		if(examTypeService.save(ExamType)) {
//			return new ResponseEntity<String>("Item is created!",HttpStatus.CREATED);
//		}else {
//			return new ResponseEntity<String>("Item already exist!",HttpStatus.CONFLICT);
//		}
//			
//	}
//	
//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<String> delete(@PathVariable Long id){
//		if(examTypeService.delete(id)) {
//			return new ResponseEntity<String>("Item is deleted!",HttpStatus.OK);
//		}else {
//			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	@PutMapping("/{id}")
//	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ExamType ExamType){
//		if(examTypeService.update(id,ExamType)) {
//			return new ResponseEntity<String>("Item is updated!",HttpStatus.OK);
//		}else {
//			return new ResponseEntity<String>("Item is not found!",HttpStatus.NOT_FOUND);
//		}
//	}
//}
