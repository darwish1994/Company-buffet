# Beverages Order Management System

A comprehensive beverage ordering system for company employees with multi-role support, real-time tracking, and analytics.

## System Architecture

- **Backend**: Kotlin + Spring Boot + MongoDB
- **Mobile**: Native Android (Kotlin + Jetpack Compose)
- **Web Admin**: React + TypeScript + Vite
- **Database**: MongoDB
- **Authentication**: JWT
- **Real-time**: Spring WebSocket
- **Notifications**: Firebase Cloud Messaging

## Features

### User Roles

1. **Admin**
   - Full CRUD operations for users and beverages
   - System configuration and settings
   - Comprehensive reports and analytics
   - Export data to Excel/PDF

2. **Employee**
   - Browse and order beverages
   - Track order status in real-time
   - View order history
   - Rate beverages
   - View consumption reports

3. **Worker** (Beverage Service Staff)
   - View orders queue in real-time
   - Update order status
   - Mark orders as completed
   - View daily workload

4. **Management**
   - Analytics dashboard
   - Cost analysis and budget tracking
   - Department comparison reports
   - Trend analysis

### Key Capabilities

- Multi-language support (Arabic RTL + English)
- Real-time order tracking with WebSocket
- Push notifications for order updates
- Image upload for beverages
- Advanced reporting with charts
- Export to Excel and PDF
- Role-based access control
- Offline support (Android)

## Project Structure

```
company_coffee_api/
├── backend/              # Spring Boot API
├── android/              # Native Android App
├── web-admin/            # React Admin Panel
├── docker/               # Docker configurations
├── docs/                 # Documentation
├── docker-compose.yml    # MongoDB setup
└── README.md
```

## Quick Start

### Prerequisites

- Java 17+ (for backend)
- Node.js 18+ (for web admin)
- Docker & Docker Compose
- Android Studio (for mobile app)
- MongoDB (via Docker)

### 1. Start MongoDB

```bash
docker-compose up -d
```

MongoDB will be available at `localhost:27017`
Mongo Express (Web UI) at `http://localhost:8081`

**Credentials:**
- Username: `admin`
- Password: `admin123`

### 2. Backend Setup

```bash
cd backend
./gradlew bootRun
```

Backend API will run on `http://localhost:8080`

### 3. Web Admin Setup

```bash
cd web-admin
npm install
npm run dev
```

Web admin will run on `http://localhost:5173`

### 4. Android App Setup

Open the `android/` directory in Android Studio and run the app.

## Database Schema

### Users Collection
```javascript
{
  _id: ObjectId,
  name: String,
  email: String (unique),
  password: String (hashed with BCrypt),
  role: Enum['ADMIN', 'EMPLOYEE', 'WORKER', 'MANAGEMENT'],
  department: String,
  employeeId: String (unique),
  quota: Number,
  fcmToken: String,
  active: Boolean,
  createdAt: Date
}
```

### Beverages Collection
```javascript
{
  _id: ObjectId,
  name: String,
  nameAr: String,
  description: String,
  descriptionAr: String,
  category: String,
  price: Number,
  image: String,
  available: Boolean,
  ratings: [{ userId: String, rating: Number }],
  createdAt: Date
}
```

### Orders Collection
```javascript
{
  _id: ObjectId,
  employeeId: String,
  beverages: [{ beverageId: String, quantity: Number, price: Number }],
  totalPrice: Number,
  status: Enum['PENDING', 'PREPARING', 'READY', 'DELIVERED', 'CANCELLED'],
  notes: String,
  orderDate: Date,
  completedDate: Date,
  rating: Number,
  workerId: String
}
```

## API Endpoints

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration

### Users (Admin only)
- `GET /api/v1/users` - List all users
- `POST /api/v1/users` - Create user
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user

### Beverages
- `GET /api/v1/beverages` - List beverages
- `POST /api/v1/beverages` - Create beverage (Admin)
- `PUT /api/v1/beverages/{id}` - Update beverage (Admin)
- `DELETE /api/v1/beverages/{id}` - Delete beverage (Admin)

### Orders
- `GET /api/v1/orders` - List orders
- `POST /api/v1/orders` - Create order
- `PUT /api/v1/orders/{id}/status` - Update order status
- `DELETE /api/v1/orders/{id}` - Cancel order

### Reports
- `GET /api/v1/reports/statistics` - Get statistics
- `GET /api/v1/reports/export/excel` - Export to Excel
- `GET /api/v1/reports/export/pdf` - Export to PDF

## Environment Variables

### Backend (.env or application.yml)
```yaml
MONGODB_URI: mongodb://admin:admin123@localhost:27017/beverages_db
JWT_SECRET: your-secret-key-change-in-production
JWT_EXPIRATION: 86400000
FIREBASE_CONFIG_PATH: path/to/firebase-service-account.json
```

### Web Admin (.env)
```env
VITE_API_BASE_URL=http://localhost:8080/api/v1
VITE_WS_URL=ws://localhost:8080/ws
```

## Development

### Backend Development
```bash
cd backend
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Web Admin Development
```bash
cd web-admin
npm run dev
```

### Build for Production

**Backend:**
```bash
cd backend
./gradlew build
java -jar build/libs/beverages-api-1.0.0.jar
```

**Web Admin:**
```bash
cd web-admin
npm run build
# Deploy dist/ folder to your hosting service
```

**Android:**
```bash
cd android
./gradlew assembleRelease
# APK will be in app/build/outputs/apk/release/
```

## Testing

### Backend Tests
```bash
cd backend
./gradlew test
```

### Web Admin Tests
```bash
cd web-admin
npm run test
```

## Default Users

After running seed data, you can login with:

**Admin:**
- Email: `admin@company.com`
- Password: `admin123`

**Employee:**
- Email: `employee@company.com`
- Password: `employee123`

**Worker:**
- Email: `worker@company.com`
- Password: `worker123`

**Management:**
- Email: `manager@company.com`
- Password: `manager123`

## Security Notes

- All passwords are hashed using BCrypt
- JWT tokens expire after 24 hours
- Use HTTPS in production
- Change default passwords before deployment
- Store sensitive data in environment variables
- Never commit Firebase credentials to version control

## License

Proprietary - Company Internal Use Only

## Support

For issues and feature requests, contact the development team.

## Documentation

- [Backend API Documentation](./docs/api/README.md)
- [Web Admin Guide](./web-admin/README.md)
- [Android App Guide](./android/README.md)
- [Deployment Guide](./docs/deployment/README.md)
