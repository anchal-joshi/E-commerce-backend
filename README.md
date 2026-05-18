# E-commerce-backend
feat: complete Phase 1 - core e-commerce backend (internship-ready)

Auth & Security
- JWT-based register/login with BCrypt password encoding
- Role-based access control (USER, ADMIN)
- Custom JwtFilter, JwtUtil, CustomUserDetailsService
- Stateless session management

Products
- Full CRUD for admin
- Public read endpoints

Cart
- Add, update quantity, remove items
- Auto total price calculation
- Duplicate item quantity merging

Orders
- Place order from cart, auto cart clear
- Order history per user
- Admin order status management (PLACED, SHIPPED, DELIVERED)

Exception Handling
- GlobalExceptionHandler with clean JSON error responses
- Custom exceptions: ResourceNotFoundException,
  UserAlreadyExistsException, InvalidCredentialsException,
  EmptyCartException, UnauthorizedException

Tested all endpoints via Postman including auth, role
protection, validation, and edge cases.
