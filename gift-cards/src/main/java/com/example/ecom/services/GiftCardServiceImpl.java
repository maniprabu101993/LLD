package com.example.ecom.services;

import com.example.ecom.exceptions.GiftCardDoesntExistException;
import com.example.ecom.exceptions.GiftCardExpiredException;
import com.example.ecom.models.GiftCard;
import com.example.ecom.models.LedgerEntry;
import com.example.ecom.models.TransactionType;
import com.example.ecom.repositories.GiftCardRepository;
import com.example.ecom.repositories.LedgerEntryRepository;
import com.example.ecom.utils.GiftCardUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GiftCardServiceImpl implements GiftCardService {
    GiftCardRepository giftCardRepository;
    LedgerEntryRepository ledgerEntryRepository;

    public GiftCardServiceImpl(GiftCardRepository giftCardRepository, LedgerEntryRepository ledgerEntryRepository) {
        this.giftCardRepository = giftCardRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Override
    public GiftCard createGiftCard(double amount) {
        GiftCard giftCard = new GiftCard();
        giftCard.setAmount(amount);
        giftCard.setCreatedAt(new Date());
        giftCard.setGiftCardCode(GiftCardUtils.generateGiftCardCode());
        giftCard.setExpiresAt(GiftCardUtils.getExpirationDate(new Date()));
        List<LedgerEntry> list = new ArrayList<>();
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setCreatedAt(new Date());
        ledgerEntry.setAmount(amount);
        ledgerEntry.setTransactionType(TransactionType.CREDIT);
        list.add(ledgerEntry);
        giftCard.setLedger(list);
        return giftCardRepository.save(giftCard);
    }

    @Override
    public GiftCard redeemGiftCard(int giftCardId, double amountToRedeem) throws GiftCardDoesntExistException, GiftCardExpiredException {
        GiftCard gf = giftCardRepository.findById(giftCardId).orElseThrow(() -> new GiftCardDoesntExistException("Gift card does not exist"));
        if (gf.getExpiresAt().compareTo(new Date()) < 0) {
            throw new GiftCardExpiredException("Gift card has expired");
        }
        double remainingAmount;
        if (gf.getAmount() <= amountToRedeem) {
            remainingAmount = 0;
        } else {
            remainingAmount = gf.getAmount() - amountToRedeem;
        }
        gf.setAmount(remainingAmount);
        LedgerEntry entry = new LedgerEntry();
        entry.setTransactionType(TransactionType.DEBIT);
        entry.setCreatedAt(new Date());
        entry.setAmount(remainingAmount == 0 ? gf.getAmount() : amountToRedeem);
        gf.getLedger().add(entry);
        giftCardRepository.save(gf);
        return gf;
    }
}
