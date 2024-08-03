package com.BankApp.API;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BankApp.config.InsufficientBalanceException;
import com.BankApp.config.SecurityUser;
import com.BankApp.dto.DepositDTO;
import com.BankApp.dto.ReceiverDTO;
import com.BankApp.dto.TransferDTO;
import com.BankApp.dto.WithdrawDTO;
import com.BankApp.entities.Account;
import com.BankApp.entities.TransactionHistoryDetails;
import com.BankApp.entities.TransactionType;
import com.BankApp.exceptions.ResourceNotFoundException;
import com.BankApp.service.AccountService;
import com.BankApp.service.TransactionHistoryService;
import com.BankApp.service.UserService;

import jakarta.validation.Valid;

//@RestController
@Controller
@RequestMapping(path="api/transactions")
//@PreAuthorize("hasAuthority('ROLE_MGR') or hasAuthority('ROLE_CLERK')")
public class AccountTransactionAPI {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionHistoryService transactionHistoryService;
	
	@Autowired
    private UserService userService;
	

	@Autowired
	LoginManagementAPI loginManagementAPI;
	
	
	@GetMapping("/user")
    public String user(Model model) {
		return loginManagementAPI.user(model);
	}
	
	public AccountTransactionAPI(AccountService accountService, TransactionHistoryService transactionHistoryService) {
        this.accountService = accountService;
        this.transactionHistoryService = transactionHistoryService;
    }
	
	@GetMapping(path="alltansactionhistory")
	public List<TransactionHistoryDetails> getAllTransactionHistoryDetails(){
		return transactionHistoryService.getAll();
		
	}
	
	
//	@GetMapping(path="tansactionhistory{accountId}")
//	public List<TransactionHistoryDetails> getTransactionHistoryDetialsOfSpacificAccount(@PathVariable(name="accountId") int accountId){
//		return transactionHistoryService.findByFromAccIdOrToAccId(accountId);
//		
//	}
	
	@GetMapping("transactionhistory/{accountId}")
    public String getTransactionHistoryDetailsOfSpecificAccount(@PathVariable(name = "accountId") int accountId, Model model) {
		loginManagementAPI.user(model);
		
	    System.out.println("Fetching transaction history for accountId: " + accountId + " with type: TRANSFER");

        List<TransactionHistoryDetails> transactions = transactionHistoryService.findByFromAccIdOrToAccIdAndTxType(accountId, TransactionType.TRANSFER);
        model.addAttribute("transactions", transactions);
        
        return "payments history"; // The name of your Thymeleaf template
    }
	
	
	
	
	
	
//	//api/transactions/transfer
//	//transfer
//	@PostMapping(path="transfer")
//	public ResponseEntity<String> transfer(@Valid @RequestBody TransferDTO transferDTO , @AuthenticationPrincipal SecurityUser securityUser) {
//
//		//System.out.println("------------: "+securityUser.getUserEntity().getRoles());
//		boolean isAllowedToTransferFund = false;
//		
//		List<String> roles = securityUser.getUserEntity().getRoles();
//		
//		if(transferDTO.getAmount().compareTo(BigDecimal.valueOf(150000))>0) {
//			for(String role: roles) {
//				if(role.equals("ROLE_MGR")) {
//					isAllowedToTransferFund = true;
//				}
//			}
//		}
//		else {
//			isAllowedToTransferFund =true;
//		}
//		
//		//INTERNAL SERVER EXCEPTION 500
////		if(1==1) 
////			throw new RuntimeException();
//		
//		if(isAllowedToTransferFund) {
//		accountService.transfer(transferDTO.getFromAccId(), transferDTO.getToAccId(), transferDTO.getAmount());
//		//int fromAccId, int toAccId, TransactionType txType, LocalDateTime dateTime,String authority
//		transactionHistoryService.addTransactionHistoryDeatil
//		               (new TransactionHistoryDetails(transferDTO.getFromAccId()
//		               , transferDTO.getToAccId(),TransactionType.TRANSFER, LocalDateTime.now(), securityUser.getUserEntity().getUsername(), transferDTO.getAmount()));
//		return ResponseEntity.status(HttpStatus.OK)
//				.body("fund is transfer from account id: " + transferDTO.getFromAccId()+ " to account id: " + transferDTO.getToAccId());
//		}
//		else {
//			throw new org.springframework.security.access.AccessDeniedException("fund trnasfer of more then 1.5L can be initiated by manager");
//		}
//		
//	}
//	
	
	@GetMapping("/transfer")
	public String showTransferPage(Model model) {
	    model.addAttribute("receiverDTO", new ReceiverDTO());
	    model.addAttribute("transferDTO", new TransferDTO());
	    return "transfer";
	}

	@PostMapping(path = "checkReceiver")
	public String checkRecipient(@Valid @ModelAttribute ReceiverDTO receiverDTO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			 model.addAttribute("transferDTO", new TransferDTO());
            return "transfer";
        }

		
		Account toAccount = accountService.findByPhone(receiverDTO.getPhone());
	    TransferDTO transferDTO = new TransferDTO();
	    transferDTO.setPhone(receiverDTO.getPhone());
	    if (toAccount == null) {
	        model.addAttribute("errorMessage", "Account not found");
	    } else {
	        model.addAttribute("receiverName", toAccount.getName());
	    }
	    model.addAttribute("transferDTO", transferDTO); // Ensure transferDTO is populated correctly
	    return "transfer";
	}

	
	@PostMapping(path = "/transfer")
	public String transfer(@Valid @ModelAttribute TransferDTO transferDTO, 
	                       @AuthenticationPrincipal SecurityUser securityUser,
	                       BindingResult bindingResult, Model model) throws MethodArgumentNotValidException {

		
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("transferDTO", transferDTO); // Ensure transferDTO is added back to the model
	        return "transfer"; // Return the name of the Thymeleaf template
	    }

	    boolean isAllowedToTransferFund = isTransferAllowed(transferDTO.getAmount(), securityUser.getUserEntity().getRoles());
	    if (!isAllowedToTransferFund) {
	        model.addAttribute("errors", bindingResult.getAllErrors());
	        return "transfer";
	    }

	    // Check if the phone number exists in the account table
	    Account toAccount = accountService.findByPhone(transferDTO.getPhone());
	    if (toAccount == null) {
	        model.addAttribute("errorMessage", "Account not found");
	        model.addAttribute("receiverDTO", new ReceiverDTO()); // Ensure other necessary attributes are also set
	        model.addAttribute("transferDTO", transferDTO); // Add transferDTO to the model
	        return "transfer";
	    }

	    // Proceed with the transfer logic
	    try {
	    	
	    	transactionHistoryService.addTransactionHistoryDeatil(
	                new TransactionHistoryDetails(
	                    securityUser.getUserEntity().getId(), 
	                    toAccount.getId(), 
	                    TransactionType.TRANSFER, 
	                    LocalDateTime.now(), 
	                    securityUser.getUserEntity().getUsername(),transferDTO.getAmount()
	                )
	            );
	        accountService.transfer(securityUser.getUserEntity().getId(), toAccount.getId(), transferDTO.getAmount());
	        model.addAttribute("successMessage", "Transfer successful");
	    } 
	    
	    catch (Exception e) {
	        model.addAttribute("errorMessage", "Transfer failed: " + e.getMessage());
	    }
	    

	    // Ensure all required attributes are set for rendering the view
	    model.addAttribute("receiverDTO", new ReceiverDTO()); // Reinitialize if needed
	    model.addAttribute("transferDTO", transferDTO); // Add transferDTO to the model

	    return "transfer";
	}



	    private boolean isTransferAllowed(BigDecimal transferAmount, List<String> roles) throws MethodArgumentNotValidException {
	        if (transferAmount.compareTo(BigDecimal.valueOf(150000)) > 0) {
			    for (String role : roles) {
			        if (role.equals("ROLE_MGR")) {
			            return true;
			        }
			    }
			    return false;
			}
	        return true;
	    }
	
	
	
	    // this method to  Check Balance using AccountTransactionAPI controller
	    @GetMapping("/checkBalance")
	    public String checkBalance(@AuthenticationPrincipal SecurityUser securityUser, Model model) {
	        int accountId = securityUser.getUserEntity().getId();
	        Account account = accountService.getById(accountId);

	        if (account != null) {
	            model.addAttribute("account", account);
	            model.addAttribute("balance", account.getBalance());
	        } else {
	            model.addAttribute("errorMessage", "Account not found");
	        }

	        return "check_balance"; // This should match the name of your Thymeleaf template
	    }
	

	
	//api/transactions/deposit
	//deposit
//	@PostMapping(path="deposit")
//	public ResponseEntity<String> deposit(@Valid @RequestBody DepositDTO depositDTO) {
//		
//		accountService.deposit(depositDTO.getAccId(), depositDTO.getAmount());
//		
//		transactionHistoryService.addTransactionHistoryDeatil
//        (new TransactionHistoryDetails(0
//        ,depositDTO.getAccId(),TransactionType.DEPOSIT, LocalDateTime.now(), "raj", depositDTO.getAmount()));
//		
//		return ResponseEntity.status(HttpStatus.OK)
//				.body("fund is deposit to account id: " +depositDTO.getAccId()+ " amount is " + depositDTO.getAmount());
//
//	}	
	    
	    
	    
	@GetMapping("/deposit")
    public String showDepositPage(Model model) {
        model.addAttribute("depositDTO", new DepositDTO());
        return "deposit"; // This should match the name of your Thymeleaf template
    }

    @PostMapping("/deposit")
    public String deposit(@Valid @ModelAttribute("depositDTO") DepositDTO depositDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "deposit";
        }

        Account user = accountService.getById(depositDTO.getAccId());
        if (user == null || !user.getName().equals(depositDTO.getUsername()) || !user.getPhone().equals(depositDTO.getPhone())) {
            model.addAttribute("errorMessage", "Invalid account details");
            return "deposit";
        }

        accountService.deposit(depositDTO.getAccId(), depositDTO.getAmount());

        transactionHistoryService.addTransactionHistoryDeatil(
            new TransactionHistoryDetails(0, depositDTO.getAccId(), TransactionType.DEPOSIT, LocalDateTime.now(), user.getName(), depositDTO.getAmount()));

        model.addAttribute("successMessage", "Funds deposited to account ID: " + depositDTO.getAccId() + " Amount: " + depositDTO.getAmount());
        model.addAttribute("depositDTO", new DepositDTO()); // Clear form data
        return "deposit";
    }


	
	
	
//	//api/transactions/withdraw
//	//withdraw
//	@PostMapping(path="withdraw")
//	public ResponseEntity<String> withdraw(@Valid @RequestBody  WithdrawDTO withdrawDTO, @AuthenticationPrincipal SecurityUser securityUser) {
//			
//		accountService.withdraw(withdrawDTO.getAccId(), withdrawDTO.getAmount());
//		
//		transactionHistoryService.addTransactionHistoryDeatil
//        (new TransactionHistoryDetails(withdrawDTO.getAccId(),
//        		0,TransactionType.WITHDRAW, LocalDateTime.now(), securityUser.getUserEntity().getUsername(), withdrawDTO.getAmount()));
//		
//		return ResponseEntity.status(HttpStatus.OK)
//				.body("fund is withdraw from account id: " +withdrawDTO.getAccId()+ " amount is " + withdrawDTO.getAmount());
//	
//		}

    @GetMapping("/withdraw")
    public String showWithdrawPage(Model model) {
        model.addAttribute("withdrawDTO", new WithdrawDTO());
        return "withdraw";
    }
    
    @PostMapping("/withdraw")
    public String withdraw(@ModelAttribute WithdrawDTO withdrawDTO, BindingResult result, @AuthenticationPrincipal SecurityUser securityUser, Model model) {
        if (result.hasErrors()) {
            return "withdraw";
        }
        
        try {
            accountService.withdraw(withdrawDTO.getAccId(), withdrawDTO.getAmount());

            transactionHistoryService.addTransactionHistoryDeatil(new TransactionHistoryDetails(
                    withdrawDTO.getAccId(),
                    0,
                    TransactionType.WITHDRAW,
                    LocalDateTime.now(),
                    securityUser.getUserEntity().getUsername(),
                    withdrawDTO.getAmount()
            ));

            model.addAttribute("successMessage", "Funds withdrawn successfully from account ID: " + withdrawDTO.getAccId());
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        catch (InsufficientBalanceException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        return "withdraw";
    }

    
    
}
