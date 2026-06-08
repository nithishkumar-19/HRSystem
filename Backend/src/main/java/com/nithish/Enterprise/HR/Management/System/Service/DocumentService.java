package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.UploadDocumentDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.EmployeeDocument;
import com.nithish.Enterprise.HR.Management.System.Enum.DocumentStatus;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeDocumentRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDocumentRepository documentRepository;

    public DocumentService(EmployeeRepository employeeRepository, EmployeeDocumentRepository documentRepository) {
        this.employeeRepository = employeeRepository;
        this.documentRepository = documentRepository;
    }

    public EmployeeDocument approveDocument(Long documentId) {

        EmployeeDocument document = documentRepository.findById(documentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Document not found: " + documentId));

        document.setStatus(DocumentStatus.APPROVED);
        return documentRepository.save(document);
    }

    public EmployeeDocument rejectDocument(Long documentId, String remarks) {

        EmployeeDocument document = documentRepository.findById(documentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Document not found: " + documentId));

        document.setStatus(DocumentStatus.REJECTED);
        document.setRemarks(remarks);

        return documentRepository.save(document);
    }

    public EmployeeDocument uploadDocument(UploadDocumentDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + dto.getEmployeeId()));

        EmployeeDocument document = EmployeeDocument.builder()
                .fileName(dto.getFileName())
                .filePath(dto.getFilePath())
                .documentType(dto.getDocumentType())
                .status(DocumentStatus.PENDING)
                .employee(employee)
                .build();

        return documentRepository.save(document);
    }
}