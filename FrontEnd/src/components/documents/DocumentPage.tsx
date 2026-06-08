import { useEffect } from 'react';
import { documentApi } from '../../api';
import { useDocumentStore } from '../../store';
import type { EmployeeDocument } from '../../types';
import { Card } from '../shared/Card';
import { Button } from '../shared/Button';
import { Table } from '../shared/Table';
import { Badge } from '../shared/Badge';
import './DocumentPage.css';

const statusVariant = (s: EmployeeDocument['status']) => {
  if (s === 'APPROVED') return 'success';
  if (s === 'REJECTED') return 'danger';
  return 'warning';
};

export const DocumentPage = () => {
  const { documents, loading, fetchDocuments } = useDocumentStore();

  useEffect(() => { fetchDocuments(); }, [fetchDocuments]);

  const handleApprove = async (id: number) => {
    await documentApi.approve(id);
    fetchDocuments();
  };

  const handleReject = async (id: number) => {
    const remarks = prompt('Enter rejection reason:');
    if (!remarks) return;
    await documentApi.reject(id, remarks);
    fetchDocuments();
  };

  const columns = [
    { key: 'id',     header: 'ID',       render: (d: EmployeeDocument) => d.id },
    { key: 'emp',    header: 'Employee',  render: (d: EmployeeDocument) => d.employee.firstName },
    { key: 'type',   header: 'Type',      render: (d: EmployeeDocument) => d.documentType },
    { key: 'status', header: 'Status',    render: (d: EmployeeDocument) => (
      <Badge label={d.status} variant={statusVariant(d.status)} />
    )},
    {
      key: 'action',
      header: 'Action',
      render: (d: EmployeeDocument) => (
        <>
          <Button size="sm" variant="success" onClick={() => handleApprove(d.id)}>Approve</Button>
          <Button size="sm" variant="danger"  onClick={() => handleReject(d.id)}>Reject</Button>
        </>
      ),
    },
  ];

  return (
    <>
      <div className="page__header">
        <h1 className="page__title">Document Management</h1>
        <p className="page__subtitle">Review and verify employee documents</p>
      </div>

      <Card title="Document List" className="document-page">
        {loading ? (
          <p style={{ color: 'var(--color-text-muted)', padding: '16px 0' }}>Loading…</p>
        ) : (
          <Table columns={columns} data={documents} keyExtractor={(d) => d.id} hasActions />
        )}
      </Card>
    </>
  );
};
