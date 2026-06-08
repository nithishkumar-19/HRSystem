package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.CertificateResponse;
import com.nithish.Enterprise.HR.Management.System.Dto.CertificationDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Certification;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.Skill;
import com.nithish.Enterprise.HR.Management.System.Enum.CertificationStatus;
import com.nithish.Enterprise.HR.Management.System.Enum.LeaveStatus;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Mapper.CertificateMapper;
import com.nithish.Enterprise.HR.Management.System.Repository.CertificationRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.SkillRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.nithish.Enterprise.HR.Management.System.Enum.Role.HR;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
    private final CertificateMapper certificateMapper;
    private final UserRepository userRepository;

    public Skill addSkillFromCertification(Long certificationId, String skillName, String proficiencyLevel) {

        Certification certification = certificationRepository.findById(certificationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Certification not found: " + certificationId));

        if (certification.getStatus() != CertificationStatus.APPROVED) {
            throw new RuntimeException("Certification not approved");
        }

        Skill skill = Skill.builder()
                .skillName(skillName)
                .proficiencyLevel(proficiencyLevel)
                .employee(certification.getEmployee())
                .build();

        return skillRepository.save(skill);
    }

    public Certification approveCertification(Long certificationId, String remarks) {

        Certification certification = certificationRepository.findById(certificationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Certification not found: " + certificationId));

        certification.setStatus(CertificationStatus.APPROVED);
        certification.setManagerRemarks(remarks);

        return certificationRepository.save(certification);
    }

    public Certification rejectCertification(Long certificationId, String remarks) {

        Certification certification = certificationRepository.findById(certificationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Certification not found: " + certificationId));

        certification.setStatus(CertificationStatus.REJECTED);
        certification.setManagerRemarks(remarks);

        return certificationRepository.save(certification);
    }

    public String uploadCertification(CertificationDTO dto) throws IOException {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + dto.getEmployeeId()));


        Certification certification = Certification.builder()
                .certificationName(dto.getCertificationName())
                .status(CertificationStatus.PENDING)
                .fileName(dto.getFile().getOriginalFilename())
                .issuingOrganization(dto.getIssuingOrganization())
                .managerRemarks("NA")
                .data(dto.getFile().getBytes())
                .employee(employee)
                .build();

        certificationRepository.save(certification);
        return "Certificate PDF uploaded successfully...!";
    }

    public List<CertificateResponse> getAllCertifications(long empId) {

        var Employee = employeeRepository.findById(empId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + empId));
        List<CertificateResponse> certificationList;
        switch (Employee.getUser().getRole()) {

            case HR -> {
                certificationList = certificateMapper.toDto(certificationRepository.findByStatusIn(
                        List.of(CertificationStatus.APPROVED)));
            }

            case MANAGER -> {
                certificationList = certificateMapper.toDto(certificationRepository.findByStatusIn(
                        List.of(CertificationStatus.PENDING)));
            }

            case EMPLOYEE -> {
                certificationList = certificateMapper.toDto(certificationRepository.findByEmployeeId(empId));
            }

            default -> throw new IllegalStateException(
                    "Unsupported role: " + Employee.getUser().getRole());
        }

        return certificationList;

    }
}