# Getting Started with Beverages Order Management System

This guide will help you set up and run the complete Beverages Order Management System.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+** (for Spring Boot backend)
- **Node.js 18+** and npm (for React web admin)
- **Docker** and **Docker Compose** (for MongoDB)
- **Git** (optional, for version control)

## Quick Start (5 minutes)

### Step 1: Start MongoDB

```bash
# From the project root directory
docker-compose up -d
```

This will start:
- MongoDB on `localhost:27017`
- Mongo Express (web UI) on `http://localhost:8081`

**MongoDB Credentials:**
- Username: `admin`
- Password: `admin123`

### Step 2: Start the Backend

```bash
cd backend

# Make gradlew executable (Linux/Mac)
chmod +x gradlew

# Run the backend
./gradlew bootRun
```

The backend will start on `http://localhost:8080`

**What happens on first run:**
- Creates database tables
- Seeds the database with sample data (users and beverages)
- Initializes JWT security

### Step 3: Start the Web Admin

Open a new terminal:

```bash
cd web-admin

# Install dependencies (first time only)
npm install

# Start the development server
npm run dev
```

The web admin will start on `http://localhost:5173`

### Step 4: Login

Open your browser and navigate to `http://localhost:5173`

**Demo Accounts:**

| Role | Email | Password |
|------|-------|----------|
| **Admin** | admin@company.com | admin123 |
| **Employee** | employee@company.com | employee123 |
| **Worker** | worker@company.com | worker123 |
| **Management** | manager@company.com | manager123 |

## Testing the System

### As an Admin:
1. Login with admin credentials
2. View the dashboard
3. Navigate to Orders to see all system orders
4. (Future) Manage users and beverages

### As an Employee:
1. Login with employee credentials
2. View available beverages
3. Place an order
4. Track order status in real-time

### As a Worker:
1. Login with worker credentials
2. View pending orders queue
3. Update order status (Pending → Preparing → Ready → Delivered)
4. See real-time order updates

### As Management:
1. Login with manager credentials
2. View analytics dashboard
3. (Future) Generate reports
4. Export data to Excel/PDF

## Verify Everything is Working

### 1. Check MongoDB Connection

Visit Mongo Express: `http://localhost:8081`
- Login with admin/admin123
- You should see the `beverages_db` database
- Collections: users, beverages, orders

### 2. Test Backend API

```bash
# Health check (should return 401 - authentication required)
curl http://localhost:8080/api/v1/orders

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@company.com","password":"admin123"}'

# You should get a response with a JWT token
```

### 3. Test Real-time Updates

1. Open two browser windows side by side
2. Login as Worker in one window
3. Login as Employee in the other
4. Employee: Place an order
5. Worker: Should see the order appear in real-time
6. Worker: Update the order status
7. Employee: Should see the status update in real-time

## Project Structure

```
company_coffee_api/
├── backend/                 # Spring Boot Backend (Port 8080)
│   ├── src/main/kotlin/
│   │   └── com/company/beverages/
│   │       ├── controller/   # REST API endpoints
│   │       ├── service/      # Business logic
│   │       ├── repository/   # Database access
│   │       ├── model/        # Data models
│   │       ├── security/     # JWT authentication
│   │       └── config/       # Spring configuration
│   └── build.gradle.kts
│
├── web-admin/               # React Web Admin (Port 5173)
│   ├── src/
│   │   ├── api/            # API client
│   │   ├── store/          # Redux state management
│   │   ├── pages/          # Page components
│   │   └── types/          # TypeScript types
│   └── package.json
│
├── docker-compose.yml       # MongoDB setup
└── README.md
```

## API Documentation

### Authentication Endpoints

```
POST /api/v1/auth/login
POST /api/v1/auth/register
```

### Order Endpoints

```
GET    /api/v1/orders              # Get all orders (Admin/Management/Worker)
GET    /api/v1/orders/my-orders    # Get user's orders (Employee)
GET    /api/v1/orders/{id}         # Get order by ID
POST   /api/v1/orders              # Create new order (Employee)
PUT    /api/v1/orders/{id}/status  # Update order status (Worker/Admin)
DELETE /api/v1/orders/{id}         # Cancel order (Employee)
GET    /api/v1/orders/pending      # Get pending orders (Worker/Admin)
```

### WebSocket

```
ws://localhost:8080/ws

Topics:
- /topic/orders          # Broadcast all order updates
- /user/queue/orders     # User-specific order updates
```

## Development Workflow

### Backend Development

```bash
cd backend

# Run tests
./gradlew test

# Build
./gradlew build

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Web Admin Development

```bash
cd web-admin

# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

## Troubleshooting

### MongoDB Connection Issues

**Problem:** Backend can't connect to MongoDB

**Solution:**
```bash
# Check if MongoDB is running
docker ps

# Restart MongoDB
docker-compose restart mongodb

# Check MongoDB logs
docker logs beverages-mongodb
```

### Port Already in Use

**Problem:** Port 8080 or 5173 is already in use

**Solution:**
```bash
# Find and kill process using port 8080 (Backend)
lsof -ti:8080 | xargs kill -9

# Find and kill process using port 5173 (Frontend)
lsof -ti:5173 | xargs kill -9
```

### CORS Errors

**Problem:** CORS errors when calling API from web admin

**Solution:** The backend is already configured to allow requests from `localhost:5173`. If using a different port, update `backend/src/main/resources/application.yml`:

```yaml
cors:
  allowed-origins: http://localhost:YOUR_PORT
```

### Authentication Errors

**Problem:** "JWT token is invalid" or "401 Unauthorized"

**Solution:**
1. Clear browser localStorage
2. Login again
3. Check that JWT_SECRET in application.yml matches

## Next Steps

### Implement Additional Features

1. **Beverage Management**: Add CRUD endpoints for beverages
2. **User Management**: Admin can create/edit/delete users
3. **Reports**: Generate analytics reports
4. **Image Upload**: Upload beverage images
5. **Export**: Export reports to Excel/PDF
6. **Multi-language**: Add full Arabic support
7. **Push Notifications**: Add Firebase Cloud Messaging
8. **Mobile App**: Build the Android app

### Deploy to Production

1. **Backend**:
   - Build JAR: `./gradlew build`
   - Deploy to AWS/Heroku/DigitalOcean
   - Use production MongoDB (MongoDB Atlas)

2. **Web Admin**:
   - Build: `npm run build`
   - Deploy to Vercel/Netlify
   - Update API URL in `.env.production`

## Support

For issues or questions:
- Check the main [README.md](./README.md)
- Review the implementation plan at `.claude/plans/`
- Contact the development team

## License

Proprietary - Company Internal Use Only
