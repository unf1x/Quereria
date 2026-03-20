// Получаем квизы из localStorage
function getQuizzes() {
  return JSON.parse(localStorage.getItem("quizzes")) || [];
}

// Рендер карточек
function renderQuizzes() {
  const container = document.getElementById("quiz-container");
  container.innerHTML = "";

  const quizzes = getQuizzes();

  if (quizzes.length === 0) {
    container.innerHTML = "<p>Пока нет квизов</p>";
    return;
  }

  quizzes.forEach(quiz => {
    const card = document.createElement("div");
    card.classList.add("quiz-card");

    card.innerHTML = `
      <h3>${quiz.title}</h3>
      <p>Количество вопросов: ${quiz.questions.length}</p>
      <button data-id="${quiz.id}">Открыть</button>
    `;

    container.appendChild(card);
  });
}

// Кнопка "Создать квиз"
document.getElementById("create-btn")
  .addEventListener("click", () => {
    window.location.href = "create.html";
  });

// Запуск
renderQuizzes();