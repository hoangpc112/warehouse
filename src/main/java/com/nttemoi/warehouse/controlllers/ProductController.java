package com.nttemoi.warehouse.controlllers;

import com.nttemoi.warehouse.dtos.ProductDTO;
import com.nttemoi.warehouse.entities.Product;
import com.nttemoi.warehouse.entities.ProductBom;
import com.nttemoi.warehouse.entities.Supplier;
import com.nttemoi.warehouse.services.impl.ProductBomServiceImpl;
import com.nttemoi.warehouse.services.impl.ProductServiceImpl;
import com.nttemoi.warehouse.services.impl.SupplierServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping ("/product")
public class ProductController {

    private final ProductServiceImpl productService;
    private final ProductBomServiceImpl productBomService;
    private final SupplierServiceImpl supplierService;

    public ProductController (SupplierServiceImpl supplierService, ProductServiceImpl productService, ProductBomServiceImpl productBomService) {
        this.supplierService = supplierService;
        this.productService = productService;
        this.productBomService = productBomService;
    }

    @GetMapping
    public String getAll (Model model,
                          @RequestParam (required = false) Optional <String> keyword,
                          @RequestParam (defaultValue = "1") int page,
                          @RequestParam (defaultValue = "10") int size,
                          @RequestParam (defaultValue = "asc") String order,
                          @RequestParam (defaultValue = "id") String orderBy) {
        Page <Product> productPage;

        if (keyword.isPresent()) {
            productPage = productService.findAllByKeyword("%" + keyword.get() + "%", page - 1, size, order, orderBy);
            model.addAttribute("keyword", keyword.get());
        }
        else {
            productPage = productService.findAll(page - 1, size, order, orderBy);
        }

        if (!orderBy.equals("id")) {
            model.addAttribute("orderBy", orderBy);
            model.addAttribute("order", order);
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber() + 1);
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("pageSize", size);

        return "show-product";
    }

    @GetMapping ("/detail/{id}")
    public String getAllBom (Model model,
                             @PathVariable Long id,
                             @RequestParam (required = false) Optional <String> keyword,
                             @RequestParam (defaultValue = "1") int page,
                             @RequestParam (defaultValue = "10") int size,
                             @RequestParam (defaultValue = "asc") String order,
                             @RequestParam (defaultValue = "id") String orderBy) {
        Page <ProductBom> productBomPage;

        if (keyword.isPresent()) {
            productBomPage = productBomService.findAllByKeyword(id, "%" + keyword.get() + "%", page - 1, size, order, orderBy);
            model.addAttribute("keyword", keyword.get());
        }
        else {
            productBomPage = productBomService.findAll(id, page - 1, size, order, orderBy);
        }

        if (!orderBy.equals("id")) {
            model.addAttribute("order", order);
            model.addAttribute("orderBy", orderBy);
        }

        model.addAttribute("productBomList", productBomPage.getContent());
        model.addAttribute("currentPage", productBomPage.getNumber() + 1);
        model.addAttribute("totalItems", productBomPage.getTotalElements());
        model.addAttribute("totalPages", productBomPage.getTotalPages());
        model.addAttribute("pageSize", size);

        return "show-productbom";
    }


    @GetMapping ("/new")
    public String addProduct (Model model) {
        ProductDTO productDTO = new ProductDTO();
        List <Supplier> suppliers = supplierService.findAll();

        model.addAttribute("suppliers", suppliers);
        model.addAttribute("productDTO", productDTO);
        return "add-product";
    }

    @PostMapping ("/save")
    public String saveProduct (@ModelAttribute ProductDTO productDTO,
                               RedirectAttributes redirectAttributes,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("suppliers", supplierService.findAll());
            return "add-product";
        }

        try {
            Product product = new Product();
            if (Optional.ofNullable(productDTO.getId()).isPresent()) {
                product.setId(productDTO.getId());
            }
            product.setName(productDTO.getName().trim());
            product.setSize(productDTO.getSize());
            product.setType(productDTO.getType().trim());
            product.setSupplier(supplierService.findById(productDTO.getSupplierId()));
            product.setUnit(productDTO.getUnit().trim());
            product.setStatus(productDTO.getStatus());
            product.setWeight(productDTO.getWeight());

            List <ProductBom> productBomList = Optional.ofNullable(productDTO.getProductbomlist())
                    .orElseGet(ArrayList::new);

            List <ProductBom> filteredProductBomList = productBomList.stream()
                    .filter(productBom -> Optional.ofNullable(productBom.getName()).filter(name -> !name.isEmpty()).isPresent())
                    .collect(Collectors.toList());

            filteredProductBomList.forEach(productBom -> productBom.setProduct(product));

            product.setProductbomlist(filteredProductBomList);


            productService.save(product);

            redirectAttributes.addFlashAttribute("message", "Product has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/product/new";
        }
        return "redirect:/product";
    }

    @GetMapping ("/{id}")
    public String editProductAndProductBom (@PathVariable Long id,
                                            Model model) {
        Product existingProduct = productService.findById(id);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(existingProduct.getId());
        productDTO.setName(existingProduct.getName());
        productDTO.setSize(existingProduct.getSize());
        productDTO.setType(existingProduct.getType());
        productDTO.setSupplierId(existingProduct.getSupplier().getId());
        productDTO.setUnit(existingProduct.getUnit());
        productDTO.setStatus(existingProduct.getStatus());
        productDTO.setWeight(existingProduct.getWeight());
        productDTO.setProductbomlist(existingProduct.getProductbomlist());

        List <Supplier> suppliers = supplierService.findAll();

        model.addAttribute("suppliers", suppliers);
        model.addAttribute("productDTO", productDTO);
        return "add-product";
    }

    @GetMapping ("/delete/{id}")
    public String deleteProduct (@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The Product has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/product";
    }
}