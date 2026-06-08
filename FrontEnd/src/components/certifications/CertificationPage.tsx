import { useEffect, useState } from 'react';
import { certificationApi } from '../../api';
import { useAuthStore, useCertificationStore } from '../../store';
import type { Certification } from '../../types';

import { Card } from '../shared/Card';
import { Button } from '../shared/Button';
import { FormField } from '../shared/FormField';
import { Table } from '../shared/Table';
import { Badge } from '../shared/Badge';

import './CertificationPage.css';

interface Props {
  canUpload?: boolean;
  canReview?: boolean;
}

const statusVariant = (s: Certification['status']) => {
  if (s === 'APPROVED') return 'success';
  if (s === 'REJECTED') return 'danger';
  return 'warning';
};

export const CertificationPage = ({
  canUpload = false,
  canReview = false,
}: Props) => {
  const { employeeId,role } = useAuthStore();

  const {
    certifications,
    loading,
    fetchCertifications,
    uploadCertification,
  } = useCertificationStore();
 
  const [certName, setCertName] = useState('');
  const [org, setOrg] = useState('');
  const [file, setFile] = useState<File | null>(null);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (employeeId) {
      fetchCertifications(employeeId);
    }
  }, [employeeId, fetchCertifications]);

  const handleUpload = async (id:number , certName:string , issuingOrg:string) => {
    if (!employeeId) {
      alert('Employee ID not found');
      return;
    }

    if (!file) {
      alert('Please select a file');
      return;
    }

    try {
      setSubmitting(true);
      
      await uploadCertification({employeeId:id , 
        certificationName:certName ,
        issuingOrganization:issuingOrg,
        certificateFilePath:"FilePath" , 
        file:file });

      setCertName('');
      setOrg('');
      setFile(null);

      await fetchCertifications(employeeId);

      alert('Certification uploaded successfully');
    } catch (error) {
      console.error(error);
      alert('Upload failed');
    } finally {
      setSubmitting(false);
    }
  };

  const handleApprove = async (id: number) => {
    try {
      await certificationApi.approve(id);

      if (employeeId) {
        await fetchCertifications(employeeId);
      }
    } catch {
      alert('Approval failed');
    }
  };

  const handleReject = async (id: number) => {
    try {
      await certificationApi.reject(id);

      if (employeeId) {
        await fetchCertifications(employeeId);
      }
    } catch {
      alert('Rejection failed');
    }
  };

  const handleAddSkill = async (certId: number) => {
    const skillName = prompt('Enter skill name:');
    const level = prompt('Enter proficiency level:');

    if (!skillName || !level) return;

    try {
      await certificationApi.addSkill(certId, skillName, level);
      alert('Skill added');
    } catch {
      alert('Failed to add skill');
    }
  };

  const columns = [
    {
      key: 'EmployeeName',
      header: 'EmployeeName',
      render: (c: Certification) => c.employeeName,
    },
    {
      key: 'name',
      header: 'Certification',
      render: (c: Certification) => c.certificationName,
    },
    {
      key: 'org',
      header: 'Organization',
      render: (c: Certification) => c.issuingOrganization,
    },
    {
      key: 'status',
      header: 'Status',
      render: (c: Certification) => (
        <Badge
          label={c.status}
          variant={statusVariant(c.status)}
        />
      ),
    },
    ...(canReview && role == "MANAGER"
      ? [
          {
            key: 'action',
            header: 'Action',
            render: (c: Certification) => (
              <>
                <Button
                  size="sm"
                  variant="success"
                  onClick={() => handleApprove(c.id)}
                >
                  Approve
                </Button>

                <Button
                  size="sm"
                  variant="danger"
                  onClick={() => handleReject(c.id)}
                >
                  Reject
                </Button>
              </>
            ),
          },
        ]
      : []),
  ];

  return (
    <>
      <div className="page__header">
        <h1 className="page__title">
          Certification Management
        </h1>

        <p className="page__subtitle">
          {canUpload
            ? 'Upload and track certifications'
            : 'Review certification submissions'}
        </p>
      </div>

      {canUpload && role != "HR" &&  (
        <Card title="Upload Certification">
          <div className="cert-form">
            <FormField
              id="certName"
              label="Certification Name"
              value={certName}
              onChange={(e) => setCertName(e.target.value)}
            />

            <FormField
              id="certOrg"
              label="Issuing Organization"
              value={org}
              onChange={(e) => setOrg(e.target.value)}
            />

            <div className="form-field">
              <label htmlFor="certFile">
                Certificate File
              </label>

              <input
                id="certFile"
                type="file"
                accept=".pdf,.jpg,.jpeg,.png"
                onChange={(e) =>
                  setFile(e.target.files?.[0] ?? null)
                }
              />
            </div>

            <div
              style={{
                display: 'flex',
                alignItems: 'flex-end',
              }}
            >
              {employeeId != null && (
                <Button
                variant="warning"
                loading={submitting}
                onClick={() => {handleUpload(employeeId, certName ,org)}}
              >
                Upload
              </Button>
              )}
              
            </div>
          </div>
        </Card>
      )}

      <Card
        title="Certification List"
      >
        {loading ? (
          <p
            style={{
              color: 'var(--color-text-muted)',
            }}
          >
            Loading...
          </p>
        ) : (
          <Table
            columns={columns}
            data={certifications}
            keyExtractor={(c) => c.id}
            hasActions={canReview}
          />
        )}
      </Card>
    </>
  );
};