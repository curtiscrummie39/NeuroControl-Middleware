# Product Management

This module provides product management functionality for the NeuroControl Middleware system.

## Overview

The product management module allows you to create, read, update, and delete (CRUD) product information. Products can represent different types of devices that the middleware supports, such as headsets, wheelchairs, or any other controllable device.

## Classes

### Product

The `Product` class represents a device or product in the system.

**Properties:**
- `id` - Unique identifier for the product
- `name` - Name of the product
- `description` - Description of the product
- `type` - Type of product (e.g., "HEADSET", "WHEELCHAIR")
- `active` - Boolean flag indicating if the product is active

**Example:**
```java
Product headset = new Product(
    "1",
    "Neurosky Mindwave2",
    "Brain-computer interface headset",
    "HEADSET"
);
```

### ProductManager

The `ProductManager` class provides methods to manage a collection of products.

**Key Methods:**
- `createProduct(Product product)` - Create a new product
- `getProduct(String id)` - Get a product by ID
- `getAllProducts()` - Get all products
- `getActiveProducts()` - Get only active products
- `updateProduct(Product product)` - Update an existing product
- `deleteProduct(String id)` - Delete a product by ID
- `productExists(String id)` - Check if a product exists
- `getProductCount()` - Get the total number of products

## Usage

### Basic Example

```java
// Create a ProductManager instance
ProductManager productManager = new ProductManager();

// Create a product
Product headset = new Product(
    "1",
    "Neurosky Mindwave2",
    "Brain-computer interface headset",
    "HEADSET"
);

// Add the product
productManager.createProduct(headset);

// Retrieve the product
Product retrieved = productManager.getProduct("1");
System.out.println(retrieved);
```

### Managing Multiple Products

```java
ProductManager manager = new ProductManager();

// Create multiple products
manager.createProduct(new Product("1", "Headset A", "Description A", "HEADSET"));
manager.createProduct(new Product("2", "Wheelchair B", "Description B", "WHEELCHAIR"));

// Get all products
List<Product> allProducts = manager.getAllProducts();
System.out.println("Total products: " + allProducts.size());

// Get only active products
List<Product> activeProducts = manager.getActiveProducts();
```

### Updating Products

```java
// Get a product
Product product = manager.getProduct("1");

// Update its properties
product.setName("Updated Name");
product.setDescription("Updated Description");

// Save the changes
manager.updateProduct(product);
```

### Deactivating Products

```java
// Get a product
Product product = manager.getProduct("1");

// Deactivate it
product.setActive(false);
manager.updateProduct(product);

// Now it won't appear in getActiveProducts()
```

## Running the Example

A complete usage example is available in `examples/ProductUsageExample.java`. To run it:

```bash
# Compile
javac -cp app/src/main/java examples/ProductUsageExample.java

# Run
java -cp .:app/src/main/java examples.ProductUsageExample
```

## Testing

Unit tests are provided for both `Product` and `ProductManager` classes:
- `app/src/test/java/com/example/wrappercore/product/ProductTest.java`
- `app/src/test/java/com/example/wrappercore/product/ProductManagerTest.java`

Run tests using:
```bash
# Using Gradle
./gradlew test

# Using Maven
mvn test
```

## Integration with WrapperCore

The product management system can be integrated with the main WrapperCore class to manage different device configurations. For example, you could store different wheelchair models or headset configurations as products and load them dynamically based on user selection.

## Future Enhancements

Potential improvements for this module:
- Persistence layer (database integration)
- REST API endpoints for product management
- Product validation and business rules
- Product categories and hierarchies
- Search and filtering capabilities
