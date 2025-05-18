package com.scaler.services;

import com.scaler.exceptions.UnauthorizedAccessException;
import com.scaler.exceptions.UserNotFoundException;
import com.scaler.models.User;
import com.scaler.models.UserType;
import com.scaler.models.WaitListPosition;
import com.scaler.repositories.UserRepository;
import com.scaler.repositories.WaitListPositionRepository;

import java.util.*;

public class WaitingListServiceImpl implements WaitListService {
    private UserRepository userRepo;
    private WaitListPositionRepository waitingRepo;

    public WaitingListServiceImpl(UserRepository userRepo, WaitListPositionRepository waitingRepo) {
        this.userRepo = userRepo;
        this.waitingRepo = waitingRepo;
    }

    @Override
    public int addUserToWaitList(long userId) throws UserNotFoundException {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        List<WaitListPosition> list= waitingRepo.findAll();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUser().getId()==userId){
                return i+1;
            }
        }
        WaitListPosition position = new WaitListPosition();
        position.setUser(user.get());
        position.setInsertedAt(new Date());
        waitingRepo.save(position);
        return waitingRepo.findAll().size();
    }

    @Override
    public int getWaitListPosition(long userId) throws UserNotFoundException {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        List<WaitListPosition> all = waitingRepo.findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getUser().getId() == userId) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public void updateWaitList(long adminUserId, int numberOfSpots) throws UserNotFoundException, UnauthorizedAccessException {
        Optional<User> user = userRepo.findById(adminUserId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        if (user.get().getUserType() != UserType.ADMIN) {
            throw new UnauthorizedAccessException("Access Denied");
        }
        List<WaitListPosition> list = waitingRepo.findAll();
        if (list.size() < numberOfSpots) {
            list.forEach((position) -> {
                waitingRepo.delete(position);
            });
        } else {
            for (int i = 0; i < numberOfSpots; i++) {
                waitingRepo.delete(list.get(i));

            }
        }
    }
}
