import { Navigate } from 'react-router-dom';
import { useAuthStore } from '../../store';
import type { UserRole } from '../../types';

interface Props {
  children: React.ReactNode;
  allowedRoles?: UserRole[];
}

export const ProtectedRoute = ({ children, allowedRoles }: Props) => {
  const { token, role } = useAuthStore();

  if (!token) return <Navigate to="/login" replace />;

  if (allowedRoles && role && !allowedRoles.includes(role)) {
    // Redirect to their correct dashboard
    const homeMap: Record<UserRole, string> = {
      HR: '/hr',
      MANAGER: '/manager',
      EMPLOYEE: '/employee',
    };
    return <Navigate to={homeMap[role]} replace />;
  }

  return <>{children}</>;
};
