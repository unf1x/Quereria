document.addEventListener('DOMContentLoaded', async () => {
    const burger = document.getElementById('burger');
    const menu = document.getElementById('menu');
    const profileBtn = document.getElementById('profileBtn');

    if (burger && menu) {
        burger.addEventListener('click', () => {
            menu.classList.toggle('active');
        });
    }

    const currentPage = window.location.pathname.split("/").pop();

    document.querySelectorAll('.menu-item').forEach(item => {
        const href = item.getAttribute('href');
        if (href === currentPage) {
            item.classList.add('active');
        }
    });

    if (profileBtn) {
        try {
            const response = await fetch('/api/auth/me', {
                method: 'GET',
                credentials: 'include'
            });

            if (response.ok) {
                const user = await response.json();
                profileBtn.textContent = user.name || 'Профиль';
                profileBtn.addEventListener('click', () => {
                    window.location.href = 'profile.html';
                });
            } else {
                profileBtn.textContent = 'Регистрация/вход';
                profileBtn.addEventListener('click', () => {
                    window.location.href = 'login.html';
                });
            }
        } catch (e) {
            profileBtn.textContent = 'Регистрация/вход';
            profileBtn.addEventListener('click', () => {
                window.location.href = 'login.html';
            });
        }
    }
});