package examples;

import com.example.wrappercore.product.Product;
import com.example.wrappercore.product.ProductManager;

/**
 * Example demonstrating how to use the Product and ProductManager classes.
 */
public class ProductUsageExample {

  public static void main(String[] args) {
    // Create a ProductManager instance
    ProductManager productManager = new ProductManager();

    // Create some products
    Product headset = new Product(
        "1",
        "Neurosky Mindwave2",
        "Brain-computer interface headset for reading brainwave signals",
        "HEADSET",
        0,
        32
    );

    Product wheelchair = new Product(
        "2",
        "Wheelchair Model X",
        "Advanced electric wheelchair controlled by brain signals",
        "WHEELCHAIR"
    );

    // Add products to the manager
    boolean headsetCreated = productManager.createProduct(headset);
    boolean wheelchairCreated = productManager.createProduct(wheelchair);

    System.out.println("Headset created: " + headsetCreated);
    System.out.println("Wheelchair created: " + wheelchairCreated);

    // Get a specific product
    Product retrievedHeadset = productManager.getProduct("1");
    System.out.println("\nRetrieved product: " + retrievedHeadset);

    // Get all products
    System.out.println("\nAll products:");
    for (Product product : productManager.getAllProducts()) {
      System.out.println("  - " + product.getName() + " (" + product.getType() + ")");
    }

    // Update a product
    wheelchair.setDescription("Advanced electric wheelchair with AI-powered navigation");
    productManager.updateProduct(wheelchair);
    System.out.println("\nUpdated wheelchair: " + productManager.getProduct("2"));

    // Deactivate a product
    wheelchair.setActive(false);
    productManager.updateProduct(wheelchair);

    // Get only active products
    System.out.println("\nActive products:");
    for (Product product : productManager.getActiveProducts()) {
      System.out.println("  - " + product.getName());
    }

    // Get product count
    System.out.println("\nTotal products: " + productManager.getProductCount());

    // Check if a product exists
    System.out.println("Product '1' exists: " + productManager.productExists("1"));
    System.out.println("Product '999' exists: " + productManager.productExists("999"));

    // Delete a product
    boolean deleted = productManager.deleteProduct("1");
    System.out.println("\nProduct '1' deleted: " + deleted);
    System.out.println("Remaining products: " + productManager.getProductCount());
  }
}
