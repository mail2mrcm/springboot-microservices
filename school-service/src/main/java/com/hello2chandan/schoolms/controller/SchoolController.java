package com.hello2chandan.schoolms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hello2chandan.schoolms.model.Result;
import com.hello2chandan.schoolms.model.School;

@RestController
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepo;

    @GetMapping("/")
    public ResponseEntity<Result<List<School>>> getSchool() {
        List<School> schList = schoolRepo.findAll();
        Result<List<School>> result = new Result<>();
        if (schList.size() > 0) {
            result.setData(schList);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        result.setMessage("School not found");
        result.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable Integer id) {
        Optional<School> school = schoolRepo.findById(id);       
        if (school.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(school.get());
        }       
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(school.get());
    }

    @PostMapping("/")
    public ResponseEntity<Result<School>> addSchool(@RequestBody School school) {
        School newSchool = schoolRepo.saveAndFlush(school);
        Result<School> result = new Result<>();
        if (newSchool.getId() > 0) {
            result.setData(newSchool);
            result.setMessage("School created successfuly");
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        result.setMessage("Unable to create");
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<School>> updateSchool(@PathVariable Integer id, @RequestBody School school) {
        Optional<School> existingSchool = schoolRepo.findById(id);
        Result<School> result = new Result<>();
        if (existingSchool.isPresent()) {
            School existingSchoolDetails = existingSchool.get();
            existingSchoolDetails.setEstDate(school.getEstDate());
            existingSchoolDetails.setLocation(school.getLocation());
            existingSchoolDetails.setName(school.getName());
            existingSchoolDetails.setOwner(school.getName());
            schoolRepo.saveAndFlush(existingSchoolDetails);
            result.setMessage("School updated successfuly");
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        result.setMessage("Unable to update");
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<School>> deleteSchoolById(@PathVariable Integer id) {
        Optional<School> school = schoolRepo.findById(id);
        Result<School> result = new Result<>();
        if (school.isPresent()) {
            schoolRepo.deleteById(id);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        result.setMessage("School not found");
        result.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

}
