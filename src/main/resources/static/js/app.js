/*// Optional: Simple form validation or alert messages

window.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.querySelector("form[action*='/users/login']");
    const registerForm = document.querySelector("form[action*='/users/register']");

    // Basic login validation
    if (loginForm) {
        loginForm.addEventListener("submit", (e) => {
            const username = loginForm.username.value.trim();
            const password = loginForm.password.value.trim();
            if (!username || !password) {
                e.preventDefault();
                alert("Please enter both username and password");
            }
        });
    }

    // Basic register validation
    if (registerForm) {
        registerForm.addEventListener("submit", (e) => {
            const username = registerForm.querySelector("[name='userName']").value.trim();
            const password = registerForm.querySelector("[name='password']").value.trim();
            if (!username || !password) {
                e.preventDefault();
                alert("Please enter username and password for registration");
            }
        });
    }
});*/
// ================= USER REGISTRATION =================
function register() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.getElementById("role").value;

    if (!username || !password) {
        alert("Please enter username and password for registration");
        return;
    }

    fetch("/users/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userName: username, password, role })
    })
    .then(res => {
        if (!res.ok) throw new Error("Registration failed");
        return res.json();
    })
    .then(res => alert("Registered successfully: " + res.username))
    .catch(err => {
        console.error(err);
        alert("Registration failed");
    });
}

// ================= USER LOGIN =================
function login() {
    const username = document.getElementById("loginUsername").value.trim();
    const password = document.getElementById("loginPassword").value.trim();

    if (!username || !password) {
        alert("Please enter username and password");
        return;
    }

    fetch(`/users/login?username=${username}&password=${password}`, { method: "POST" })
    .then(res => {
        if (!res.ok) throw new Error("Login failed");
        return res.json();
    })
    .then(res => {
        // Save user info in localStorage
        localStorage.setItem("userId", res.id);
        localStorage.setItem("username", res.username);

        // Redirect to admission page
        window.location.href = "/admission.html";
    })
    .catch(err => {
        console.error(err);
        alert("Invalid username or password");
    });
}

// ================= INITIAL PAGE LOAD =================
window.onload = () => {
    const userId = localStorage.getItem("userId");
    const username = localStorage.getItem("username");

    if (userId && username) {
        // Already logged in → redirect to admissions
        window.location.href = "/admission.html";
    } else {
        // Show login/register page
        const loginCard = document.getElementById("loginFormCard");
        const registerCard = document.getElementById("registerFormCard");
        if (loginCard) loginCard.style.display = "block";
        if (registerCard) registerCard.style.display = "block";
    }
};
