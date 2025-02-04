const API_URL = window.location.origin + '/api';

async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem('token', data.token);
            localStorage.setItem('username', data.username);
            console.log('Login successful, redirecting...');
            window.location.href = '/index.html';
        } else {
            console.error('Login failed:', data);
            showError(data.error || 'Giriş başarısız. Lütfen bilgilerinizi kontrol edin.');
        }
    } catch (error) {
        console.error('Login error:', error);
        showError('Bir hata oluştu. Lütfen daha sonra tekrar deneyin.');
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        showError('Şifreler eşleşmiyor.');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        const data = await response.json();

        if (response.ok) {
            console.log('Registration successful, redirecting to login...');
            window.location.href = '/login.html';
        } else {
            console.error('Registration failed:', data);
            showError(data.error || 'Kayıt başarısız. Lütfen tekrar deneyin.');
        }
    } catch (error) {
        console.error('Registration error:', error);
        showError('Bir hata oluştu. Lütfen daha sonra tekrar deneyin.');
    }
}

function showError(message) {
    const errorElement = document.querySelector('.error-message');
    if (errorElement) {
        errorElement.textContent = message;
        errorElement.style.display = 'block';
    } else {
        alert(message);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }

    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', handleRegister);
    }
});

