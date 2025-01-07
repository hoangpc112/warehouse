package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.entities.Product;
import com.nttemoi.warehouse.entities.ProductBom;
import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.services.impl.ProductBomServiceImpl;
import com.nttemoi.warehouse.services.impl.ProductServiceImpl;
import com.nttemoi.warehouse.services.impl.SupplierServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceImpl productService;
    private final ProductBomServiceImpl productbomService;
    private final SupplierServiceImpl supplierService;

    public ProductController(SupplierServiceImpl supplierService, ProductServiceImpl productService, ProductBomServiceImpl productbomService) {
        this.supplierService = supplierService;
        this.productService = productService;
        this.productbomService = productbomService;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) String keyword,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(required = false) String order,
                         @RequestParam(required = false) String orderBy) {
        Page<Product> pageTuts;
        System.out.println(page + " " + size);
        if (keyword == null) {
            if (order != null) {
                pageTuts = productService.findAllAndSort(page - 1, size, order, orderBy);
            } else {
                pageTuts = productService.findAll(page - 1, size);
            }
        } else {
            if (order != null) {
                pageTuts = productService.findByKeywordAndSort(keyword, page - 1, size, order, orderBy);
            } else {
                pageTuts = productService.findByKeyword(keyword, page - 1, size);
            }
            model.addAttribute("keyword", keyword);
        }
        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("orderBy", orderBy);
        }

        model.addAttribute("products", pageTuts.getContent());
        model.addAttribute("currentPage", pageTuts.getNumber() + 1);
        model.addAttribute("totalItems", pageTuts.getTotalElements());
        model.addAttribute("totalPages", pageTuts.getTotalPages());
        model.addAttribute("pageSize", size);

        return "show-product";
    }

    @GetMapping("/detail/{id}")
    public String getAllBom(Model model, @PathVariable Long id,
                            @RequestParam(required = false) String keyword,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) String order,
                            @RequestParam(required = false) String orderBy) {
        Page<ProductBom> pageTuts;
        if (keyword == null) {
            if (order != null) {
                pageTuts = productbomService.findAllByProductIdAndSort(id, page - 1, size, order, orderBy);
            } else {
                pageTuts = productbomService.findAllByProductId(id, page - 1, size);
            }
        } else {
            if (order != null) {
                pageTuts = productbomService.findAllByProductIdAndSort(id, page - 1, size, order, orderBy);
            } else {
                pageTuts = productbomService.findByKeywordAndProductId(keyword, id, page - 1, size);
            }
            model.addAttribute("keyword", keyword);
        }
        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("orderBy", orderBy);
        }
        model.addAttribute("productboms", pageTuts.getContent());
        model.addAttribute("currentPage", pageTuts.getNumber() + 1);
        model.addAttribute("totalItems", pageTuts.getTotalElements());
        model.addAttribute("totalPages", pageTuts.getTotalPages());
        model.addAttribute("pageSize", size);
        return "show-productbom";
    }

    @GetMapping("/new")
    public String addProduct(Model model) {
        Product product = new Product();
        product.setPublished(true);
        List<Supplier> suppliers = supplierService.findAll();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("product", product);
        return "add-product";
    }

    @PostMapping("/save")
    public String saveProduct(Product product,
                              RedirectAttributes redirectAttributes) {

        try {
            if (product.getProductbomlist() != null) {
                for (ProductBom productbom : product.getProductbomlist()) {
                    productbom.setProduct(product);
                }
            }
            productService.save(product);

            redirectAttributes.addFlashAttribute("message", "The Product has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save the product: " + e.getMessage());
            return "redirect:/product/new";
        }
        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String editProductAndProductbom(@PathVariable("id") Long id,
                                           Model model) {
        Product existingProduct = productService.findById(id);
        List<ProductBom> productbomList = existingProduct.getProductbomlist();
        if (productbomList == null) {
            productbomList = new ArrayList<>();
        }
        List<Supplier> suppliers = supplierService.findAll();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("product", existingProduct);
        model.addAttribute("pageTitle", "Edit Supplier (ID: " + id + ")");
        model.addAttribute("productbomList", productbomList);
        return "add-product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "The Product with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/product";
    }

    @GetMapping("/submit/{id}")
    public String submitProduct(@PathVariable("id") Long id, @ModelAttribute Product product,
                                RedirectAttributes redirectAttributes) {
        try {
            if (product.getProductbomlist() != null) {
                for (ProductBom productbom : product.getProductbomlist()) {
                    productbom.setProduct(product);
                    productbomService.save(productbom);
                }
            }
            productService.save(product);
            redirectAttributes.addFlashAttribute("message", "Product and BOM have been submitted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to submit the product: " + e.getMessage());
        }
        return "redirect:/product";
    }

    @GetMapping("/{id}/published/{status}")
    public String updateProductPublishedStatus(@PathVariable("id") Long id,
                                               @PathVariable("status") boolean published,
                                               RedirectAttributes redirectAttributes) {
        try {
            productService.updatePublishedStatus(id, published);
            String status = published ? "published" : "disabled";
            String message = "The Product id=" + id + " has been " + status;

            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/product";
    }
}
