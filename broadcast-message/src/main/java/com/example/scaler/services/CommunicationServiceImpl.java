package com.example.scaler.services;

import com.example.scaler.adapters.EmailAdapter;
import com.example.scaler.adapters.WhatsappAdapter;
import com.example.scaler.exceptions.InvalidBatchException;
import com.example.scaler.exceptions.InvalidUserException;
import com.example.scaler.exceptions.UnAuthorizedAccessException;
import com.example.scaler.models.*;
import com.example.scaler.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    BatchRepository batchRepository;
    LearnerRepository learnerRepository;
    BatchLearnerRepository batchLearnerRepository;
    CommunicationRepository communicationRepository;
    UserRepository userRepository;
    CommunicationLearnerRepository communicationLearnerRepository;
    EmailAdapter emailAdapter;
    WhatsappAdapter whatsappAdapter;


    public CommunicationServiceImpl(BatchRepository batchRepository, UserRepository userRepository, BatchLearnerRepository batchLearnerRepository, WhatsappAdapter whatsappAdapter, EmailAdapter emailAdapter, CommunicationLearnerRepository communicationLearnerRepository, CommunicationRepository communicationRepository) {
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
        this.batchLearnerRepository = batchLearnerRepository;
        this.whatsappAdapter = whatsappAdapter;
        this.emailAdapter = emailAdapter;
        this.communicationLearnerRepository = communicationLearnerRepository;
        this.communicationRepository = communicationRepository;
    }

    @Override
    public Communication broadcastMessage(long batchId, long userId, String message) throws InvalidBatchException, InvalidUserException, UnAuthorizedAccessException {
        Optional<User> userOptinal = userRepository.findById(userId);
        if (userOptinal.isEmpty()) {
            throw new InvalidUserException("User does not exist");
        }
        User userAdmin = userOptinal.get();
        if (!userAdmin.getUserType().equals(UserType.ADMIN)) {
            throw new UnAuthorizedAccessException("Access Denied");
        }
        Optional<Batch> batchOptinal = batchRepository.findById(batchId);
        if (batchOptinal.isEmpty()) {
            throw new InvalidBatchException("Batch does not found");
        }
        Batch batch = batchOptinal.get();

        Communication communication = new Communication();
        communication.setBatch(batch);
        communication.setSentBy(userAdmin);
        communication.setMessage(message);
        communicationRepository.save(communication);

        List<BatchLearner> batchLearners = batchLearnerRepository.findAll();
        List<Learner> learners = new ArrayList<>();

        for (BatchLearner batchLearner : batchLearners) {
            if (batchLearner.getBatch().getId() == batchId) {
                if (batchLearner.getExitDate() == null) {
                    learners.add(batchLearner.getLearner());
                }

            }
        }


        for (Learner learner : learners) {
            CommunicationLearner communicationLearner = new CommunicationLearner();
            communicationLearner.setCommunication(communication);
            try {
                whatsappAdapter.sendWhatsappMessage(learner.getPhoneNumber(), message);
                communicationLearner.setWhatsappDelivered(true);

            } catch (Exception e) {
                communicationLearner.setWhatsappDelivered(false);
            }

            try {
                emailAdapter.sendEmail(learner.getEmail(), message);
                communicationLearner.setEmailDelivered(true);
            } catch (Exception e) {
                communicationLearner.setEmailDelivered(false);
            }

            communicationLearner.setLearner(learner);
            communicationLearnerRepository.save(communicationLearner);
        }
        return communication;
    }
}
