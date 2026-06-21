package org.sid.bank_account_service.service;

import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;

import org.sid.bank_account_service.entities.BankAccount;

import org.sid.bank_account_service.mappers.AccountMapper;
import org.sid.bank_account_service.repoitories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Override

    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO) {
        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(bankAccountDTO.getBalance())
                .type(bankAccountDTO.getType())
                .currency(bankAccountDTO.getCurrency())
                .build();
        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        /*
        BankAccountResponseDTO bankAccountResponseDTO = BankAccountResponseDTO.builder()
                .id(saveBankAccount.getId())
                .type(saveBankAccount.getType())
                .createdAt(saveBankAccount.getCreatedAt())
                .currency(saveBankAccount.getCurrency())
                .balance(saveBankAccount.getBalance())
                .build();

         */
        BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(saveBankAccount);

        return bankAccountResponseDTO;
    }
    @Override
    public BankAccountResponseDTO updateAccount(String id,BankAccountRequestDTO bankAccountDTO) {

        BankAccount bankAccount = BankAccount.builder()
                .id(id)
                .createdAt(bankAccountRepository.findById(id)
                        .map(BankAccount::getCreatedAt)
                        .orElse(new Date())) // Provide a default value if not found
                .balance(bankAccountDTO.getBalance() != null
                        ? bankAccountDTO.getBalance()
                        : bankAccountRepository.findById(id)
                        .map(BankAccount::getBalance)
                        .orElseThrow(() -> new RuntimeException("balance is null")))
                .type(bankAccountDTO.getType() != null
                        ? bankAccountDTO.getType()
                        : bankAccountRepository.findById(id)
                        .map(BankAccount::getType)
                        .orElseThrow(() -> new RuntimeException("Type is null")))
                .currency(bankAccountDTO.getCurrency() != null
                        ? bankAccountDTO.getCurrency()
                        : bankAccountRepository.findById(id)
                        .map(BankAccount::getCurrency)
                        .orElseThrow(() -> new RuntimeException("Currency is null")))
                .build();
        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(saveBankAccount);

        return bankAccountResponseDTO;
    }
}
