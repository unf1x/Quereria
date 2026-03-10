CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    gender VARCHAR(50),
    birthday DATE
);

CREATE TABLE quizzes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(50),
    cover VARCHAR(500),
    date_of_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY,
    number INTEGER NOT NULL,
    type VARCHAR(50),
    timer_seconds INTEGER
);

CREATE TABLE answers (
    id BIGSERIAL PRIMARY KEY,
    text TEXT NOT NULL
);

CREATE TABLE results (
    id BIGSERIAL PRIMARY KEY,
    score INTEGER NOT NULL,
    user_id BIGINT NOT NULL,
    quiz_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_result_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_result_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

CREATE TABLE quiz_access (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    quiz_id BIGINT NOT NULL,
    rights VARCHAR(50) NOT NULL,
    CONSTRAINT fk_quiz_access_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_quiz_access_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    CONSTRAINT uq_quiz_access UNIQUE (user_id, quiz_id)
);

CREATE TABLE quiz_question_link (
    id BIGSERIAL PRIMARY KEY,
    quiz_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    CONSTRAINT fk_quiz_question_link_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    CONSTRAINT fk_quiz_question_link_question FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    CONSTRAINT uq_quiz_question UNIQUE (quiz_id, question_id)
);

CREATE TABLE question_answer_bond (
    id BIGSERIAL PRIMARY KEY,
    answer_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    is_corrected BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_question_answer_bond_answer FOREIGN KEY (answer_id) REFERENCES answers(id) ON DELETE CASCADE,
    CONSTRAINT fk_question_answer_bond_question FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    CONSTRAINT uq_question_answer UNIQUE (answer_id, question_id)
);