import { NavLink, Outlet, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../../store';
import './Layout.css';

const HR_LINKS = [
  { to: '/hr', label: 'Dashboard', icon: '▦' },
  { to: '/hr/employees', label: 'Employees', icon: '👥' },
  { to: '/hr/leaves', label: 'Leaves', icon: '📋' },
  { to: '/hr/certifications', label: 'Certifications', icon: '🏅' },
  { to: '/hr/payslips', label: 'Payslips', icon: '💰' },
];

const MANAGER_LINKS = [
  { to: '/manager', label: 'Dashboard', icon: '▦' },
  { to: '/manager/leaves', label: 'Leave Approvals', icon: '📋' },
  { to: '/manager/certifications', label: 'Cert Reviews', icon: '🏅' },
];

const EMPLOYEE_LINKS = [
  { to: '/employee', label: 'Dashboard', icon: '▦' },
  { to: '/employee/leaves', label: 'My Leaves', icon: '📋' },
  { to: '/employee/certifications', label: 'My Certs', icon: '🏅' },
  { to: '/employee/payslips', label: 'My Payslips', icon: '💰' },
];

export const Layout = () => {
  const { role, logout } = useAuthStore();
  const navigate = useNavigate();

  const links =
    role === 'HR'
      ? HR_LINKS
      : role === 'MANAGER'
      ? MANAGER_LINKS
      : EMPLOYEE_LINKS;

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="layout">
      {/* Sidebar */}
      <aside className="sidebar">
        <div className="sidebar__logo">HRMS</div>
        <nav className="sidebar__nav" aria-label="Main navigation">
          <p className="sidebar__section-label">Navigation</p>
          {links.map(({ to, label, icon }) => (
            <NavLink
              key={to}
              to={to}
              end={to.split('/').length <= 2}
              className={({ isActive }) =>
                `sidebar__link ${isActive ? 'active' : ''}`
              }
            >
              <span aria-hidden="true">{icon}</span>
              {label}
            </NavLink>
          ))}
        </nav>
        <div className="sidebar__footer">
          <button className="sidebar__logout" onClick={handleLogout}>
            <span aria-hidden="true">↩</span>
            Log out
          </button>
        </div>
      </aside>

      {/* Main */}
      <div className="layout__main">
        <header className="topbar">
          <span className="topbar__title">Human Resources</span>
          <span className="topbar__role">{role ?? 'GUEST'}</span>
        </header>
        <main className="page">
          <Outlet />
        </main>
      </div>
    </div>
  );
};
