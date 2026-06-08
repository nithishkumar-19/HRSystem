import { useEffect, useState } from 'react';
import { leaveApi } from '../../api';
import { useAuthStore, useLeaveStore } from '../../store';
import type { Leave, LeaveStatus } from '../../types';
import { Card } from '../shared/Card';
import { Button } from '../shared/Button';
import { FormField } from '../shared/FormField';
import { Table } from '../shared/Table';
import { Badge } from '../shared/Badge';
import './LeavePage.css';

interface Props {
  /** Show apply-leave form. Managers see approval actions only. */
  canApply?: boolean;
  /** Show approve/reject buttons */
  canApprove?: boolean;
}

const statusVariant = (s: Leave['status']) => {
  if (s === 'APPROVED') return 'success';
  if (s === 'REJECTED') return 'danger';
  if (s === 'MANAGER_APPROVED') return 'info';
  return 'warning';
};

export const LeavePage = ({ canApply = false, canApprove = false }: Props) => {
  const { employeeId } = useAuthStore();
  const { leaves, Approvalleaves, loading, fetchLeaves , fetchApprovalLeaves } = useLeaveStore();

  const [from,    setFrom]    = useState('');
  const [to,      setTo]      = useState('');
  const [reason,  setReason]  = useState('');
  const [submitting, setSub]  = useState(false);

  useEffect(() => {
    if (employeeId) fetchLeaves(employeeId);

    if(employeeId && !canApply) fetchApprovalLeaves(employeeId);
  }, [employeeId, fetchLeaves , fetchApprovalLeaves]);

  const handleApply = async () => {
    if (!employeeId) return;
    setSub(true);
    try {
      await leaveApi.apply({ employeeId, fromDate: from, toDate: to, reason, noOfDays: 5 });
      setFrom(''); setTo(''); setReason('');
      fetchLeaves(employeeId);
    } catch {
      alert('Leave apply failed');
    } finally {
      setSub(false);
    }
  };

  const handleApprove = async (id: number) => {
    await leaveApi.approve(id , "Approved by manager");
    if (employeeId) fetchLeaves(employeeId);
  };

  const handleReject = async (id: number) => {
    const remarks = prompt('Enter rejection reason:');
    if (!remarks) return;
    await leaveApi.reject(id, remarks);
    if (employeeId) fetchLeaves(employeeId);
  };
  const handleHRConfirmation = async (id:number) => {
    var decision: LeaveStatus= "PENDING";
    if(Approvalleaves != null && Approvalleaves.length > 0) {
       decision = Approvalleaves.find(X => X.id == id)?.status
    }
    

    await leaveApi.hrConfirmation(id , decision == "MANAGER_APPROVED" ? "Leave Confirmed." : "Leave Rejected");

    if (employeeId) fetchLeaves(employeeId);
  }

  const columns = [
    { key: 'id',     header: 'ID',       render: (l: Leave) => l.id },
    { key: 'from',   header: 'From',     render: (l: Leave) => l.fromDate },
    { key: 'to',     header: 'To',       render: (l: Leave) => l.toDate },
    { key: 'reason', header: 'Reason',   render: (l: Leave) => l.reason },
    { key: 'status', header: 'Status',   render: (l: Leave) => (
      <Badge label={l.status} variant={statusVariant(l.status)} />
    )},
    ...(canApprove ? [{
      key: 'action',
      header: 'Action',
      render: (l: Leave) => (
        <>
          {l.status === "PENDING" ? (
  <>
      <Button size="sm" variant="success" onClick={() => handleApprove(l.id)}>
        Approve
      </Button>
      <Button size="sm" variant="danger" onClick={() => handleReject(l.id)}>
        Reject
      </Button>
    </>
  ) : l.status === "MANAGER_APPROVED" ? (
    <Button size="sm" variant="success" onClick={() => handleHRConfirmation(l.id)}>
      Confirm Leave
    </Button>
  ) : l.status === "MANAGER_REJECTED" ? (
    <Button size="sm" variant="danger" onClick={() => handleHRConfirmation(l.id)}>
      Confirm Rejection
    </Button>
  ) : null}
        </>
      ),
    }] : []),
  ];

  return (
    <>
      <div className="page__header">
        <h1 className="page__title">Leave Management</h1>
        <p className="page__subtitle">{canApply ? 'Apply and track your leaves' : 'Review leave requests'}</p>
      </div>

      {canApply && (
        <Card title="Apply Leave">
          <div className="leave-form">
            <FormField id="fromDate" label="From Date" type="date" value={from} onChange={(e) => setFrom(e.target.value)} />
            <FormField id="toDate"   label="To Date"   type="date" value={to}   onChange={(e) => setTo(e.target.value)} />
            <FormField id="reason"   label="Reason"    value={reason}           onChange={(e) => setReason(e.target.value)} placeholder="Reason for leave" />
            <div style={{ display: 'flex', alignItems: 'flex-end' }}>
              <Button variant="primary" loading={submitting} onClick={handleApply}>
                Apply Leave
              </Button>
            </div>
          </div>
        </Card>
      )}

      <Card title="Leave Requests" style={{ marginTop: canApply ? '24px' : 0 } as React.CSSProperties}>
        {loading ? (
          <p style={{ color: 'var(--color-text-muted)' }}>Loading…</p>
        ) : (
          <Table columns={columns} data={canApprove ? Approvalleaves : leaves} keyExtractor={(l) => l.id} hasActions={canApprove} />
          
        )}
      </Card>
    </>
  );
};
