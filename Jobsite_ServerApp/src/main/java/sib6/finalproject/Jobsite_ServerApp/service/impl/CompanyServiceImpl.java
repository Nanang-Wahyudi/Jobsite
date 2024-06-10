package sib6.finalproject.Jobsite_ServerApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sib6.finalproject.Jobsite_ServerApp.entity.Company;
import sib6.finalproject.Jobsite_ServerApp.entity.User;
import sib6.finalproject.Jobsite_ServerApp.model.request.UpdateCompanyDetailRequest;
import sib6.finalproject.Jobsite_ServerApp.repository.CompanyRepository;
import sib6.finalproject.Jobsite_ServerApp.repository.UserRepository;
import sib6.finalproject.Jobsite_ServerApp.service.CompanyService;
import sib6.finalproject.Jobsite_ServerApp.service.media.CloudinaryService;

import java.io.IOException;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public String updateCompanyDetail(String username, UpdateCompanyDetailRequest companyDetailRequest) {
        String pictureUrl = null, bannerUrl = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with Username: " + username));
        Company company = user.getCompany();

        if (company == null) {
            company = new Company();
            company.setUser(user);
        }

        MultipartFile pictureFile = companyDetailRequest.getPicture();
        if (pictureFile != null && !pictureFile.isEmpty()) {
            if (!isImageFile(pictureFile)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type for picture. Only image files are allowed.");
            }
            try {
                if (company.getPicture() != null) cloudinaryService.deleteFile(company.getPicture());
                pictureUrl = cloudinaryService.uploadFile(pictureFile);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading picture: " + e.getMessage());
            }
        }

        MultipartFile bannerFile = companyDetailRequest.getBanner();
        if (bannerFile != null && !bannerFile.isEmpty()) {
            if (!isImageFile(bannerFile)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type for banner. Only image files are allowed.");
            }
            try {
                if (company.getBanner() != null) cloudinaryService.deleteFile(company.getBanner());
                bannerUrl = cloudinaryService.uploadFile(bannerFile);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading banner: " + e.getMessage());
            }
        }

        company.setName(companyDetailRequest.getName() != null && !companyDetailRequest.getName().isEmpty()
                ? companyDetailRequest.getName() : company.getName());
        company.setEmail(companyDetailRequest.getEmail() != null && !companyDetailRequest.getEmail().isEmpty()
                ? companyDetailRequest.getEmail() : company.getEmail());
        company.setAddress(companyDetailRequest.getAddress() != null && !companyDetailRequest.getAddress().isEmpty()
                ? companyDetailRequest.getAddress() : company.getAddress());
        company.setPicture(pictureUrl != null ? pictureUrl : company.getPicture());
        company.setBanner(bannerUrl != null ? bannerUrl : company.getBanner());
        company.setDescription(companyDetailRequest.getDescription() != null && !companyDetailRequest.getDescription().isEmpty()
                ? companyDetailRequest.getDescription() : company.getDescription());

        companyRepository.save(company);

        return "Company Detail Successfully Updated with Username: " + username;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/jpeg") || contentType.startsWith("image/png") || contentType.startsWith("image/gif"));
    }

}
