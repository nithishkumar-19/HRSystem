package com.nithish.Enterprise.HR.Management.System.Controller;

import com.nithish.Enterprise.HR.Management.System.Dto.UploadDocumentDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.EmployeeDocument;
import com.nithish.Enterprise.HR.Management.System.Service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public EmployeeDocument uploadDocument(@Valid @RequestBody UploadDocumentDTO dto) {

        return documentService.uploadDocument(dto);
    }

    @PutMapping("/approve/{documentId}")
    public EmployeeDocument approveDocument(@PathVariable Long documentId) {

        return documentService.approveDocument(documentId);
    }

    @PutMapping("/reject/{documentId}")
    public EmployeeDocument rejectDocument(@PathVariable Long documentId, @RequestParam String remarks) {

        return documentService.rejectDocument(documentId, remarks);
    }
}
