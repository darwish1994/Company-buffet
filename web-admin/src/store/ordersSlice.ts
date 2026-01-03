import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { ordersApi } from '../api/orders.api';
import { Order, OrderStatus, PageResponse } from '../types';

interface OrdersState {
  orders: Order[];
  currentOrder: Order | null;
  pagination: {
    page: number;
    totalPages: number;
    totalElements: number;
  };
  loading: boolean;
  error: string | null;
}

const initialState: OrdersState = {
  orders: [],
  currentOrder: null,
  pagination: {
    page: 0,
    totalPages: 0,
    totalElements: 0,
  },
  loading: false,
  error: null,
};

export const fetchOrders = createAsyncThunk(
  'orders/fetchOrders',
  async ({ page = 0, size = 20, status }: { page?: number; size?: number; status?: OrderStatus }) => {
    return await ordersApi.getOrders(page, size, status);
  }
);

export const updateOrderStatus = createAsyncThunk(
  'orders/updateStatus',
  async ({ id, status }: { id: string; status: OrderStatus }) => {
    return await ordersApi.updateOrderStatus(id, status);
  }
);

const ordersSlice = createSlice({
  name: 'orders',
  initialState,
  reducers: {
    updateOrderRealtime: (state, action: PayloadAction<Order>) => {
      const index = state.orders.findIndex(o => o.id === action.payload.id);
      if (index !== -1) {
        state.orders[index] = action.payload;
      } else {
        state.orders.unshift(action.payload);
      }
    },
    clearOrders: (state) => {
      state.orders = [];
      state.currentOrder = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchOrders.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchOrders.fulfilled, (state, action: PayloadAction<PageResponse<Order>>) => {
        state.loading = false;
        state.orders = action.payload.content;
        state.pagination = {
          page: action.payload.page,
          totalPages: action.payload.totalPages,
          totalElements: action.payload.totalElements,
        };
      })
      .addCase(fetchOrders.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Failed to fetch orders';
      })
      .addCase(updateOrderStatus.fulfilled, (state, action: PayloadAction<Order>) => {
        const index = state.orders.findIndex(o => o.id === action.payload.id);
        if (index !== -1) {
          state.orders[index] = action.payload;
        }
      });
  },
});

export const { updateOrderRealtime, clearOrders } = ordersSlice.actions;
export default ordersSlice.reducer;
