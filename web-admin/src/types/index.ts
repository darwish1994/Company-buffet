// User types
export enum UserRole {
  ADMIN = 'ADMIN',
  EMPLOYEE = 'EMPLOYEE',
  WORKER = 'WORKER',
  MANAGEMENT = 'MANAGEMENT',
}

export interface User {
  id: string;
  name: string;
  email: string;
  role: UserRole;
  department?: string;
  employeeId?: string;
  quota: number;
  active: boolean;
}

// Auth types
export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

// Order types
export enum OrderStatus {
  PENDING = 'PENDING',
  PREPARING = 'PREPARING',
  READY = 'READY',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
}

export interface OrderItem {
  beverageId: string;
  beverageName: string;
  beverageNameAr: string;
  quantity: number;
  price: number;
  subtotal: number;
}

export interface Order {
  id: string;
  employeeId: string;
  employeeName: string;
  department?: string;
  beverages: OrderItem[];
  totalPrice: number;
  status: OrderStatus;
  notes?: string;
  orderDate: string;
  completedDate?: string;
  rating?: number;
  ratingComment?: string;
  workerId?: string;
  workerName?: string;
}

// Beverage types
export enum BeverageCategory {
  HOT_DRINKS = 'HOT_DRINKS',
  COLD_DRINKS = 'COLD_DRINKS',
  JUICES = 'JUICES',
  SMOOTHIES = 'SMOOTHIES',
  SPECIALTY = 'SPECIALTY',
  SEASONAL = 'SEASONAL',
}

export interface Beverage {
  id: string;
  name: string;
  nameAr: string;
  description?: string;
  descriptionAr?: string;
  category: BeverageCategory;
  price: number;
  image?: string;
  available: boolean;
  averageRating: number;
  totalOrders: number;
}

// API Response types
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data?: T;
}

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}
