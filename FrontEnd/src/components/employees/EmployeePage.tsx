import { useEffect, useState } from 'react';
import { employeeApi } from '../../api';
import { useEmployeeStore } from '../../store';
import type { CreateEmployeeRequest, Employee } from '../../types';
import { Card } from '../shared/Card';
import { Button } from '../shared/Button';
import { FormField } from '../shared/FormField';
import { Table } from '../shared/Table';
import { Badge } from '../shared/Badge';
import './EmployeePage.css';

const DEPARTMENTS = [
  'Software Development', 'Quality Assurance (QA)', 'DevOps', 'Cloud Engineering',
  'Data Science', 'Cyber Security', 'UI/UX Design', 'Product Management',
  'Business Analysis', 'Human Resources (HR)', 'Finance', 'IT Support',
];

const DESIGNATIONS = [
  'Intern', 'Trainee', 'Software Engineer', 'Senior Software Engineer',
  'Technical Lead', 'Team Lead', 'Project Manager', 'DevOps Engineer',
  'QA Engineer', 'Data Analyst', 'Data Scientist', 'UI/UX Designer',
  'HR Executive', 'HR Manager',
];

const COUNTRY_CODES = [
  { code: '+91', flag: '🇮🇳' }, { code: '+1', flag: '🇺🇸' },
  { code: '+44', flag: '🇬🇧' }, { code: '+61', flag: '🇦🇺' },
  { code: '+65', flag: '🇸🇬' },
];

const emptyForm = (): CreateEmployeeRequest & { countryCode: string } => ({
  firstName: '', lastName: '', personalEmail: '', companyEmail: '',
  gender: '', dob: '', department: '', designation: '',
  mobile: '', joiningDate: '', countryCode: '+91',
});

export const EmployeePage = () => {
  const { employees, loading, fetchEmployees, addEmployee } = useEmployeeStore();
  const [form, setForm]         = useState(emptyForm());
  const [submitting, setSubmit] = useState(false);

  useEffect(() => { fetchEmployees(); }, [fetchEmployees]);

  const set = (key: keyof typeof form) =>
    (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
      setForm((f) => ({ ...f, [key]: e.target.value }));

  const handleAdd = async () => {
    setSubmit(true);
    try {
      const payload: CreateEmployeeRequest = {
        ...form,
        mobile: form.countryCode + form.mobile,
      };
      const newEmp = await employeeApi.create(payload);
      addEmployee(newEmp);
      setForm(emptyForm());
    } catch (exception:unknown){

      console.log("Error at payslip generation" , exception);
      alert('Employee creation failed');
      
    } finally {
      setSubmit(false);
    }
  };


  const columns = 
  [
    { key: 'id',   header: 'ID',          render: (e: Employee) => e.id },
    { key: 'name', header: 'Name',         render: (e: Employee) => `${e.firstName} ${e.lastName}` },
    { key: 'dept', header: 'Department',   render: (e: Employee) => e.department },
    { key: 'desig',header: 'Designation',  render: (e: Employee) => e.designation },
    { key: 'gender',header: 'Gender',      render: (e: Employee) => e.gender},
    { key: 'email',header: 'Email',        render: (e: Employee) => e.personalEmail }
  ];

  return (
    <>
      <div className="page__header">
        <h1 className="page__title">Employee Management</h1>
        <p className="page__subtitle">Add and manage employee records</p>
      </div>

      <Card title="Add Employee">
        <div className="employee-form">
          <FormField id="firstName" label="First Name" value={form.firstName} onChange={set('firstName')} />
          <FormField id="lastName"  label="Last Name"  value={form.lastName}  onChange={set('lastName')} />
          <FormField id="gender" label="Gender" as="select" value={form.gender} onChange={set('gender')}>
            <option value="">Select gender</option>
            <option>Male</option><option>Female</option><option>Other</option>
          </FormField>
          <FormField id="dob" label="Date of Birth" type="date" value={form.dob} onChange={set('dob')} />

          <div className="field">
            <label className="field__label" htmlFor="mobile">Mobile Number</label>
            <div className="mobile-group">
              <select
                className="field__select"
                value={form.countryCode}
                onChange={set('countryCode')}
                aria-label="Country code"
              >
                {COUNTRY_CODES.map(({ code, flag }) => (
                  <option key={code} value={code}>{flag} {code}</option>
                ))}
              </select>
              <input id="mobile" className="field__input" type="text" placeholder="Mobile number"
                value={form.mobile} onChange={set('mobile')} />
            </div>
          </div>

          <FormField id="joiningDate" label="Joining Date" type="date" value={form.joiningDate} onChange={set('joiningDate')} />
          <FormField id="department" label="Department" as="select" value={form.department} onChange={set('department')}>
            <option value="">Select department</option>
            {DEPARTMENTS.map((d) => <option key={d}>{d}</option>)}
          </FormField>
          <FormField id="designation" label="Designation" as="select" value={form.designation} onChange={set('designation')}>
            <option value="">Select designation</option>
            {DESIGNATIONS.map((d) => <option key={d}>{d}</option>)}
          </FormField>
          <FormField id="personalEmail" label="Personal Email" type="email" value={form.personalEmail} onChange={set('personalEmail')} />
          <FormField id="companyEmail"  label="Company Email"  type="email" value={form.companyEmail}  onChange={set('companyEmail')} />

          <div className="employee-form__submit">
            <Button variant="success" loading={submitting} onClick={handleAdd}>
              Add Employee
            </Button>
          </div>
        </div>
      </Card>

      <Card title="Employee List" style={{ marginTop: '24px' } as React.CSSProperties}>
        {loading ? (
          <p style={{ color: 'var(--color-text-muted)' }}>Loading…</p>
        ) : (
          <Table
            columns={columns}
            data={employees}
            keyExtractor={(e) => e.id}
            hasActions={false}
          />
        )}
      </Card>
    </>
  );
};
