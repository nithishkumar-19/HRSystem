import { Link } from 'react-router-dom';
import './Dashboard.css';

export const ManagerDashboard = () => (
  <>
    <div className="page__header">
      <h1 className="page__title">Manager Dashboard</h1>
      <p className="page__subtitle">Team management overview</p>
    </div>
    <div className="dashboard-quicklinks">
      <Link to="/manager/leaves" className="quicklink-card">
        <span className="quicklink-card__icon" aria-hidden="true">📋</span>
        <div>
          <div className="quicklink-card__label">Leave Approvals</div>
          <div className="quicklink-card__sub">Review team requests</div>
        </div>
      </Link>
      <Link to="/manager/certifications" className="quicklink-card">
        <span className="quicklink-card__icon" aria-hidden="true">🏅</span>
        <div>
          <div className="quicklink-card__label">Certification Reviews</div>
          <div className="quicklink-card__sub">Approve certifications</div>
        </div>
      </Link>
    </div>
  </>
);

export const EmployeeDashboard = () => (
  <>
    <div className="page__header">
      <h1 className="page__title">Employee Dashboard</h1>
      <p className="page__subtitle">Your personal workspace</p>
    </div>
    <div className="dashboard-quicklinks">
      <Link to="/employee/leaves" className="quicklink-card">
        <span className="quicklink-card__icon" aria-hidden="true">📋</span>
        <div>
          <div className="quicklink-card__label">Apply Leave</div>
          <div className="quicklink-card__sub">Submit & track requests</div>
        </div>
      </Link>
      <Link to="/employee/certifications" className="quicklink-card">
        <span className="quicklink-card__icon" aria-hidden="true">🏅</span>
        <div>
          <div className="quicklink-card__label">Upload Certification</div>
          <div className="quicklink-card__sub">Add your credentials</div>
        </div>
      </Link>
      <Link to="/employee/payslips" className="quicklink-card">
        <span className="quicklink-card__icon" aria-hidden="true">💰</span>
        <div>
          <div className="quicklink-card__label">View Payslips</div>
          <div className="quicklink-card__sub">Download pay statements</div>
        </div>
      </Link>
    </div>
  </>
);
