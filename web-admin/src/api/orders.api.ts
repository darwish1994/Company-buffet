import apiClient from './client';
import { Order, OrderStatus, ApiResponse, PageResponse } from '../types';

export const ordersApi = {
  getOrders: async (page = 0, size = 20, status?: OrderStatus): Promise<PageResponse<Order>> => {
    const params: any = { page, size };
    if (status) params.status = status;

    const response = await apiClient.get<ApiResponse<PageResponse<Order>>>('/orders', { params });
    return response.data.data!;
  },

  getOrderById: async (id: string): Promise<Order> => {
    const response = await apiClient.get<ApiResponse<Order>>(`/orders/${id}`);
    return response.data.data!;
  },

  createOrder: async (data: any): Promise<Order> => {
    const response = await apiClient.post<ApiResponse<Order>>('/orders', data);
    return response.data.data!;
  },

  updateOrderStatus: async (id: string, status: OrderStatus): Promise<Order> => {
    const response = await apiClient.put<ApiResponse<Order>>(
      `/orders/${id}/status`,
      null,
      { params: { status } }
    );
    return response.data.data!;
  },

  cancelOrder: async (id: string): Promise<Order> => {
    const response = await apiClient.delete<ApiResponse<Order>>(`/orders/${id}`);
    return response.data.data!;
  },

  getPendingOrders: async (): Promise<Order[]> => {
    const response = await apiClient.get<ApiResponse<Order[]>>('/orders/pending');
    return response.data.data!;
  },
};
