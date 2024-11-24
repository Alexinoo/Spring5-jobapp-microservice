package com.embarkx.jobms.job.serviceimpl;


import com.embarkx.jobms.job.clients.CompanyClient;
import com.embarkx.jobms.job.clients.ReviewClient;
import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;
import com.embarkx.jobms.job.mapper.JobMapper;
import com.embarkx.jobms.job.model.Job;
import com.embarkx.jobms.job.repository.JobRepository;
import com.embarkx.jobms.job.service.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

    //private List<Job> jobs  = new ArrayList<>();
    // private Long id = 1L;
   // private static RestTemplate  restTemplate = new RestTemplate();
    private JobRepository jobRepository;

    // Create an object of CompanyClient
    private CompanyClient companyClient;

    // Create an object of ReviewClient
    private ReviewClient reviewClient;
    @Autowired
    RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,
                          ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    @CircuitBreaker(name = "companyBreaker", fallbackMethod="companyBreakerFallback")
    public List<JobDTO> findAll() {
       // return jobs;  ** managed by ArrayList **

      /* Fetch with RestTemplate class
        RestTemplate  restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://localhost:8081/companies/1", Company.class);
        System.out.println("Company name : "+ company.getName());
        System.out.println("Company id : "+ company.getId()); */

        /* Fetch With DTO */
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobWithCompanyDetailsDTOSList = new ArrayList<>();

        // Loop through the jobs
        // Create a new JobWithCompanyDetailsDTO object
        // Add a job to the new object
        // get the company from company microservice
        // Add the company to the new obj
        // Add the instance of the new obj to the List


        /* -- Using Streams -- **
        for (Job job: jobs) {
            JobWithCompanyDetailsDTO jobWithCompanyDetailsDTO = new JobWithCompanyDetailsDTO();
            jobWithCompanyDetailsDTO.setJob(job);
            Company company = restTemplate.getForObject("http://localhost:8081/companies/"+ job.getCompanyId(), Company.class);
            jobWithCompanyDetailsDTO.setCompany(company);
            jobWithCompanyDetailsDTOSList.add(jobWithCompanyDetailsDTO);
        }

        // return jobRepository.findAll();
       //  return jobWithCompanyDetailsDTOSList; */

        return jobs.stream()
                .map(this::convertTo)
                .collect(Collectors.toList());
    }
    
    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    private JobDTO convertTo(Job job) {
       // JobWithCompanyDetailsDTO jobWithCompanyDetailsDTO = new JobWithCompanyDetailsDTO();
       // jobWithCompanyDetailsDTO.setJob(job); - we have removed the setter for setJob(Job job)

        // using RestTemplate class
       // Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/"+ job.getCompanyId(), Company.class);


        // Using Feign client - CompanyClient
        Company company = companyClient.getCompany(job.getCompanyId());

        /* ** Using RestTemplate
        ResponseEntity<List<Review>> reviewResponse =
                restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {
                });
        List<Review> reviews = reviewResponse.getBody(); */


        // Using Feign client - ReviewClient
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());



        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job,company, reviews);
        //jobDTO.setCompany(company); - No longer needed since we're handling this functionality in the JobDTO class
        return jobDTO;
    }

    @Override
    public void createJob(Job newJob) {
       // newJob.setId(id++);
        //  jobs.add(newJob); ** managed by ArrayList **
        jobRepository.save(newJob);
    }

    // Updated return type to JobWithCompanyDTO both on service class also
    @Override
    public JobDTO findJobById(Long id) {
      /*  ** managed by ArrayList **
        for (Job job : jobs){

            if (id.equals(job.getId()))
                return job;
        }
        return null;
      */
        Job job = jobRepository.findById(id).orElse(null);
        return convertTo(job);

    }

    @Override
    public boolean updateJobById(Job updatedJob , Long id) {
         /*  ** managed by ArrayList **
        Job jobToUpdate = findJobById(id);
        if (jobToUpdate != null){
            jobToUpdate.setTitle(updatedJob.getTitle());
            jobToUpdate.setDescription(updatedJob.getDescription());
            jobToUpdate.setMinSalary(updatedJob.getMinSalary());
            jobToUpdate.setMaxSalary(updatedJob.getMaxSalary());
            jobToUpdate.setLocation(updatedJob.getLocation());
            return true;
        }
        return false; */
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()){
            Job jobToUpdate = jobOptional.get();
            jobToUpdate.setTitle(updatedJob.getTitle());
            jobToUpdate.setDescription(updatedJob.getDescription());
            jobToUpdate.setMinSalary(updatedJob.getMinSalary());
            jobToUpdate.setMaxSalary(updatedJob.getMaxSalary());
            jobToUpdate.setLocation(updatedJob.getLocation());
            jobRepository.save(jobToUpdate);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteJobById(Long id) {
        /*  ** managed by ArrayList **
        Iterator<Job> iterator = jobs.iterator();
        while (iterator.hasNext()){
            Job job = iterator.next();
            if (id.equals(job.getId())){
                iterator.remove();
                return true;
            }
        }
        return false; */
        try{
            jobRepository.deleteById(id);
            return true;
        }catch (Exception exc){
           return false;
        }

    }

}
