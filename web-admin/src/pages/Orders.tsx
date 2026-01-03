import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../store/hooks';
import { fetchOrders, updateOrderStatus } from '../store/ordersSlice';
import { logout } from '../store/authSlice';
import { OrderStatus } from '../types';
import toast from 'react-hot-toast';
import './Orders.css';

function Orders() {
  const { user } = useAppSelector(state => state.auth);
  const { orders, loading } = useAppSelector(state => state.orders);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [filter, setFilter] = useState<OrderStatus | undefined>();

  useEffect(() => {
    loadOrders();
  }, [filter]);

  const loadOrders = () => {
    dispatch(fetchOrders({ page: 0, size: 20, status: filter }));
  };

  const handleStatusUpdate = async (orderId: string, newStatus: OrderStatus) => {
    try {
      await dispatch(updateOrderStatus({ id: orderId, status: newStatus })).unwrap();
      toast.success('Order status updated');
    } catch (error) {
      toast.error('Failed to update status');
    }
  };

  const handleLogout = () => {
    dispatch(logout());
    navigate('/login');
  };

  const getStatusColor = (status: OrderStatus) => {
    switch (status) {
      case OrderStatus.PENDING: return '#f59e0b';
      case OrderStatus.PREPARING: return '#3b82f6';
      case OrderStatus.READY: return '#8b5cf6';
      case OrderStatus.DELIVERED: return '#10b981';
      case OrderStatus.CANCELLED: return '#ef4444';
      default: return '#6b7280';
    }
  };

  return (
    <div className="orders-page">
      <nav className="navbar">
        <div className="navbar-brand">
          <h2>Beverages Management</h2>
        </div>
        <div className="navbar-menu">
          <button onClick={() => navigate('/dashboard')} className="nav-link">
            Dashboard
          </button>
          <button onClick={() => navigate('/orders')} className="nav-link active">
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

      <div className="orders-content">
        <div className="page-header">
          <h1>Orders Management</h1>
          <button onClick={loadOrders} className="refresh-btn">
            Refresh
          </button>
        </div>

        <div className="filters">
          <button
            onClick={() => setFilter(undefined)}
            className={`filter-btn ${!filter ? 'active' : ''}`}
          >
            All
          </button>
          <button
            onClick={() => setFilter(OrderStatus.PENDING)}
            className={`filter-btn ${filter === OrderStatus.PENDING ? 'active' : ''}`}
          >
            Pending
          </button>
          <button
            onClick={() => setFilter(OrderStatus.PREPARING)}
            className={`filter-btn ${filter === OrderStatus.PREPARING ? 'active' : ''}`}
          >
            Preparing
          </button>
          <button
            onClick={() => setFilter(OrderStatus.READY)}
            className={`filter-btn ${filter === OrderStatus.READY ? 'active' : ''}`}
          >
            Ready
          </button>
          <button
            onClick={() => setFilter(OrderStatus.DELIVERED)}
            className={`filter-btn ${filter === OrderStatus.DELIVERED ? 'active' : ''}`}
          >
            Delivered
          </button>
        </div>

        {loading ? (
          <div className="loading-state">
            <div className="spinner"></div>
            <p>Loading orders...</p>
          </div>
        ) : (
          <div className="orders-grid">
            {orders.length === 0 ? (
              <div className="empty-state">
                <p>No orders found</p>
              </div>
            ) : (
              orders.map(order => (
                <div key={order.id} className="order-card">
                  <div className="order-header">
                    <span className="order-id">#{order.id.substring(0, 8)}</span>
                    <span
                      className="order-status"
                      style={{ backgroundColor: getStatusColor(order.status) }}
                    >
                      {order.status}
                    </span>
                  </div>

                  <div className="order-info">
                    <p><strong>Employee:</strong> {order.employeeName}</p>
                    <p><strong>Department:</strong> {order.department || 'N/A'}</p>
                    <p><strong>Items:</strong> {order.beverages.length}</p>
                    <p><strong>Total:</strong> ${order.totalPrice.toFixed(2)}</p>
                  </div>

                  <div className="order-items">
                    {order.beverages.map((item, idx) => (
                      <div key={idx} className="order-item">
                        {item.quantity}x {item.beverageName}
                      </div>
                    ))}
                  </div>

                  {order.status === OrderStatus.PENDING && (
                    <div className="order-actions">
                      <button
                        onClick={() => handleStatusUpdate(order.id, OrderStatus.PREPARING)}
                        className="action-btn preparing"
                      >
                        Start Preparing
                      </button>
                    </div>
                  )}

                  {order.status === OrderStatus.PREPARING && (
                    <div className="order-actions">
                      <button
                        onClick={() => handleStatusUpdate(order.id, OrderStatus.READY)}
                        className="action-btn ready"
                      >
                        Mark Ready
                      </button>
                    </div>
                  )}

                  {order.status === OrderStatus.READY && (
                    <div className="order-actions">
                      <button
                        onClick={() => handleStatusUpdate(order.id, OrderStatus.DELIVERED)}
                        className="action-btn delivered"
                      >
                        Mark Delivered
                      </button>
                    </div>
                  )}
                </div>
              ))
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default Orders;
