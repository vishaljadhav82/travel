package com.travel.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel.web.model.Farmer;
import com.travel.web.model.Product;
import com.travel.web.repository.FarmerRepository;
import com.travel.web.repository.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private FarmerRepository farmerRepository;
    
    @GetMapping("/upload")
    public String view() {
    	return "/product/form";
    }

    @PostMapping("/uploadProduct")
    public String uploadProduct(@RequestParam("farmerEmail") String farmerEmail,
                                @RequestParam("farmerPhone") String farmerPhone,
                                @RequestParam("productName") String productName,
                                @RequestParam("category") String category,
                                @RequestParam("description") String description,
                                @RequestParam("price") Double price,
                                @RequestParam("quantity") Integer quantity,
                                @RequestParam("unitOfMeasurement") String unitOfMeasurement,
                                @RequestParam("availability") String availability,
                                @RequestParam("harvestDate") String harvestDate,
                                @RequestParam("productImage") MultipartFile productImage,
                                @RequestParam("certification") String certification) throws IOException {

        // Find the farmer by email and phone
        Farmer farmerOpt = farmerRepository.findByEmailAndMobileNumber(farmerEmail, farmerPhone);
        if (farmerOpt == null) {
            // Handle case where farmer is not found
            return "redirect:/error";
        }

        Farmer farmer = farmerOpt;

        // Save the product image
        String imageUrl = "/static/img/" + productImage.getOriginalFilename();
        File imageFile = new File("src/main/resources/static/img/" + productImage.getOriginalFilename());
        productImage.transferTo(imageFile);

        // Create and save the product
        Product product = new Product();
        product.setProductName(productName);
        product.setCategory(category);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setUnitOfMeasurement(unitOfMeasurement);
        product.setAvailability(availability);
        product.setHarvestDate(java.sql.Date.valueOf(harvestDate)); // Ensure the date is in proper format
        product.setProductImageUrl(imageUrl);
        product.setCertification(certification);
        product.setFarmer(farmer);

        productRepository.save(product);

        return "redirect:/book/success";
    }

    @GetMapping("/products")
    public String listProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value = "category", required = false) String category,
                               Model model) {
        
        // Create Pageable object for pagination and sorting (by productName in ascending order)
        Pageable pageable = PageRequest.of(page, size);
        
        Page<Product> productPage;
        
        if (category != null && !category.isEmpty()) {
            // Fetch products by category with pagination
            productPage = productRepository.findByCategory(category, pageable);
        } else {
            // Fetch all products with pagination
            productPage = productRepository.findAll(pageable);
        }

        // Add products to the model
        model.addAttribute("productPage", productPage);
        model.addAttribute("category", category);  // To keep the selected category in the form
        
        // Return the view
        return "product/productList";
    }
    @GetMapping("/list")
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product/product-list";
    }

    // Search products by product name or category
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productRepository.findByProductNameContainingOrCategoryContaining(query, query);
        model.addAttribute("products", products);
        return "product/product-list";
    }

    // Show product creation form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/product-form";
    }

    // Create a new product
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("message", "Product added successfully!");
        return "redirect:/products";
    }

    // Show product update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "product/product-form";
    }

    // Update product
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
        return "redirect:/products";
    }

    // Delete product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
        return "redirect:/products";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("farmer", new Farmer());
        return "farmer/registration"; // Create farmer/register.html
    }

}
