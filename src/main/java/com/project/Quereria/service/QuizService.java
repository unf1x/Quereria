package com.project.Quereria.service;

import com.project.Quereria.dto.response.AnswerResponse;
import com.project.Quereria.dto.response.QuestionResponse;
import com.project.Quereria.dto.response.QuizFullResponse;
import com.project.Quereria.dto.response.QuizSummaryResponse;
import com.project.Quereria.dto.request.QuestionRequest;
import com.project.Quereria.dto.request.QuizRequest;
import com.project.Quereria.dto.request.TimeRequest;
import com.project.Quereria.entity.*;
import com.project.Quereria.entity.enums.QuestionType;
import com.project.Quereria.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public QuizFullResponse getQuizFullById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Квиз не найден: " + quizId));

        List<QuizQuestionLink> links = quizQuestionLinkRepository.findByQuizId(quizId);

        List<Long> questionIds = links.stream()
                .map(link -> link.getQuestion().getId())
                .toList();

        List<Question> questions = questionIds.isEmpty()
                ? Collections.emptyList()
                : questionRepository.findByIdIn(questionIds);

        Map<Long, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        List<QuestionAnswerBond> allBonds = questionIds.isEmpty()
                ? Collections.emptyList()
                : questionAnswerBondRepository.findByQuestionIdIn(questionIds);

        Map<Long, List<QuestionAnswerBond>> bondsByQuestionId = allBonds.stream()
                .collect(Collectors.groupingBy(bond -> bond.getQuestion().getId()));

        List<QuestionResponse> questionResponses = links.stream()
                .map(link -> {
                    Long questionId = link.getQuestion().getId();
                    Question question = questionMap.get(questionId);

                    List<AnswerResponse> answers = bondsByQuestionId
                            .getOrDefault(questionId, Collections.emptyList())
                            .stream()
                            .map(bond -> new AnswerResponse(
                                    bond.getAnswer().getId(),
                                    bond.getAnswer().getText(),
                                    bond.getIsCorrected()
                            ))
                            .toList();

                    return new QuestionResponse(
                            question.getId(),
                            question.getNumber(),
                            question.getText(),
                            question.getType().name(),
                            question.getTimerSeconds(),
                            answers
                    );
                })
                .sorted(Comparator.comparing(QuestionResponse::getNumber))
                .toList();

        return new QuizFullResponse(
                quiz.getId(),
                quiz.getName(),
                quiz.getDescription(),
                quiz.getDateOfCreation(),
                questionResponses
        );
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
            if (q.getText() == null || q.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Текст вопроса обязателен");
            }

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

        int hours = time.getHours();
        int minutes = time.getMinutes();
        int seconds = time.getSeconds();

        return hours * 3600 + minutes * 60 + seconds;
    }
}