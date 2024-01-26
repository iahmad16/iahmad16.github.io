document.addEventListener('DOMContentLoaded', function () {
    const rememberMe = document.getElementById("remember-me");
    const emailInput = document.getElementById("email");

    const storedUsername = localStorage.getItem("username");

    if (localStorage.checkbox && localStorage.checkbox === "checked" && storedUsername) {
        rememberMe.setAttribute("checked", "checked");
        emailInput.value = storedUsername;
    } else {
        rememberMe.removeAttribute("checked");
        emailInput.value = "";
    }
});

function login() {
    let email_input = document.getElementById('email').value
    let password_input = document.getElementById('password').value
    let remember_me = document.getElementById('remember-me')
    let csrfToken = document.getElementById("csrfToken").value;
    const loginButton = document.getElementById("login-button");
    const spinningIcon = document.createElement("i");
    spinningIcon.className = "fa fa-circle-o-notch fa-spin";

    loginButton.disabled = true;

    if (remember_me.checked && email_input.length > 0) {
        localStorage.setItem("username", email_input);
        localStorage.setItem("checkbox", "checked");
    } else {
        localStorage.removeItem("username");
        localStorage.removeItem("checkbox");
    }

    loginButton.innerHTML = "Logging In ";
    loginButton.appendChild(spinningIcon);

    // If inputs are empty
    if (email_input.length === 0 && password_input.length === 0) {
        alert("Email and Password cannot be empty. Please enter again.")
    } else if (email_input.length === 0) {
        alert("Email cannot be empty. Please enter again.")
    } else if (password_input.length === 0) {
        alert("Password cannot be empty. Please enter again.")
    } else {

        const email_regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if(!email_regex.test(email_input))
        {
            alert("Invalid Email Format!")
        }
        else
        {
            const url = `http://localhost:9000/admin/login?csrfToken=${csrfToken}`;
            // const url = `http://192.168.18.3:9000/admin/login?csrfToken=${csrfToken}`;
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: email_input,
                    password: password_input
                })
            })
            .then(res => {
                if(res.status === 200){
                    window.location.href = '/admin/dashboard'
                }
                else {
                    res.text().then(error => alert(error));
                    document.getElementById('password').value = '';
                }

            })
        }
    }
}