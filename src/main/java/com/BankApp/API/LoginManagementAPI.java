package com.BankApp.API;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BankApp.dto.UserDTO;
import com.BankApp.entities.TransactionHistoryDetails;
import com.BankApp.entities.TransactionType;
import com.BankApp.entities.UserEntity;
import com.BankApp.service.TransactionHistoryService;
import com.BankApp.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path="api/loginmgt")
//@PreAuthorize("hasAuthority('ROLE_MGR') or hasAuthority('ROLE_CLERK')")
public class LoginManagementAPI {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionHistoryService transactionHistoryService;
	

	
//	  @GetMapping("/login")
//	    public String getRegistrationPage(Model model) {
//	        model.addAttribute("userEntity", new UserContactDetailDTO());
//	        return "signinpage"; // This should be the name of your Thymeleaf template without the .html extension
//	    }
	  

		@GetMapping("/login")
		public String login() {
			return "signinpage";
		}
		
		@GetMapping("/manager")
		public String manager() {
			return "manager";
		}
		
		
		
		
//		@GetMapping("/user")
//	    public String user(Model model) {
//	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	        String username = authentication.getName(); // Retrieve the username of the authenticated user
//	        
//	        int userId = userService.getAuthenticatedUserId(username);
//	        
//	        // Fetch user details using both ID and username
//	        UserEntity userEntity = userService.findByIdAndUsername(userId, username);
//	        
//	        if (userEntity == null) {
//	            // Handle case where user is not found
//	            return "error"; // Redirect to an error page or handle as needed
//	        }
//
//	        // Add user details to the model
//	        model.addAttribute("username", userEntity.getUsername());
//	        model.addAttribute("email", userEntity.getEmail());
//	        model.addAttribute("phone", userEntity.getPhone());
//	        model.addAttribute("address", userEntity.getAddress());
//	        model.addAttribute("accountId", userEntity.getId());
//
//	        return "user"; // This should match your Thymeleaf template name
//	    }
		
//		@GetMapping("/user")
//		public String user(Model model) {
//		    // Get the authenticated user's username
//		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		    String username = authentication.getName();
//		    
//		    // Retrieve the user ID and details
//		    int userId = userService.getAuthenticatedUserId(username);
//		    UserEntity userEntity = userService.findByIdAndUsername(userId, username);
//		    
//		    if (userEntity == null) {
//		        return "error"; // Handle user not found
//		    }
//
//		    // Fetch transactions involving the logged-in user
//		    List<TransactionHistoryDetails> transactions = transactionHistoryService.findByFromAccIdOrToAccId(userId);
//		    
//		    // Extract unique user IDs from transactions
//		    Set<Integer> transactionUserIds = transactions.stream()
//		        .map(tx -> tx.getFromAccId() == userId ? tx.getToAccId() : tx.getFromAccId())
//		        .collect(Collectors.toSet());
//		    
//		    // Fetch the users involved in these transactions
//		    List<UserEntity> transactionUsers = userService.findUsersByIds(transactionUserIds);
//
//		    // Add user details and transaction users to the model
//		    model.addAttribute("username", userEntity.getUsername());
//		    model.addAttribute("email", userEntity.getEmail());
//		    model.addAttribute("phone", userEntity.getPhone());
//		    model.addAttribute("address", userEntity.getAddress());
//		    model.addAttribute("accountId", userEntity.getId());
//		    model.addAttribute("transactionUsers", transactionUsers); // List of users for transaction details
//
//		    return "user"; // Thymeleaf template name to display user list
//		}

		
		@GetMapping("/user")
		public String user(Model model) {
		    // Get the authenticated user's username
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String username = authentication.getName();
		    
		    // Retrieve the user ID and details
		    int userId = userService.getAuthenticatedUserId(username);
		    UserEntity userEntity = userService.findByIdAndUsername(userId, username);
		    
		    if (userEntity == null) {
		        return "error"; // Handle user not found
		    }

		    // Fetch transactions involving the logged-in user
		    List<TransactionHistoryDetails> transactions = transactionHistoryService.findByFromAccIdOrToAccIdAndTxType(userId, TransactionType.TRANSFER);
		    
		    // Extract unique user IDs from transactions where toAccId > 0
		    Set<Integer> transactionUserIds = transactions.stream()
		        .filter(tx -> tx.getToAccId() !=0) 
		        .map(tx -> tx.getFromAccId() == userId ? tx.getToAccId() : tx.getFromAccId())
		        .collect(Collectors.toSet());
		    // Fetch the users involved in these transactions
		    List<UserEntity> transactionUsers = userService.findUsersByIds(transactionUserIds);

		    model.addAttribute("transactionUsers", transactionUsers);
		   
		    // Add user details and transaction users to the model
		    model.addAttribute("username", userEntity.getUsername());
		    model.addAttribute("email", userEntity.getEmail());
		    model.addAttribute("phone", userEntity.getPhone());
		    model.addAttribute("address", userEntity.getAddress());
		    model.addAttribute("accountId", userEntity.getId());

		    return "user"; // Thymeleaf template name to display user list
		}

		
		
		 
		 @Autowired
		    public LoginManagementAPI(UserService userService) {
		        this.userService = userService;
		    }

		    @GetMapping("/registration")
		    public String showRegistrationForm(Model model) {
		        model.addAttribute("userDTO", new UserDTO());
		        return "registration";
		    }
		    @PostMapping("/registration")
		    public String registerNewUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
		        if (bindingResult.hasErrors()) {
		            model.addAttribute("userDTO", userDTO);
		            return "registration";
		        }

		        if (userService.isUserExistByEmail(userDTO.getEmail())) {
		            model.addAttribute("error", "Email already registered. Please login.");
		            model.addAttribute("userDTO", userDTO);
		            return "registration";
		        }

		        if (userService.isUserExistByPhone(userDTO.getPhone())) {
		            model.addAttribute("error", "Phone number already registered. Please login.");
		            model.addAttribute("userDTO", userDTO);
		            return "registration";
		        }

		        try {
		            userService.addUserEntitys(userDTO);
		        } catch (Exception e) {
		            model.addAttribute("error", "An unexpected error occurred. Please try again.");
		            model.addAttribute("userDTO", userDTO);
		            return "registration";
		        }

		        return "redirect:/api/loginmgt/login";
		    }

		  
		 
}
