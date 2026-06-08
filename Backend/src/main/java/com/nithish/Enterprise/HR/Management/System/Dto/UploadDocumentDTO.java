package com.nithish.Enterprise.HR.Management.System.Dto;

import com.nithish.Enterprise.HR.Management.System.Enum.DocumentType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadDocumentDTO {

    private Long employeeId;

    private String fileName;

    private String filePath;

    private DocumentType documentType;
}