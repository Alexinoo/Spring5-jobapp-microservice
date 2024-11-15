package com.embarkx.jobms.job.serviceimpl;


import com.embarkx.jobms.job.model.Job;
import com.embarkx.jobms.job.repository.JobRepository;
import com.embarkx.jobms.job.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JobServiceImpl implements JobService {

    //private List<Job> jobs  = new ArrayList<>();
    // private Long id = 1L;
    private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
       // return jobs;  ** managed by ArrayList **
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job newJob) {
       // newJob.setId(id++);
        //  jobs.add(newJob); ** managed by ArrayList **
        jobRepository.save(newJob);
    }

    @Override
    public Job findJobById(Long id) {
      /*  ** managed by ArrayList **
        for (Job job : jobs){

            if (id.equals(job.getId()))
                return job;
        }
        return null;
      */
        return jobRepository.findById(id).orElse(null);

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
