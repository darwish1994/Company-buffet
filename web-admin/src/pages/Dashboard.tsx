import { useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../store/hooks';
import { logout } from '../store/authSlice';
import toast from 'react-hot-toast';
import './Dashboard.css';

function Dashboard() {
  const { user } = useAppSelector(state => state.auth);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch(logout());
    toast.success('Logged out successfully');
    navigate('/login');
  };

  return (
    <div className="dashboard">
      <nav className="navbar">
        <div className="navbar-brand">
          <h2>Beverages Management</h2>
        </div>
        <div className="navbar-menu">
          <button onClick={() => navigate('/dashboard')} className="nav-link">
            Dashboard
          </button>
          <button onClick={() => navigate('/orders')} className="nav-link">
            Orders
          </button>
          <div className="navbar-user">
            <span>{user?.name} ({user?.role})</span>
            <button onClick={handleLogout} className="logout-btn">
              Logout
            </button>
          </div>
        </div>
      </nav>

      <div className="dashboard-content">
        <div className="page-header">
          <h1>Welcome, {user?.name}!</h1>
          <p>Manage your beverage orders and system</p>
        </div>

        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon">📊</div>
            <div className="stat-info">
              <h3>Total Orders</h3>
              <p className="stat-value">156</p>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon">⏳</div>
            <div className="stat-info">
              <h3>Pending</h3>
              <p className="stat-value">12</p>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon">✅</div>
            <div className="stat-info">
              <h3>Completed</h3>
              <p className="stat-value">142</p>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon">💰</div>
            <div className="stat-info">
              <h3>Revenue</h3>
              <p className="stat-value">$1,234</p>
            </div>
          </div>
        </div>

        <div className="quick-actions">
          <h2>Quick Actions</h2>
          <div className="actions-grid">
            <button onClick={() => navigate('/orders')} className="action-card">
              <span className="action-icon">📋</span>
              <span className="action-title">View Orders</span>
            </button>
            <button className="action-card">
              <span className="action-icon">🍹</span>
              <span className="action-title">Manage Beverages</span>
            </button>
            <button className="action-card">
              <span className="action-icon">👥</span>
              <span className="action-title">Manage Users</span>
            </button>
            <button className="action-card">
              <span className="action-icon">📈</span>
              <span className="action-title">View Reports</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
