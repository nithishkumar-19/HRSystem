// ─── Auth ────────────────────────────────────────────────────────────────────

export type UserRole = 'HR' | 'MANAGER' | 'EMPLOYEE';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  role: UserRole;
  employeeId: number | null;
}

export interface AuthState {
  token: string | null;       // base64 "user:pass"
  role: UserRole | null;
  employeeId: number | null;
}

// ─── Employee ────────────────────────────────────────────────────────────────

export type EmployeeStatus = 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';

export interface Employee {
  id: number | null;
  firstName: string | null;
  lastName: string | null;
  personalEmail: string | null;
  companyEmail: string | null;
  gender: string | null;
  dob: string | null;
  department: string | null;
  designation: string | null;
  mobile: string | null;
  joiningDate: string | null;
}

export interface EmployeeResponse {
  id: number | null;
  name: string | null;
  dept: string | null;
  design: string | null;
  gender: string | null;
  email: string | null;
}

export interface CreateEmployeeRequest {
  firstName: string;
  lastName: string;
  personalEmail: string;
  companyEmail: string;
  gender: string;
  dob: string;
  department: string;
  designation: string;
  mobile: string;
  joiningDate: string;
}

// ─── Leave ───────────────────────────────────────────────────────────────────

export type LeaveStatus = 'PENDING' | 'MANAGER_APPROVED' | 'MANAGER_REJECTED' |'APPROVED' | 'REJECTED';

export interface Leave {
  id: number;
  fromDate: string;
  toDate: string;
  reason: string;
  status: LeaveStatus;
}

export interface ApplyLeaveRequest {
  employeeId: number;
  fromDate: string;
  toDate: string;
  reason: string;
  noOfDays: number;
}

// ─── Certification ───────────────────────────────────────────────────────────

export type CertStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface Certification {
  id:number,
  employeeName:string;
  certificationName: string;
  issuingOrganization: string;
  certificateFilePath: string;
  status: CertStatus;
}

export interface UploadCertificationRequest {
  employeeId: number | string;
  certificationName: string;
  issuingOrganization: string;
  certificateFilePath: string;
  file:File
}

// ─── Document ────────────────────────────────────────────────────────────────

export type DocStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface EmployeeDocument {
  id: number;
  employee: { firstName: string; lastName: string };
  documentType: string;
  status: DocStatus;
}

// ─── Payslip ─────────────────────────────────────────────────────────────────

export interface Payslip {
  employeeId: number;
  name:string;
  //employeeId: number;
  month: string;
  year: number;
  basicSalary: number;
  allowances: number;
  deductions: number;
  netSalary: number;
  pdfPath: string;
}

export interface GeneratePayslipRequest {
  employeeId: number;
  month: string;
  year: number;
  basicSalary: number;
  allowances: number;
  deductions: number;
}

// ─── Dashboard ───────────────────────────────────────────────────────────────

export interface HrDashboardData {
  totalEmployees: number;
  onboardingPending: number;
  pendingDocuments: number;
}
