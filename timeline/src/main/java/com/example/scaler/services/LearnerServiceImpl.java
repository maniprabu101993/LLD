package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidLearnerException;
import com.example.scaler.models.BatchLearner;
import com.example.scaler.models.Learner;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LearnerServiceImpl implements LearnerService {
    BatchRepository batchRepository;
    BatchLearnerRepository batchLearnerRepository;
    LearnerRepository learnerRepository;
    LectureRepository lectureRepository;
    ScheduledLectureRepository scheduledLectureRepository;

    public LearnerServiceImpl(BatchRepository batchRepository,
                              BatchLearnerRepository batchLearnerRepository,
                              LearnerRepository learnerRepository,
                              LectureRepository lectureRepository,
                              ScheduledLectureRepository scheduledLectureRepository) {
        this.batchLearnerRepository = batchLearnerRepository;
        this.batchRepository = batchRepository;
        this.learnerRepository = learnerRepository;
        this.lectureRepository = lectureRepository;
        this.scheduledLectureRepository = scheduledLectureRepository;
    }

    @Override
    public List<ScheduledLecture> fetchTimeline(long learnerId) throws InvalidLearnerException {
        Optional<Learner> learnerOptional = learnerRepository.findById(learnerId);
        if (learnerOptional.isEmpty()) {
            throw new InvalidLearnerException("Invalid learner with id: " + learnerId);
        }

        List<BatchLearner> batchLearners = batchLearnerRepository.findAllByLearnerId(learnerOptional.get().getId());
        List<ScheduledLecture> scheduledLectureList = scheduledLectureRepository.findAll();

        List<ScheduledLecture> allowedLectures = new ArrayList<>();
        for (BatchLearner bl : batchLearners) {
            for (ScheduledLecture sl : scheduledLectureList) {
                Date start = sl.getLectureStartTime();
                Date end = sl.getLectureEndTime();
                Date entry = bl.getEntryDate();
                Date exit = bl.getExitDate();

                if (bl.getBatch().getId() == sl.getBatch().getId()) {
                    if (bl.getEntryDate() != null && bl.getExitDate() != null) {
                        if ((end.equals(entry) || end.after(entry)) &&
                                (end.equals(exit) || end.before(exit))
                        ) {
                            allowedLectures.add(sl);
                        }
                    }

                    if (bl.getEntryDate() != null && bl.getExitDate() == null) {
                        if ((end.equals(entry) || end.after(entry))
                        ) {
                            allowedLectures.add(sl);
                        }
                    }

                }
            }
        }


        return allowedLectures;


    }
}
