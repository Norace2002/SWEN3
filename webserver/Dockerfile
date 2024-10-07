# Build stage
FROM node:16-alpine AS build

# Set working directory inside the container
WORKDIR /app

# Copy package.json and yarn.lock to install dependencies
COPY ./package.json ./yarn.lock ./

# install missing babel - dependiency
RUN yarn add @babel/plugin-proposal-private-property-in-object --dev

# Install all dependencies
RUN yarn install --frozen-lockfile

# Copy the entire source code (including public and src folders)
COPY ./src ./src
COPY ./public ./public

# Build the React application
RUN yarn build

# Production stage
FROM nginx:alpine

# Copy built files from the build stage to Nginx's HTML directory
COPY --from=build /app/build /usr/share/nginx/html

# Copy custom Nginx configuration to override default config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80 for Nginx
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
