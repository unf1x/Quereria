UPDATE quizzes
SET type = (
    SELECT q.type
    FROM quizzes q
    WHERE q.type <> 'POPULAR'
    LIMIT 1
)
WHERE type = 'POPULAR';