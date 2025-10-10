# React Application with Documentation and User Management

A modern React application built with TypeScript, Vite, and Tailwind CSS featuring two main pages: Documentation and User Management.

## Features

### Pages
- **Documentation Page**: Comprehensive documentation with API examples and guides
- **User Management Page**: Full CRUD operations for user management with external API integration

### Reusable Components
- **Button**: Multiple variants (primary, secondary, outline, ghost) and sizes (sm, md, lg)
- **Title**: Flexible heading component with gradient and muted variants
- **Card**: Content containers with different elevation styles
- **Navigation**: Responsive navigation with active state indicators

### API Service
- **Axios-based API service** with interceptors for error handling
- **Type-safe API calls** with TypeScript interfaces
- **Configurable base URL** and authentication token management
- **Comprehensive error handling** with custom error types

## Technology Stack

- **React 19** with TypeScript
- **Vite** for fast development and building
- **React Router DOM** for client-side routing
- **Tailwind CSS** for styling
- **Axios** for HTTP requests

## Project Structure

```
src/
├── components/
│   ├── Button.tsx          # Reusable button component
│   ├── Title.tsx           # Flexible title component
│   ├── Card.tsx            # Content container component
│   ├── Navigation.tsx      # Navigation header
│   └── index.ts            # Component exports
├── pages/
│   ├── Documentation.tsx    # Documentation page
│   └── User.tsx            # User management page
├── services/
│   └── api.ts              # API service with axios
├── App.tsx                 # Main app with routing
└── main.tsx               # Application entry point
```

## Getting Started

### Prerequisites
- Node.js (v20.19.0 or higher)
- npm or yarn

### Installation

1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

4. Open your browser and navigate to `http://localhost:5173`

### Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## API Integration

The application uses JSONPlaceholder API for demonstration purposes. The API service is configured to:

- Handle different HTTP methods (GET, POST, PUT, DELETE)
- Provide type-safe responses with TypeScript
- Include comprehensive error handling
- Support authentication token management
- Allow base URL configuration

### Example API Usage

```typescript
import apiService from './services/api';

// Fetch users
const response = await apiService.get<User[]>('/users');

// Create a new user
const newUser = await apiService.post<User>('/users', userData);

// Update user
const updatedUser = await apiService.put<User>(`/users/${id}`, userData);

// Delete user
await apiService.delete(`/users/${id}`);
```

## Component Usage

### Button Component
```tsx
<Button variant="primary" size="md" onClick={handleClick}>
  Click me
</Button>
```

### Title Component
```tsx
<Title level="h1" variant="gradient">
  Welcome to our app
</Title>
```

### Card Component
```tsx
<Card variant="elevated" className="hover:shadow-xl">
  <Title level="h3">Card Title</Title>
  <p>Card content goes here</p>
</Card>
```

## Design Principles

The application follows modern design principles:

- **Responsive Design**: Mobile-first approach with Tailwind CSS
- **Accessibility**: Proper focus states and semantic HTML
- **Performance**: Optimized builds with Vite
- **Type Safety**: Full TypeScript implementation
- **Reusability**: Modular component architecture

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## License

This project is open source and available under the MIT License.
