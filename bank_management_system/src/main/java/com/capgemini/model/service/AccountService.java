package com.capgemini.model.service;

import com.capgemini.model.entity.Account;
import com.capgemini.model.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Map<String, Object> createAccount(Account account) {

		if (account.getBalance() < 0) {
			throw new RuntimeException("Balance cannot be negative");
		}

		Account saved = accountRepository.save(account);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Account created successfully");
		response.put("accountId", saved.getId());

		return response;
	}

	public Account getAccountById(int id) {
		return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
	}

	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	public Map<String, String> updateAccount(int id, Account updated) {

		Account existing = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

		if (updated.getBalance() < 0) {
			throw new RuntimeException("Balance cannot be negative");
		}

		existing.setAccountHolderName(updated.getAccountHolderName());
		existing.setBalance(updated.getBalance());

		accountRepository.save(existing);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Account updated successfully");

		return response;
	}

	public Map<String, String> deleteAccount(int id) {

		Account existing = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

		accountRepository.delete(existing);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Account deleted successfully");

		return response;
	}

	public Map<String, Object> deposit(int accountId, int amount) {

		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not found"));

		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}

		account.setBalance(account.getBalance() + amount);
		accountRepository.save(account);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Deposit successful");
		response.put("updatedBalance", account.getBalance());

		return response;
	}

	public Map<String, Object> withdraw(int accountId, int amount) {

		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not found"));

		if (amount <= 0) {
			throw new RuntimeException("Invalid amount");
		}

		if (amount > account.getBalance()) {
			throw new RuntimeException("Insufficient balance");
		}

		account.setBalance(account.getBalance() - amount);
		accountRepository.save(account);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Withdrawal successful");
		response.put("updatedBalance", account.getBalance());

		return response;
	}
}