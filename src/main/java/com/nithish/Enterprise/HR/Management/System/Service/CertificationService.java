package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.CertificationDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Certification;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.Skill;
import com.nithish.Enterprise.HR.Management.System.Enum.CertificationStatus;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Repository.CertificationRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;

    public CertificationService(CertificationRepository certificationRepository, EmployeeRepository employeeRepository, SkillRepository skillRepository) {
        this.certificationRepository = certificationRepository;
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
    }

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

    public Certification uploadCertification(CertificationDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + dto.getEmployeeId()));

        Certification certification = Certification.builder()
                .certificationName(dto.getCertificationName())
                .issuingOrganization(dto.getIssuingOrganization())
                .certificateFileName(dto.getCertificateFileName())
                .certificateFilePath(dto.getCertificateFilePath())
                .status(CertificationStatus.PENDING)
                .employee(employee)
                .build();

        return certificationRepository.save(certification);
    }

    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }
}