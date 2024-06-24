document.getElementById('showPassword').addEventListener('change', function() {
    var ids = ['password', 'newPassword', 'repeatPassword'];
    var passwordInputs = ids.map(function(id) {
        return document.getElementById(id);
    }).filter(function(input) {
        return input !== null;
    });

    passwordInputs.forEach(function(passwordInput) {
        if (this.checked) {
            passwordInput.type = 'text';
        } else {
            passwordInput.type = 'password';
        }
    }, this);
});


