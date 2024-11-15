package com.embarkx.companyms.service;

import com.embarkx.companyms.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    boolean updateCompany(Company company,Long id);

    void createCompany(Company newCompany);

    boolean deleteCompany(Long id);

    Company getCompany(Long id);
}
