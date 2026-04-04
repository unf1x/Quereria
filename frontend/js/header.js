// БУРГЕР-МЕНЮ
document.addEventListener('DOMContentLoaded', () => {
    const burger = document.getElementById('burger');
    const menu = document.getElementById('menu');

    if (burger && menu) {
        burger.addEventListener('click', () => {
            menu.classList.toggle('active');
        });
    }
});

// Актуальная активная страница
const currentPage = window.location.pathname.split("/").pop();

document.querySelectorAll('.menu-item').forEach(item => {
    if (item.dataset.page === currentPage) {
        item.classList.add('active');
    }
});

// Переход по профилю
const profileBtn = document.getElementById('profileBtn');
if (profileBtn) {
    profileBtn.addEventListener('click', () => {
        window.location.href = 'profile.html';
    });
}