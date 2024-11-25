package com.embarkx.companyms.service;

import com.embarkx.companyms.dto.ReviewMessage;
import com.embarkx.companyms.model.Company;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    boolean updateCompany(Company company,Long id);

    void createCompany(Company newCompany);

    boolean deleteCompany(Long id);

    Company getCompany(Long id);

    void updateCompanyRating(ReviewMessage reviewMessage);
}
