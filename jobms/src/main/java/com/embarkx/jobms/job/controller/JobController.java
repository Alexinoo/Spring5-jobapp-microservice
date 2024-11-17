package com.embarkx.jobms.job.controller;

import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.model.Job;
import com.embarkx.jobms.job.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs") //- sets Base Url at the class level - remove at the method level
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    //@GetMapping("/jobs") - ** Replaced '/jobs' with @RequestMapping("/jobs") at the class level **
    // update return type from ResponseEntity<List<Job>> to ResponseEntity<List<JobWithCompanyDetailsDTO>>
    @GetMapping()
    public ResponseEntity<List<JobDTO>> getAllJobs(){
        return  ResponseEntity.ok(jobService.findAll());
    }

    //@PostMapping("/jobs") - ** Replaced '/jobs' with @RequestMapping("/jobs") at the class level **
    @PostMapping()
    public ResponseEntity<String> addJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job Added Successfully", HttpStatus.CREATED);
    }

   // @GetMapping("/jobs/{id}") - ** Replaced '/jobs' with @RequestMapping("/jobs") at the class level **
    // Updated Return type from ResponseEntity<Job> to ResponseEntity<ResponseEntity<Job>>
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        /*
        Job foundJob = jobService.findJobById(id);

        if (foundJob != null)
             return new ResponseEntity<>(foundJob, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); */

        // Using DTO pattern
        JobDTO jobWithCompanyDetailsDTO = jobService.findJobById(id);

        if (jobWithCompanyDetailsDTO != null)
            return new ResponseEntity<>(jobWithCompanyDetailsDTO, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    //@PutMapping("/jobs/{id}") - ** Replaced '/jobs' with @RequestMapping("/jobs") at the class level **
   // @RequestMapping(value = "/jobs/{id}", method = RequestMethod.PUT) ** At the method level **
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob( @RequestBody Job jobToUpdate, @PathVariable Long id){
        boolean isJobUpdated = jobService.updateJobById(jobToUpdate,id);
        if (isJobUpdated)
            return new ResponseEntity<>("Job Updated Successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

   // @DeleteMapping("/jobs/{id}") - ** Replaced '/jobs' with @RequestMapping("/jobs") at the class level **
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean isJobDeleted = jobService.deleteJobById(id);
        if (isJobDeleted)
            return new ResponseEntity<>("Job deleted successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
