import { useState } from 'react';
import { payslipApi } from '../../api';
import { useAuthStore } from '../../store';
import type { Payslip } from '../../types';
import { Card } from '../shared/Card';
import { Button } from '../shared/Button';
import { FormField } from '../shared/FormField';
import { Table } from '../shared/Table';
import './PayslipPage.css';

interface Props {
  /** HR can generate payslips; employees only view */
  canGenerate?: boolean;
}

export const PayslipPage = ({ canGenerate = false }: Props) => {
  const { employeeId, role } = useAuthStore();

  // Generate form state
  const [genEmpId,     setGenEmpId]     = useState('');
  const [month,        setMonth]        = useState('');
  const [year,         setYear]         = useState('');
  const [basic,        setBasic]        = useState('');
  const [allowances,   setAllowances]   = useState('');
  const [deductions,   setDeductions]   = useState('');
  const [generating,   setGenerating]   = useState(false);

  // Search state
  const [searchId,    setSearchId]    = useState('');
  const [payslips,    setPayslips]    = useState<Payslip[]>([]);
  const [loadingList, setLoadingList] = useState(false);

  const handleGenerate = async () => {
    setGenerating(true);
    try {
      await payslipApi.generate({
        employeeId: Number(genEmpId),
        month,
        year: Number(year),
        basicSalary: Number(basic),
        allowances: Number(allowances),
        deductions: Number(deductions),
      });
      alert('Payslip generated');
    } catch {
      alert('Payslip generation failed');
    } finally {
      setGenerating(false);
    }
  };

  const handleLoad = async () => {
    const id = role === 'EMPLOYEE' && employeeId ? employeeId : Number(searchId);
    setLoadingList(true);
    try {
      const data = await payslipApi.getByEmployee(id);
      setPayslips(data);
    } catch(exception: unknown) {
      console.log("Exception while loading payslip: " , exception);
      alert('Failed to load payslips');
    } finally {
      setLoadingList(false);
    }
  };

  const columns = [
    { key: 'id',     header: 'ID',          render: (p: Payslip) => p.employeeId },
    { key: 'name',     header: 'Name',          render: (p: Payslip) => p.name },
    { key: 'month',  header: 'Month',        render: (p: Payslip) => p.month },
    { key: 'year',   header: 'Year',         render: (p: Payslip) => p.year },
    { key: 'net',    header: 'Net Salary',   render: (p: Payslip) => `₹${(p?.basicSalary + p?.allowances - p?.deductions).toLocaleString() ?? "10000"}` }
  ];

  return (
    <>
      <div className="page__header">
        <h1 className="page__title">Payslip Management</h1>
        <p className="page__subtitle">
          {canGenerate ? 'Generate and view employee payslips' : 'View your payslips'}
        </p>
      </div>

      {canGenerate && (
        <Card title="Generate Payslip">
          <div className="payslip-generate-form">
            <FormField id="genEmpId"   label="Employee ID"  value={genEmpId}   onChange={(e) => setGenEmpId(e.target.value)} />
            <FormField id="month"      label="Month"        value={month}      onChange={(e) => setMonth(e.target.value)} placeholder="e.g. January" />
            <FormField id="year"       label="Year"         value={year}       onChange={(e) => setYear(e.target.value)}  type="number" />
            <FormField id="basic"      label="Basic Salary" value={basic}      onChange={(e) => setBasic(e.target.value)} type="number" />
            <FormField id="allowances" label="Allowances"   value={allowances} onChange={(e) => setAllowances(e.target.value)} type="number" />
            <FormField id="deductions" label="Deductions"   value={deductions} onChange={(e) => setDeductions(e.target.value)} type="number" />
            <div style={{ display: 'flex', alignItems: 'flex-end' }}>
              <Button variant="success" loading={generating} onClick={handleGenerate}>
                Generate
              </Button>
            </div>
          </div>
        </Card>
      )}

      <Card title="Employee Payslips" style={{ marginTop: canGenerate ? '24px' : 0 } as React.CSSProperties}>
        <div className="payslip-search">
          {role !== 'EMPLOYEE' && (
            <FormField
              id="searchEmpId"
              label="Employee ID"
              value={searchId}
              onChange={(e) => setSearchId(e.target.value)}
              placeholder="Enter employee ID"
            />
          )}
          <Button variant="primary" loading={loadingList} onClick={handleLoad}>
            Load Payslips
          </Button>
        </div>
        <Table columns={columns} data={payslips} keyExtractor={(p) => p.id} />
      </Card>
    </>
  );
};
