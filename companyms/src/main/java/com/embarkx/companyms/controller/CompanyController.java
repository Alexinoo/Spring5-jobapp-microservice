package com.embarkx.companyms.controller;


import com.embarkx.companyms.model.Company;
import com.embarkx.companyms.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long companyId){
        Company company = companyService.getCompany(companyId);
        if (company != null)
            return new ResponseEntity<>(company , HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company newCompany){
        companyService.createCompany(newCompany);
        return new ResponseEntity<>("New Company added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<String> updateCompanyById(@RequestBody Company updatedCompany ,
                                                    @PathVariable Long companyId){
        boolean isCompanyUpdated = companyService.updateCompany(updatedCompany,companyId);
        if (isCompanyUpdated)
            return new ResponseEntity<>("Company updated Successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long companyId){
        boolean isCompanyDeleted = companyService.deleteCompany(companyId);
        if (isCompanyDeleted)
            return new ResponseEntity<>("Company deleted Successfully",HttpStatus.OK);
        return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND);
    }
}
