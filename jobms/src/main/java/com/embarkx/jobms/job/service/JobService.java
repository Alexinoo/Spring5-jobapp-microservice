package com.embarkx.jobms.job.service;



import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.model.Job;

import java.util.List;

public interface JobService {

    // Updated return type from List<Job> to List<JobWithCompanyDetailsDTO>
    List<JobDTO> findAll();

    void createJob(Job job);



    // Updated return type from Job to JobWithCompanyDetailsDTO
    JobDTO findJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJobById(Job updateJob ,Long id);
}
