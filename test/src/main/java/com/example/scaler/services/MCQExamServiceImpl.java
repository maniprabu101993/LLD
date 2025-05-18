package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidExamException;
import com.example.scaler.exceptions.InvalidLearnerException;
import com.example.scaler.models.*;
import com.example.scaler.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MCQExamServiceImpl implements MCQExamService {
    @Autowired
    private LearnerRepository learnerRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private LearnerExamRepository learnerExamRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private LearnerQuestionResponseRepository learnerQuestionResponseRepository;

    @Override
    public LearnerExam startExam(Long learnerId, Long examId) throws InvalidLearnerException, InvalidExamException {
        Optional<Learner> learnerOptional = learnerRepository.findById(learnerId);
        if (learnerOptional.isEmpty()) {
            throw new InvalidLearnerException("Invalid learner with id: " + learnerId);
        }

        Optional<Exam> examOptional = examRepository.findById(examId);
        if (examOptional.isEmpty()) {
            throw new InvalidExamException("Invalid exam with id: " + examId);
        }

        Optional<LearnerExam> learnerExamOptional = learnerExamRepository.findByExam_Id(examOptional.get().getId());

        LearnerExam startedLearnerExam;
        if (learnerExamOptional.isEmpty()) {
            startedLearnerExam = new LearnerExam();
            startedLearnerExam.setExam(examOptional.get());
            startedLearnerExam.setLearner(learnerOptional.get());
            startedLearnerExam.setStartedAt(new Date());
            startedLearnerExam.setStatus(ExamStatus.STARTED);
            learnerExamRepository.save(startedLearnerExam);
        } else {
            throw new InvalidExamException("Can not start exam again !");
        }

        return startedLearnerExam;
    }

    @Override
    public LearnerExam submitExam(Long learnerId, Long examId) throws InvalidLearnerException, InvalidExamException {
        Optional<Learner> learnerOptional = learnerRepository.findById(learnerId);
        if (learnerOptional.isEmpty()) {
            throw new InvalidLearnerException("Invalid learner with id: " + learnerId);
        }

        Optional<Exam> examOptional = examRepository.findById(examId);
        if (examOptional.isEmpty()) {
            throw new InvalidExamException("Invalid exam with id: " + examId);
        }

        List<Question> questions = questionRepository.findAll();

        List<Question> examQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q.getExam().getId() == examOptional.get().getId()) {
                examQuestions.add(q);
            }
        }


        int totalScore = 0;
        for (Question q : examQuestions) {
            int questionScore = 0;
            Optional<LearnerQuestionResponse> learnerQuestionResponseOptional = learnerQuestionResponseRepository.findByQuestion_Id(q.getId());
            if (learnerQuestionResponseOptional.isEmpty()) {
                questionScore = 0;
            } else {
                if (q.getCorrectOption().getId() == learnerQuestionResponseOptional.get().getOption().getId()) {
                    questionScore = q.getScore();
                } else {
                    questionScore = 0;
                }
            }

            totalScore += questionScore;

            Exam exam = examOptional.get();
            exam.setTotalScore(totalScore);
            examRepository.save(exam);

        }

        Optional<LearnerExam> learnerExamOptional = learnerExamRepository.findByExam_Id(examOptional.get().getId());
        if (learnerExamOptional.isEmpty()) {
            throw new InvalidExamException("Can not submit the exam which has not started yet!");
        }

        LearnerExam learnerExam = learnerExamOptional.get();
        if (!learnerExam.getStatus().equals(ExamStatus.ENDED)) {
            learnerExam.setExam(examOptional.get());
            learnerExam.setStatus(ExamStatus.ENDED);
            learnerExam.setScoreObtained(totalScore);
            learnerExam.setEndedAt(new Date());
            LearnerExam submitLearnerExam = learnerExamRepository.save(learnerExam);
            return submitLearnerExam;
        } else {
            throw new InvalidExamException("Exam already submitted !");
        }

    }

    @Override
    public LearnerQuestionResponse answerQuestion(Long learnerId, Long questionId, Long optionId) throws InvalidLearnerException, InvalidExamException {
        Optional<Learner> learnerOptional = learnerRepository.findById(learnerId);
        if (learnerOptional.isEmpty()) {
            throw new InvalidLearnerException("Invalid learner with id: " + learnerId);
        }

        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isEmpty()) {
            throw new InvalidExamException("Invalid exam with question id: " + questionId);
        }

        Optional<Option> optionOptional = optionRepository.findById(optionId);
        if (optionOptional.isEmpty()) {
            throw new InvalidExamException("Invalid option with option id: " + optionId);
        }



        Option myOption = new Option();
        boolean flag = false;
        List<Option> questionOptions = questionOptional.get().getOptions();
        for(Option opt: questionOptions){
            if(opt.getId() == optionOptional.get().getId()){
                myOption = opt;
                flag = true;
                break;
            }
        }
        if(flag == false){
            throw new InvalidExamException("Option does not belong to question!");
        }

        LearnerQuestionResponse learnerQuestionResponse = null;
        List<LearnerExam> learnerExamList = learnerExamRepository.findAll();
        for (LearnerExam le : learnerExamList) {
            if (le.getLearner().getId() == learnerId) {
                if (le.getExam().getId() == questionOptional.get().getExam().getId()) {
                    if (le.getStatus() == ExamStatus.STARTED) {
                        learnerQuestionResponse = new LearnerQuestionResponse();
                        learnerQuestionResponse.setQuestion(questionOptional.get());
                        learnerQuestionResponse.setLearner(learnerOptional.get());
                        learnerQuestionResponse.setOption(myOption);
                        learnerQuestionResponseRepository.save(learnerQuestionResponse);

                    }
                }
            }

        }

        return learnerQuestionResponse;
    }
}
