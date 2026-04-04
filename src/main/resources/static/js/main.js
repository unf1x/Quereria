async function loadQuizzes() {
  const container = document.getElementById("quiz-container");
  container.innerHTML = "<p>Загрузка...</p>";

  try {
    const response = await fetch("/api/quizzes");

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

    const openBtn = card.querySelector("button");
    openBtn.addEventListener("click", () => {
      window.location.href = `viewquiz.html?id=${quiz.id}`;
    });
    });
  } catch (error) {
    console.error(error);
    container.innerHTML = "<p>Не удалось загрузить квизы</p>";
  }
}

const createBtn = document.getElementById("create-btn");
if (createBtn) {
  createBtn.addEventListener("click", () => {
    window.location.href = "create.html";
  });
}

const quizContainer = document.getElementById("quiz-container");
if (quizContainer) {
  loadQuizzes();
}