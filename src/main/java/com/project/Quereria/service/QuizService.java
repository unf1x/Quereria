package com.project.Quereria.service;

import com.project.Quereria.dto.QuizSummaryResponse;
import com.project.Quereria.entity.*;
import com.project.Quereria.entity.enums.QuestionType;
import com.project.Quereria.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuizQuestionLinkRepository quizQuestionLinkRepository;
    private final QuestionAnswerBondRepository questionAnswerBondRepository;

    @Transactional
    public Long createQuiz(QuizRequest request) {
        validateQuizRequest(request);

        Quiz quiz = Quiz.builder()
                .name(request.getTitle().trim())
                .description(request.getDescription())
                .dateOfCreation(LocalDateTime.now())
                .build();

        quizRepository.save(quiz);

        int number = 1;

        for (QuestionRequest q : request.getQuestions()) {
            Question question = new Question();
            question.setNumber(number++);
            question.setText(q.getText() == null ? null : q.getText().trim());
            question.setType(resolveType(q));
            question.setTimerSeconds(convertToSeconds(q.getTime()));

            questionRepository.save(question);

            QuizQuestionLink link = QuizQuestionLink.builder()
                    .quiz(quiz)
                    .question(question)
                    .build();

            quizQuestionLinkRepository.save(link);

            if (Boolean.TRUE.equals(q.getHasAnswers()) && q.getAnswers() != null) {
                for (int i = 0; i < q.getAnswers().size(); i++) {
                    String answerText = q.getAnswers().get(i);

                    if (answerText == null || answerText.trim().isEmpty()) {
                        continue;
                    }

                    Answer answer = Answer.builder()
                            .text(answerText.trim())
                            .build();

                    answerRepository.save(answer);

                    QuestionAnswerBond bond = QuestionAnswerBond.builder()
                            .question(question)
                            .answer(answer)
                            .isCorrected(q.getCorrectIndex() != null && q.getCorrectIndex() == i)
                            .build();

                    questionAnswerBondRepository.save(bond);
                }
            }

            if (Boolean.TRUE.equals(q.getOpen())
                    && q.getOpenAnswer() != null
                    && !q.getOpenAnswer().trim().isEmpty()) {

                Answer openAnswer = Answer.builder()
                        .text(q.getOpenAnswer().trim())
                        .build();

                answerRepository.save(openAnswer);

                QuestionAnswerBond bond = QuestionAnswerBond.builder()
                        .question(question)
                        .answer(openAnswer)
                        .isCorrected(true)
                        .build();

                questionAnswerBondRepository.save(bond);
            }
        }

        return quiz.getId();
    }

    public List<QuizSummaryResponse> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizSummaryResponse> result = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            int count = quizQuestionLinkRepository.findByQuizId(quiz.getId()).size();

            result.add(new QuizSummaryResponse(
                    quiz.getId(),
                    quiz.getName(),
                    quiz.getDescription(),
                    count
            ));
        }

        return result;
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Квиз не найден: " + id));
    }

    private void validateQuizRequest(QuizRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Пустой запрос");
        }

        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Название квиза обязательно");
        }

        if (request.getQuestions() == null || request.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("Нужен хотя бы один вопрос");
        }

        for (QuestionRequest q : request.getQuestions()) {
            if (!Boolean.TRUE.equals(q.getOpen()) && !Boolean.TRUE.equals(q.getHasAnswers())) {
                throw new IllegalArgumentException("У вопроса должен быть либо открытый ответ, либо варианты ответа");
            }

            if (Boolean.TRUE.equals(q.getHasAnswers())) {
                if (q.getAnswers() == null || q.getAnswers().isEmpty()) {
                    throw new IllegalArgumentException("Для вопроса с вариантами нужны ответы");
                }
            }
        }
    }

    private QuestionType resolveType(QuestionRequest q) {
        if (Boolean.TRUE.equals(q.getOpen())) {
            return QuestionType.OPEN;
        }
        if (Boolean.TRUE.equals(q.getHasAnswers())) {
            return QuestionType.CHOICE;
        }
        throw new IllegalArgumentException("Тип вопроса не определён");
    }

    private Integer convertToSeconds(TimeRequest time) {
        if (time == null) {
            return null;
        }

        return time.getHours() * 3600
                + time.getMinutes() * 60
                + time.getSeconds();
    }
}