import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useAuthStore } from './store';

import { LoginPage } from './components/auth/LoginPage';
import { Layout } from './components/shared/Layout';
import { ProtectedRoute } from './components/shared/ProtectedRoute';

import { HrDashboard }                      from './components/dashboard/HrDashboard';
import { ManagerDashboard, EmployeeDashboard } from './components/dashboard/OtherDashboards';
import { EmployeePage }                     from './components/employees/EmployeePage';
import { LeavePage }                        from './components/leave/LeavePage';
import { CertificationPage }                from './components/certifications/CertificationPage';
import { DocumentPage }                     from './components/documents/DocumentPage';
import { PayslipPage }                      from './components/payslip/PayslipPage';

const RootRedirect = () => {
  const { role, isAuthenticated } = useAuthStore();
  if (!isAuthenticated()) return <Navigate to="/login" replace />;
  const map = { HR: '/hr', MANAGER: '/manager', EMPLOYEE: '/employee' } as const;
  return <Navigate to={role ? map[role] : '/login'} replace />;
};

export const App = () => (
  <BrowserRouter>
    <Routes>
      {/* Public */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/"      element={<RootRedirect />} />

      {/* ── HR routes ──────────────────────────────────────────────────────── */}
      <Route
        path="/hr"
        element={
          <ProtectedRoute allowedRoles={['HR']}>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index              element={<HrDashboard />} />
        <Route path="employees"   element={<EmployeePage />} />
        <Route path="leaves"      element={<LeavePage canApprove />} />
        <Route path="certifications" element={<CertificationPage canUpload canReview />} />
        <Route path="documents"   element={<DocumentPage />} />
        <Route path="payslips"    element={<PayslipPage canGenerate />} />
      </Route>

      {/* ── Manager routes ─────────────────────────────────────────────────── */}
      <Route
        path="/manager"
        element={
          <ProtectedRoute allowedRoles={['MANAGER']}>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index              element={<ManagerDashboard />} />
        <Route path="leaves"      element={<LeavePage canApprove />} />
        <Route path="certifications" element={<CertificationPage canReview />} />
      </Route>

      {/* ── Employee routes ────────────────────────────────────────────────── */}
      <Route
        path="/employee"
        element={
          <ProtectedRoute allowedRoles={['EMPLOYEE']}>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index              element={<EmployeeDashboard />} />
        <Route path="leaves"      element={<LeavePage canApply />} />
        <Route path="certifications" element={<CertificationPage canUpload />} />
        <Route path="payslips"    element={<PayslipPage />} />
      </Route>

      {/* Fallback */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  </BrowserRouter>
);
