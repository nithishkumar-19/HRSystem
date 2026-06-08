import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { dashboardApi } from '../../api';
import type { HrDashboardData } from '../../types';
import './Dashboard.css';

export const HrDashboard = () => {
  const [stats, setStats]     = useState<HrDashboardData | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    dashboardApi
      .getHrStats()
      .then(setStats)
      .finally(() => setLoading(false));
  }, []);

  return (
    <>
      <div className="page__header">
        <h1 className="page__title">HR Dashboard</h1>
        <p className="page__subtitle">Overview of key HR metrics</p>
      </div>

      <div className="stat-grid">
        <div className="stat-card stat-card--primary">
          <div className="stat-card__label">Total Employees</div>
          <div className="stat-card__value">
            {loading ? '—' : stats?.totalEmployees ?? 0}
          </div>
        </div>
      </div>

      <h2 style={{ fontSize: '0.85rem', fontWeight: 600, textTransform: 'uppercase', letterSpacing: '0.08em', color: 'var(--color-text-muted)', marginBottom: '12px' }}>
        Quick Access
      </h2>

      <div className="dashboard-quicklinks">
        {[
          { to: '/hr/employees', icon: '👥', label: 'Employees', sub: 'Manage records' },
          { to: '/hr/leaves', icon: '📋', label: 'Leaves', sub: 'Approve requests' },
          { to: '/hr/certifications', icon: '🏅', label: 'Certifications', sub: 'Review uploads' },
          { to: '/hr/payslips', icon: '💰', label: 'Payslips', sub: 'Generate & view' },
        ].map(({ to, icon, label, sub }) => (
          <Link key={to} to={to} className="quicklink-card">
            <span className="quicklink-card__icon" aria-hidden="true">{icon}</span>
            <div>
              <div className="quicklink-card__label">{label}</div>
              <div className="quicklink-card__sub">{sub}</div>
            </div>
          </Link>
        ))}
      </div>
    </>
  );
};
