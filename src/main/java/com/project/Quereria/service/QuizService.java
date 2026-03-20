package com.project.Quereria.service;

import com.project.Quereria.entity.*;
import com.project.Quereria.entity.enums.QuestionType;
import com.project.Quereria.repository.AnswerRepository;
import com.project.Quereria.repository.QuestionRepository;
import com.project.Quereria.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public Long createQuiz(QuizRequest request) {

        Quiz quiz = Quiz.builder()
                .name(request.getTitle())
                .description(request.getDescription())
                .dateOfCreation(LocalDateTime.now())
                .build();

        quizRepository.save(quiz);

        int number = 1;

        for (QuestionRequest q : request.getQuestions()) {

            Question question = new Question();
            question.setNumber(number++);
            question.setType(resolveType(q));
            question.setTimerSeconds(convertToSeconds(q.getTime()));

            questionRepository.save(question);

            // связь с квизом (через таблицу link)
            // 👉 лучше сделать через @ManyToMany или вручную

            if (Boolean.TRUE.equals(q.getHasAnswers())) {
                for (int i = 0; i < q.getAnswers().size(); i++) {

                    Answer answer = new Answer();
                    answer.setText(q.getAnswers().get(i));

                    answerRepository.save(answer);

                    // тут надо сохранить связь question_answer_bond
                    // + отметить правильный ответ
                }
            }

            if (Boolean.TRUE.equals(q.getOpen())) {
                // сохранить openAnswer как отдельный Answer
            }
        }

        return quiz.getId();
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
        if (time == null) return null;
        return time.getHours() * 3600 +
                time.getMinutes() * 60 +
                time.getSeconds();
    }
}