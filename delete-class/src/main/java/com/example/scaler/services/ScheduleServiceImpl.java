package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidScheduledLectureException;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.repositories.BatchRepository;
import com.example.scaler.repositories.InstructorRepository;
import com.example.scaler.repositories.LectureRepository;
import com.example.scaler.repositories.ScheduledLectureRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduledLectureService {
    BatchRepository batchRepository;
    InstructorRepository instructorRepository;
    LectureRepository lectureRepository;
    ScheduledLectureRepository scheduledLectureRepository;

    public ScheduleServiceImpl(BatchRepository batchRepository, InstructorRepository instructorRepository, LectureRepository lectureRepository,
                               ScheduledLectureRepository scheduledLectureRepository) {
        this.batchRepository = batchRepository;
        this.instructorRepository = instructorRepository;
        this.lectureRepository = lectureRepository;
        this.scheduledLectureRepository = scheduledLectureRepository;
    }

    @Override
    public List<ScheduledLecture> rescheduleScheduledLecture(long scheduledLectureId) throws InvalidScheduledLectureException {
        ScheduledLecture temp= scheduledLectureRepository.findById(scheduledLectureId).orElseThrow(() -> new InvalidScheduledLectureException("Scheduled Lecture not found"));
        List<ScheduledLecture> list= scheduledLectureRepository.findScheduledLectureByBatchId(temp.getBatch().getId());
        List<ScheduledLecture> resedList= new ArrayList<>();
        for(ScheduledLecture sclecture: list){
            if(sclecture.getLectureStartTime().compareTo(temp.getLectureStartTime())>=0){
                Calendar startDate = Calendar.getInstance();
                startDate.setTime(sclecture.getLectureStartTime());
                if(startDate.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY || startDate.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                    startDate.add(Calendar.DATE, 3);
                }else {
                    startDate.add(Calendar.DATE, 2);
                }
                sclecture.setLectureStartTime(startDate.getTime());
                sclecture.setLectureEndTime(get2HalfHour(sclecture.getLectureStartTime()));
                resedList.add(sclecture);
            }
        }
        return scheduledLectureRepository.saveAll(resedList);
    }

    private Date get2HalfHour(Date startDate){
        Calendar cal=Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.HOUR, 2);
        cal.add(Calendar.MINUTE, 30);
        return cal.getTime();
    }
}
