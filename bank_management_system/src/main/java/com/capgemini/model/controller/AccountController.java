package com.capgemini.model.controller;

import com.capgemini.model.entity.Account;
import com.capgemini.model.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public Map<String, Object> createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/{id}")
	public Account getAccountById(@PathVariable int id) {
		return accountService.getAccountById(id);
	}

	@GetMapping
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}

	@PutMapping("/{id}")
	public Map<String, String> updateAccount(@PathVariable int id, @RequestBody Account account) {
		return accountService.updateAccount(id, account);
	}

	@DeleteMapping("/{id}")
	public Map<String, String> deleteAccount(@PathVariable int id) {
		return accountService.deleteAccount(id);
	}

	@PostMapping("/deposit")
	public Map<String, Object> deposit(@RequestBody Map<String, Integer> request) {
		return accountService.deposit(request.get("accountId"), request.get("amount"));
	}

	@PostMapping("/withdraw")
	public Map<String, Object> withdraw(@RequestBody Map<String, Integer> request) {
		return accountService.withdraw(request.get("accountId"), request.get("amount"));
	}
}