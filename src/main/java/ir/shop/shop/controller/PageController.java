package ir.shop.shop.controller;

import ir.shop.shop.dto.requests.*;
import ir.shop.shop.dto.response.CategoryResponse;
import ir.shop.shop.dto.response.ProductResponse;
import ir.shop.shop.dto.response.UserResponse;
import ir.shop.shop.exception.CategoryHasProductsException;
import ir.shop.shop.exception.CategoryNotFoundException;
import ir.shop.shop.jwt.JwtService;
import ir.shop.shop.repository.RoleRepo;
import ir.shop.shop.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ProductService productService;
    private final AuthService authService;
    private final CartService cartService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final RoleRepo roleRepo;
    private final JwtService jwtService;

    @GetMapping("/")
    public String home() {

        return "index";

    }

    @GetMapping("/products")
    public String products(Model model) {

        model.addAttribute("products", productService.getAllProducts());

        return "products";

    }

    @GetMapping("/login")
    public String loginPage(Model model){

        model.addAttribute("loginRequest", new LoginRequest());

        return "login";
    }

    @PostMapping("/login")
    public String login(LoginRequest request,
                        HttpServletResponse response) {

        String token = authService.login(request);

        Cookie cookie = new Cookie("jwt", token);

        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        // cookie.setSecure(true);

        response.addCookie(cookie);

        String role = jwtService.extractRole(token);

        if ("ROLE_ADMIN".equals(role)) {
            return "redirect:/admin";
        }

        return "redirect:/products";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest" , new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(RegisterRequest request) {

        authService.register(request);

        return "redirect:/verify?email=" + request.getEmail();
    }

    @GetMapping("/verify")
    public String verifyPage(@RequestParam String email,
                             Model model){

        model.addAttribute("verifyRequest",
                VerifyRequest.builder()
                        .email(email)
                        .build());

        return "verify";
    }

    @PostMapping("/verify")
    public String verify(VerifyRequest request){

        authService.verifyCode(
                request.getEmail(),
                request.getCode());

        return "redirect:/login";
    }

    @GetMapping("/me")
    @ResponseBody
    public String me(Authentication authentication) {

        if (authentication == null) {
            return "Not Logged In";
        }

        return authentication.toString();
    }

    @GetMapping("/cart")
    public String cart(Model model) {

        model.addAttribute("cart", cartService.getCart());

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity) {

        cartService.addProduct(
                AddToCartRequest.builder()
                        .productId(productId)
                        .quantity(quantity)
                        .build()
        );

        return "redirect:/products";
    }

    @PostMapping("/cart/remove")
    public String removeProduct(@RequestParam Long cartItemId){

        cartService.removeProduct(cartItemId);

        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(){

        cartService.clearCart();

        return "redirect:/cart";
    }


    @RequestMapping("/admin")
    public String dashboard(){

        return "admin/dashboard";

    }

   @GetMapping("/admin/products")
   public String adminProducts(
           @RequestParam(required = false) Long editId,
           Model model) {


       model.addAttribute(
               "products",
               productService.getAllProducts()
       );


       model.addAttribute(
               "categories",
               categoryService.getAllCategories()
       );


       if(editId != null){

           ProductResponse product =
                   productService.getProductById(editId);


           ProductRequest request =
                   ProductRequest.builder()
                           .id(product.getId())
                           .name(product.getName())
                           .price(product.getPrice())
                           .quantity(product.getQuantity())
                           .description(product.getDescription())
                           .imageUrl(product.getImageUrl())
                           .categoryId(product.getCategoryId())
                           .build();


           model.addAttribute(
                   "productRequest",
                   request
           );


       }else{

           model.addAttribute(
                   "productRequest",
                   new ProductRequest()
           );

       }


       return "admin/products";
   }

   @PostMapping("/admin/products/save")
   public String saveProduct(ProductRequest request){

       System.out.println("REQUEST ID = " + request.getId());
       System.out.println("CATEGORY ID = " + request.getCategoryId());

       if(request.getId() == null){

           productService.createProduct(request);

       }else{

           productService.updateProduct(request.getId(), request);

       }

       return "redirect:/admin/products";
   }
    @PostMapping("/admin/products/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id){

        productService.deleteProduct(id);

        return "redirect:/admin/products";
    }

  @GetMapping("/admin/categories")
  public String adminCategories(
          @RequestParam(required = false) Long editId,
          Model model) {

      model.addAttribute(
              "categories",
              categoryService.getAllCategories()
      );

      if (editId != null) {

          try {

              CategoryResponse category =
                      categoryService.getCategoryById(editId);

              CategoryRequest request =
                      CategoryRequest.builder()
                              .name(category.getName())
                              .build();

              model.addAttribute(
                      "categoryRequest",
                      request
              );

              model.addAttribute(
                      "editId",
                      editId
              );

          } catch (CategoryNotFoundException e) {

              model.addAttribute(
                      "errorMessage",
                      e.getMessage()
              );

              model.addAttribute(
                      "categoryRequest",
                      new CategoryRequest()
              );

              model.addAttribute(
                      "editId",
                      null
              );
          }

      } else {

          model.addAttribute(
                  "categoryRequest",
                  new CategoryRequest()
          );

      }

      return "admin/categories";
  }
    @PostMapping("/admin/categories/save")
    public String saveCategory(
            @RequestParam(required = false) Long id,
            @ModelAttribute CategoryRequest request,
            Model model) {

        try {

            if (id == null) {

                categoryService.addCategory(request);

            } else {

                categoryService.updateCategory(id, request);

            }

            return "redirect:/admin/categories";

        } catch (CategoryHasProductsException e) {

            model.addAttribute(
                    "errorMessage",
                    e.getMessage()
            );

            model.addAttribute(
                    "categories",
                    categoryService.getAllCategories()
            );

            model.addAttribute(
                    "categoryRequest",
                    request
            );

            model.addAttribute(
                    "editId",
                    id
            );

            return "admin/categories";
        }
    }
    @PostMapping("/admin/categories/delete/{id}")
    public String deleteCategory(
            @PathVariable Long id,
            Model model) {

        try {

            categoryService.deleteCategory(id);

            return "redirect:/admin/categories";

        } catch (CategoryHasProductsException e) {

            model.addAttribute(
                    "errorMessage",
                    e.getMessage()
            );

            model.addAttribute(
                    "categories",
                    categoryService.getAllCategories()
            );

            model.addAttribute(
                    "categoryRequest",
                    new CategoryRequest()
            );

            return "admin/categories";
        }
    }

    @GetMapping("/admin/users")
    public String adminUsers(@RequestParam(required = false) Long editId, Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        model.addAttribute(
        "roles",
                roleRepo.findAll()
        );

        if (editId != null) {

            UserResponse user =
                    userService.getUserById(editId);

            UserUpdateRequest request =
                    UserUpdateRequest.builder()
                            .firstname(user.getFirstname())
                            .lastname(user.getLastname())
                            .roleId(user.getRoleId())
                            .build();

            model.addAttribute(
                    "userUpdateRequest",
                    request
            );

            model.addAttribute(
                    "editUserId",
                    editId
            );

        } else {

            model.addAttribute(
                    "userUpdateRequest",
                    new UserUpdateRequest()
            );

            model.addAttribute(
                    "editUserId",
                    null
            );

        }

        return "admin/users";
    }

    @PostMapping("/admin/users/update/{id}")
    public String updateUser(
            @PathVariable Long id,
            UserUpdateRequest request) {

        userService.updateUser(id, request);

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return "redirect:/admin/users";
    }
}
