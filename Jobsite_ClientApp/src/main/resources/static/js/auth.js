// Register User
$("#register-user").click((event) => {
    event.preventDefault();

    let valueName= $("#name").val();
    let valueEmail = $("#email").val();
    let valueUsername = $("#username").val();
    let valuePassword = $("#password").val();

    if (!valueName || !valueEmail || !valueUsername || !valuePassword) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill all fields!",
            showConfirmButton: false,
            timer: 1500,
        });
    } else {
        $.ajax({
            method: "POST",
            url: "https://dev.ezcoder.my.id/api/auth/register/user",
            dataType: "JSON",
            contentType: "application/json",
            data: JSON.stringify({
                name: valueName,
                email: valueEmail,
                username: valueUsername,
                password: valuePassword
            }),
            success: (res) => {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Register User Successfully",
                    showConfirmButton: false,
                    timer: 1500,
                });
                $("#name").val("");
                $("#email").val("");
                $("#username").val("");
                $("#password").val("");

            },
            error: (err) => {
                console.error(err);
            },
        });
    }
});


// Register Company
$("#register-company").click((event) => {
    event.preventDefault();

    let valueName= $("#name").val();
    let valueEmail = $("#email").val();
    let valueUsername = $("#username").val();
    let valuePassword = $("#password").val();

    if (!valueName || !valueEmail || !valueUsername || !valuePassword) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill all fields!",
            showConfirmButton: false,
            timer: 1500,
        });
    } else {
        $.ajax({
            method: "POST",
            url: "https://dev.ezcoder.my.id/api/auth/register/company",
            dataType: "JSON",
            contentType: "application/json",
            data: JSON.stringify({
                name: valueName,
                email: valueEmail,
                username: valueUsername,
                password: valuePassword
            }),
            success: (res) => {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Register Company Successfully",
                    showConfirmButton: false,
                    timer: 1500,
                });
                $("#name").val("");
                $("#email").val("");
                $("#username").val("");
                $("#password").val("");

            },
            error: (err) => {
                console.error(err);
            },
        });
    }
});


// Register Admin
$("#register-admin").click((event) => {
    event.preventDefault();

    let valueName= $("#name").val();
    let valueEmail = $("#email").val();
    let valueUsername = $("#username").val();
    let valuePassword = $("#password").val();

    if (!valueName || !valueEmail || !valueUsername || !valuePassword) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill all fields!",
            showConfirmButton: false,
            timer: 1500,
        });
    } else {
        $.ajax({
            method: "POST",
            url: "https://dev.ezcoder.my.id/api/auth/register/admin",
            dataType: "JSON",
            contentType: "application/json",
            data: JSON.stringify({
                name: valueName,
                email: valueEmail,
                username: valueUsername,
                password: valuePassword
            }),
            success: (res) => {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Register Admin Successfully",
                    showConfirmButton: false,
                    timer: 1500,
                });
                $("#name").val("");
                $("#email").val("");
                $("#username").val("");
                $("#password").val("");

            },
            error: (err) => {
                console.error(err);
            },
        });
    }
});


// Forgot Password
$("#forgot-password").click((event) => {
    event.preventDefault();

    let valueUsername = $("#username").val();
    let valuePassword = $("#password").val();
    let valueRepeatPassword = $("#repeatPassword").val();

    if (!valueUsername || !valuePassword || !valueRepeatPassword) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill all fields!",
            showConfirmButton: false,
            timer: 1500,
        });
    } else {
        $.ajax({
            method: "PUT",
            url: "https://dev.ezcoder.my.id/api/auth/forgot-password",
            dataType: "JSON",
            contentType: "application/json",
            data: JSON.stringify({
                username: valueUsername,
                newPassword: valuePassword,
                repeatNewPassword: valueRepeatPassword,
            }),
            success: (res) => {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Password Successfully Updated",
                    showConfirmButton: false,
                    timer: 1500,
                });
                $("#username").val("");
                $("#password").val("");
                $("#repeatPassword").val("");
                
            },
            error: (err) => {
                console.error(err);
            },
        });
    }
});