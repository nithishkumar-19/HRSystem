package com.nithish.Enterprise.HR.Management.System.Controller;

import com.nithish.Enterprise.HR.Management.System.Dto.CertificationDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Certification;
import com.nithish.Enterprise.HR.Management.System.Entity.Skill;
import com.nithish.Enterprise.HR.Management.System.Service.CertificationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certification")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping("/upload")
    public Certification uploadCertification(@Valid @RequestBody CertificationDTO dto) {
        return certificationService.uploadCertification(dto);
    }

    @GetMapping("/all")
    public List<Certification> getAllCertifications() {
        return certificationService.getAllCertifications();
    }

    @PutMapping("/approve/{id}")
    public Certification approveCertification(@PathVariable Long id, @RequestParam String remarks) {
        return certificationService.approveCertification(id, remarks);
    }

    @PutMapping("/reject/{id}")
    public Certification rejectCertification(@PathVariable Long id, @RequestParam String remarks) {
        return certificationService.rejectCertification(id, remarks);
    }

    @PostMapping("/add-skill/{certificationId}")
    public Skill addSkill(@PathVariable Long certificationId, @RequestParam String skillName, @RequestParam String proficiencyLevel) {

        return certificationService.addSkillFromCertification(certificationId, skillName, proficiencyLevel);
    }
}