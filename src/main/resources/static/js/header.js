document.addEventListener('DOMContentLoaded', async () => {
    const burger = document.getElementById('burger');
    const menu = document.getElementById('menu');
    const profileBtn = document.getElementById('profileBtn');

    if (burger && menu) {
        burger.addEventListener('click', (e) => {
            e.stopPropagation();
            menu.classList.toggle('active');
        });

        document.addEventListener('click', (event) => {
            if (!menu.contains(event.target) && !burger.contains(event.target)) {
                menu.classList.remove('active');
            }
        });
    }

    const currentPage = window.location.pathname.split("/").pop();

    document.querySelectorAll('.menu-item').forEach(item => {
        const href = item.getAttribute('href');
        const dataPage = item.getAttribute('data-page');
        const targetPage = href || dataPage;

        if (targetPage === currentPage) {
            item.classList.add('active');
        }

        item.addEventListener('click', () => {
            if (menu) {
                menu.classList.remove('active');
            }
        });
    });

    document.querySelectorAll('.logo, #logoBtn').forEach(logo => {
        logo.style.cursor = 'pointer';
        logo.addEventListener('click', () => {
            window.location.href = 'mainpage.html';
        });
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