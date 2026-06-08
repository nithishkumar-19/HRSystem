package com.nithish.Enterprise.HR.Management.System.Controller;

import com.nithish.Enterprise.HR.Management.System.Dto.CertificateResponse;
import com.nithish.Enterprise.HR.Management.System.Dto.CertificationDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Certification;
import com.nithish.Enterprise.HR.Management.System.Entity.Skill;
import com.nithish.Enterprise.HR.Management.System.Service.CertificationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/certification")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadCertification(@ModelAttribute  CertificationDTO dto) throws IOException {
        return ResponseEntity.ok(certificationService.uploadCertification(dto));
    }

    @GetMapping("/all")
    public List<CertificateResponse> getAllCertifications(@RequestParam long empId) {
        return certificationService.getAllCertifications(empId);
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