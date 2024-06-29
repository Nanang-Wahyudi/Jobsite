// Register User
$("#register-user").click((event) => {
    event.preventDefault();

    let valueName= $("#name").val();
    let valueEmail = $("#email").val();
    let valueUsername = $("#username").val();
    let valuePassword = $("#password").val();

    let missingFields = [];

    if(!valueName) missingFields.push('Name')
    if(!valueEmail) missingFields.push('Email')
    if(!valueUsername) missingFields.push('Username')
    if(!valuePassword) missingFields.push('Password')
    
    if (missingFields.length > 0) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill these required fields!",
            html: missingFields.join('<br>'),
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

                setTimeout(() => {
                    window.location.href = "/login";
                }, 1750); 
            },
            error: (err) => {
                console.error(err);
                Swal.fire({
                    position: "center",
                    icon: "error",
                    title: "Failed to register account",
                    text: err.responseJSON.details,
                    showConfirmButton: false,
                    timer: 1500,
                })
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

    let missingFields = [];

    if(!valueName) missingFields.push('Name')
    if(!valueEmail) missingFields.push('Email')
    if(!valueUsername) missingFields.push('Username')
    if(!valuePassword) missingFields.push('Password')
    
    if (missingFields.length > 0) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill these required fields!",
            html: missingFields.join('<br>'),
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

                setTimeout(() => {
                    window.location.href = "/login";
                }, 1750); 
            },
            error: (err) => {
                console.error(err);
                Swal.fire({
                    position: "center",
                    icon: "error",
                    title: "Failed to register account",
                    text: err.responseJSON.details,
                    showConfirmButton: false,
                    timer: 1500,
                })
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

    let missingFields = [];

    if(!valueName) missingFields.push('Name')
    if(!valueEmail) missingFields.push('Email')
    if(!valueUsername) missingFields.push('Username')
    if(!valuePassword) missingFields.push('Password')
    
    if (missingFields.length > 0) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill these required fields!",
            html: missingFields.join('<br>'),
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

                setTimeout(() => {
                    window.location.href = "/login";
                }, 1750); 
            },
            error: (err) => {
                console.error(err);
                Swal.fire({
                    position: "center",
                    icon: "error",
                    title: "Failed to register account",
                    text: err.responseJSON.details,
                    showConfirmButton: false,
                    timer: 1500,
                })
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

    let missingFields = [];

    if(!valueUsername) missingFields.push('Username')
    if(!valuePassword) missingFields.push('Password')
    if(!valueRepeatPassword) missingFields.push('Repeat Password')
    
    if (missingFields.length > 0) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill these required fields!",
            html: missingFields.join('<br>'),
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

                setTimeout(() => {
                    window.location.href = "/login";
                }, 1750); 
            },
            error: (err) => {
                console.error(err);
                Swal.fire({
                    position: "center",
                    icon: "error",
                    title: "Failed to update password",
                    text: err.responseJSON.details,
                    showConfirmButton: false,
                    timer: 1500,
                })
            },
        });
    }
});


// Update Password
$("#update-password").click((event) => {
    event.preventDefault();

    let valuePassword = $("#password").val();
    let valueNewPassword = $("#newPassword").val();
    let valueRepeatPassword = $("#repeatPassword").val();

    let missingFields = [];

    if(!valuePassword) missingFields.push('Old Password')
    if(!valueNewPassword) missingFields.push('New Password')
    if(!valueRepeatPassword) missingFields.push('Repeat Password')

    if (missingFields.length > 0) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please fill these required fields!",
            html: missingFields.join('<br>'),
            showConfirmButton: false,
            timer: 1500,
        });
    } else {
        $.ajax({
            method: "PUT",
            url: "/api/client/auth/update-password",
            dataType: "JSON",
            beforeSend: addCSRF(),
            contentType: "application/json",
            data: JSON.stringify({
                password: valuePassword,
                newPassword: valueNewPassword,
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
                $("#password").val("");
                $("#newPassword").val("");
                $("#repeatPassword").val("");
                
                setTimeout(() => {
                    location.reload();
                }, 1750); 
            },
            error: (err) => {
                console.error(err);
                Swal.fire({
                    position: "center",
                    icon: "error",
                    title: "Failed to Update Password",
                    text: err.responseJSON.message,
                    showConfirmButton: false,
                    timer: 1500,
                });
            },
        });
    }
});