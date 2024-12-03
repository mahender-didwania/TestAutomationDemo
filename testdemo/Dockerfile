# Use the official Cypress image as the base image
FROM cypress/included:13.14.2

# Copy the e2e folder from the local machine into the container
COPY src/test/e2e /e2e

# Set the working directory to /e2e
WORKDIR /e2e

# Install Cypress dependencies
RUN npm install
