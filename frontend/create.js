var addBtn = document.getElementById("add-question-btn");
var container = document.getElementById("questions-container");

var questionCount = 0;

addBtn.addEventListener("click", addQuestion);

function addQuestion() {

  questionCount++;

  var card = document.createElement("div");
  card.className = "question-card question-new";

  card.innerHTML = `
    <div class="question-header">Вопрос ${questionCount}</div>

    <textarea class="question-text" placeholder="Введите вопрос..."></textarea>

    <div class="question-options">

      <label>
        <input type="checkbox" class="open-check">
        Открытый вопрос
      </label>

      <label>
        <input type="checkbox" class="time-check">
        Ограничение по времени
      </label>

      <label>
        <input type="checkbox" class="answers-check">
        Варианты ответа
      </label>

    </div>

    <div class="question-actions">
      <button class="save-btn">Добавить</button>
      <button class="delete-btn">🗑</button>
    </div>
  `;

  container.appendChild(card);

  var saveBtn = card.querySelector(".save-btn");
  var deleteBtn = card.querySelector(".delete-btn");

  // ===== ВАРИАНТЫ ОТВЕТОВ =====
  var answersCheck = card.querySelector(".answers-check");

  var answersContainer = document.createElement("div");
  answersContainer.className = "answers-container";
  answersContainer.style.display = "none";

  var addAnswerBtn = document.createElement("button");
  addAnswerBtn.textContent = "+ Добавить вариант";
  addAnswerBtn.className = "add-answer-btn";

  answersContainer.appendChild(addAnswerBtn);
  card.insertBefore(answersContainer, card.querySelector(".question-actions"));

  answersCheck.addEventListener("change", function () {
    answersContainer.style.display = this.checked ? "block" : "none";

    // ❗ отключаем открытый вопрос
    if (this.checked) {
      openCheck.checked = false;
      openContainer.style.display = "none";
    }
  });

  addAnswerBtn.addEventListener("click", function () {

    var answer = document.createElement("div");
    answer.className = "answer-item";

    answer.innerHTML = `
      <input type="radio" name="correct-${questionCount}">
      <input type="text" placeholder="Введите вариант ответа">
      <button class="delete-answer">✖</button>
    `;

    answersContainer.insertBefore(answer, addAnswerBtn);

    answer.querySelector(".delete-answer").addEventListener("click", function () {
      answer.remove();
    });

  });

  // ===== ОТКРЫТЫЙ ВОПРОС =====
  var openCheck = card.querySelector(".open-check");

  var openContainer = document.createElement("div");
  openContainer.className = "open-answer-container";
  openContainer.style.display = "none";

  openContainer.innerHTML = `
    <input type="text" class="open-answer-input" placeholder="Введите правильный ответ">
  `;

  card.insertBefore(openContainer, card.querySelector(".question-actions"));

  openCheck.addEventListener("change", function () {
    openContainer.style.display = this.checked ? "block" : "none";

    // ❗ отключаем варианты ответа
    if (this.checked) {
      answersCheck.checked = false;
      answersContainer.style.display = "none";
    }
  });

  // ===== ТАЙМЕР =====
  var timeCheck = card.querySelector(".time-check");

  var timeContainer = document.createElement("div");
  timeContainer.className = "time-container";
  timeContainer.style.display = "none";

  timeContainer.innerHTML = `
    <div class="time-inputs">
      <input type="number" class="time-hours" min="0" placeholder="0"> ч
      <input type="number" class="time-minutes" min="0" max="59" placeholder="0"> мин
      <input type="number" class="time-seconds" min="0" max="59" placeholder="0"> сек
    </div>
  `;

  card.insertBefore(timeContainer, card.querySelector(".question-actions"));

  timeCheck.addEventListener("change", function () {
    timeContainer.style.display = this.checked ? "block" : "none";
  });

  // ===== СОХРАНЕНИЕ / РЕДАКТИРОВАНИЕ =====
  saveBtn.addEventListener("click", function () {

    var textarea = card.querySelector(".question-text");
    var checkboxes = card.querySelectorAll("input[type='checkbox']");
    var answerInputs = card.querySelectorAll(".answer-item input");
    var timeInputs = card.querySelectorAll(".time-container input");
    var openInput = card.querySelector(".open-answer-input");

    if (card.classList.contains("question-new")) {

      if (textarea.value.trim() === "") {
        alert("Введите вопрос!");
        return;
      }

      textarea.disabled = true;
      checkboxes.forEach(cb => cb.disabled = true);
      answerInputs.forEach(inp => inp.disabled = true);
      timeInputs.forEach(inp => inp.disabled = true);
      if (openInput) openInput.disabled = true;

      card.classList.remove("question-new");
      card.classList.add("question-saved");

      saveBtn.textContent = "Редактировать";

    } else {

      textarea.disabled = false;
      checkboxes.forEach(cb => cb.disabled = false);
      answerInputs.forEach(inp => inp.disabled = false);
      timeInputs.forEach(inp => inp.disabled = false);
      if (openInput) openInput.disabled = false;

      card.classList.remove("question-saved");
      card.classList.add("question-new");

      saveBtn.textContent = "Сохранить";
    }

  });

  deleteBtn.addEventListener("click", function () {
    card.remove();
  });

  window.scrollTo({
    top: document.body.scrollHeight,
    behavior: "smooth"
  });

}

// ===== СОХРАНЕНИЕ КВИЗА =====
var finishBtn = document.getElementById("finish-btn");

finishBtn.addEventListener("click", function () {

  var title = document.getElementById("quiz-title").value;
  var description = document.getElementById("quiz-description").value;

  var questions = [];

  var cards = document.querySelectorAll(".question-card");

  cards.forEach(function (card) {

    var text = card.querySelector(".question-text").value;

    var open = card.querySelector(".open-check")?.checked;
    var openAnswer = card.querySelector(".open-answer-input")?.value || "";

    var hasAnswers = card.querySelector(".answers-check")?.checked;
    var hasTimer = card.querySelector(".time-check")?.checked;

    var answers = [];
    var correctIndex = null;

    if (hasAnswers) {
      var answerItems = card.querySelectorAll(".answer-item");

      answerItems.forEach(function (item, index) {

        var input = item.querySelector("input[type='text']");
        var radio = item.querySelector("input[type='radio']");

        answers.push(input.value);

        if (radio.checked) {
          correctIndex = index;
        }

      });
    }

    var hours = card.querySelector(".time-hours")?.value || 0;
    var minutes = card.querySelector(".time-minutes")?.value || 0;
    var seconds = card.querySelector(".time-seconds")?.value || 0;

    questions.push({
      text,
      open,
      openAnswer,
      hasAnswers,
      answers,
      correctIndex,
      hasTimer,
      time: {
        hours: Number(hours),
        minutes: Number(minutes),
        seconds: Number(seconds)
      }
    });

  });

  var quiz = {
    id: Date.now(),
    title,
    description,
    questions
  };

  var quizzes = JSON.parse(localStorage.getItem("quizzes")) || [];

  quizzes.push(quiz);

fetch("http://localhost:8080/api/quizzes", {
  method: "POST",
  headers: {"Content-Type": "application/json"},
  body: JSON.stringify(quiz)
})
.then(response => {
  if (!response.ok) throw new Error("Ошибка при сохранении");
  return response.json();
})
.then(data => {
  alert("Квиз сохранён!");         // <-- сюда
  window.location.href = "index.html"; // <-- сюда
})
.catch(err => {
  console.error(err);
  alert("Ошибка при сохранении квиза");
});

});