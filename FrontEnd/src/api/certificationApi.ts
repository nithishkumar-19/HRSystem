import { apiClient } from './client';
import type { Certification, UploadCertificationRequest } from '../types';

export const certificationApi = {
  getAll: (empId:number) =>
    apiClient.get<Certification[]>(`/certification/all?empId=${empId}`).then((r) => r.data),

  upload: async (request: UploadCertificationRequest) => {
    const formData = new FormData();

    formData.append("employeeId", request.employeeId.toString());
    formData.append("certificationName", request.certificationName);
    formData.append("issuingOrganization", request.issuingOrganization);
    formData.append("file", request.file);

    const response = await apiClient.post(
      "/certification/upload",
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );

    return response.data;
  },

  approve: (id: number, remarks = 'ok') =>
    apiClient
      .put(`/certification/approve/${id}?remarks=${encodeURIComponent(remarks)}`)
      .then((r) => r.data),

  reject: (id: number, remarks = 'not approved') =>
    apiClient
      .put(`/certification/reject/${id}?remarks=${encodeURIComponent(remarks)}`)
      .then((r) => r.data),

  addSkill: (certId: number, skillName: string, proficiencyLevel: string) =>
    apiClient
      .post(
        `/certification/add-skill/${certId}?skillName=${encodeURIComponent(skillName)}&proficiencyLevel=${encodeURIComponent(proficiencyLevel)}`
      )
      .then((r) => r.data),
};
