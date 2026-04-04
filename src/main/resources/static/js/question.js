const timerPage=document.getElementById('timerPage');
const questionPage=document.getElementById('questionPage');
const startTimerNumber=document.getElementById('startTimerNumber');
const questionTimerEl=document.getElementById('questionTimer');
const continueBtn=document.getElementById('continueBtn');
const pauseBtn=document.getElementById('pauseBtn');
const endBtn=document.getElementById('endBtn');
const questionNumber=document.getElementById('questionNumber');
const questionText=document.getElementById('questionText');
const optionsList=document.getElementById('optionsList');

const questions=[
  {text:"Кто был первым римским императором?", answer:"Октавиан Август"},
  {text:"Кто основал Киев?", answer:"Кий"},
  {text:"В каком году произошло крещение Руси?", answer:"988"}
];

let currentQuestion=0;
let paused=false;
let questionTimeLeft=15;

// Скрываем блок вопроса до конца стартового таймера
questionPage.style.display='none';
timerPage.style.display='block';

// START TIMER (3s)
let startTime=3;
startTimerNumber.textContent=startTime;
const startInterval=setInterval(()=>{
  startTime--;
  startTimerNumber.textContent=startTime;
  if(startTime<=0){
    clearInterval(startInterval);
    timerPage.style.display='none';
    questionPage.style.display='block';
    showQuestion(currentQuestion);
  }
},1000);

// QUESTION TIMER
let questionTimerInterval;
function startQuestionTimer(){
  questionTimeLeft=15;
  questionTimerEl.textContent=questionTimeLeft+'с';
  clearInterval(questionTimerInterval);
  questionTimerInterval=setInterval(()=>{
    if(!paused){
      questionTimeLeft--;
      questionTimerEl.textContent=questionTimeLeft+'с';
      if(questionTimeLeft<=0){
        clearInterval(questionTimerInterval);
      }
    }
  },1000);
}

function showQuestion(index){
  const q=questions[index];
  questionNumber.textContent=`Вопрос ${index+1}`;
  questionText.textContent=q.text;
  optionsList.innerHTML=`<div class="option correct"><span class="option-text">${q.answer}</span><span class="check-mark">✓</span></div>`;
  startQuestionTimer();
}

// BUTTONS
continueBtn.addEventListener('click',()=>{
  if(paused) return;
  currentQuestion++;
  if(currentQuestion>=questions.length){
    alert("Квиз завершён!");
  } else {
    showQuestion(currentQuestion);
  }
});

pauseBtn.addEventListener('click',()=>{
  paused=!paused;
  pauseBtn.textContent=paused ? "Возобновить" : "Пауза";
});

endBtn.addEventListener('click',()=>{
  if(confirm("Вы уверены, что хотите завершить квиз?")) alert("Квиз завершён!");
});

// PROFILE CLICK
document.querySelector('.profile').addEventListener('click',()=>{window.location.href='profile.html';});