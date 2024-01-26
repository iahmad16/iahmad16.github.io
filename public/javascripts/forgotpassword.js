function forgot_password() {
    let email_input = document.getElementById('email').value
    let csrfToken = document.getElementById("csrfToken").value;
    let button = document.getElementById("login-button");

    button.disabled = true;

    const email_regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    // If email is empty or invalid
    if (email_input.length === 0) {
        alert("Email cannot be empty. Please enter again.")
    } else if(!email_regex.test(email_input)) {
        alert("Invalid Email Format!")
    } else
    {
        const url = `http://localhost:9000/admin/forgot-password?csrfToken=${csrfToken}`;
        // const url = `http://192.168.10.15:9000/login?csrfToken=${csrfToken}`;

        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                email: email_input
            })
        })
            .then(res => {
                if(res.status == 200){
                    alert("An email will be sent to you with your new password.")
                    window.location.href = '/admin/login'
                }
                else{
                    res.text().then(error => alert(error));
                }
            })
    }
}