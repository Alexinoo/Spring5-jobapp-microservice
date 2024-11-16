package com.embarkx.jobms.job.service;



import com.embarkx.jobms.job.dto.JobWithCompanyDetailsDTO;
import com.embarkx.jobms.job.model.Job;

import java.util.List;

public interface JobService {

    // Updated return type from List<Job> to List<JobWithCompanyDetailsDTO>
    List<JobWithCompanyDetailsDTO> findAll();

    void createJob(Job job);

    Job findJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJobById(Job updateJob ,Long id);
}
