async function loadQuizzes() {
  const container = document.getElementById("quiz-container");
  container.innerHTML = "<p>Загрузка...</p>";

  try {
    const response = await fetch("http://localhost:8080/api/quizzes");

    if (!response.ok) {
      throw new Error("Ошибка загрузки квизов");
    }

    const quizzes = await response.json();
    container.innerHTML = "";

    if (!quizzes.length) {
      container.innerHTML = "<p>Пока нет квизов</p>";
      return;
    }

    quizzes.forEach(quiz => {
      const card = document.createElement("div");
      card.classList.add("quiz-card");

      card.innerHTML = `
        <h3>${quiz.title}</h3>
        <p>${quiz.description || "Без описания"}</p>
        <p>Количество вопросов: ${quiz.questionCount}</p>
        <button data-id="${quiz.id}">Открыть</button>
      `;

      container.appendChild(card);
    });
  } catch (error) {
    console.error(error);
    container.innerHTML = "<p>Не удалось загрузить квизы</p>";
  }
}

document.getElementById("create-btn").addEventListener("click", () => {
  window.location.href = "create.html";
});

loadQuizzes();