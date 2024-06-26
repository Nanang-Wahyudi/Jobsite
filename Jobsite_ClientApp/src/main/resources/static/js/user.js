$(document).ready(function () {
    if ($("#user-update-page").length) {
        beforeUpdate();
    }
});

//Get last user profile values to show when the page is opened
function beforeUpdate(){
    $.ajax({
        method: "GET",
        url: "/api/client/talent/profile",
        dataType: "JSON",
        contentType: "application/json"
    })
    .done((res) => {
        $("#name").val(res.name);
        $("#email").val(res.email);
        $("#address").val(res.address);
        $("#description").val(res.description);
    })
    .fail((err) => {
        console.log(err);
    }); 
}

// Update User Profile
$("#update-user").click((event) => {
    event.preventDefault();

    let formData = new FormData();
    formData.append('name', $("#name").val());
    formData.append('email', $("#email").val());
    formData.append('address', $("#address").val());
    formData.append('description', $("#description").val());
    formData.append('instansiName', $("#instansiName").val());
    formData.append('major', $("#major").val());
    formData.append('avgScore', $("#avgScore").val());
    formData.append('skillName', $("#skillName").val());
    formData.append('picture', $("#picture")[0].files[0]);

    let missingFields = [];

    if (!$("#name").val()) missingFields.push('Name')
    if (!$("#email").val()) missingFields.push('Email')
    if (!$("#address").val()) missingFields.push('Address')

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
            url: "/api/client/talent/profile/update",
            dataType: "JSON",
            beforeSend: addCSRF(),
            data: formData,
            processData: false,
            contentType: false,
        })
        .done((res) => {
            Swal.fire({
                position: "center",
                icon: "success",
                title: "User has been successfully updated!",
                showConfirmButton: false,
                timer: 1500
            });
            $("#name").val("")
            $("#email").val("")
            $("#address").val("")
            $("#description").val("")
            $("#instansiName").val("")
            $("#major").val("")
            $("#avgScore").val("")
            $("#skillName").val("")
            $("#picture").val("")

            setTimeout(() => {
                window.location.href = "/talent/profile";
            }, 1750);
        })
        .fail((err) => {
            console.log(err);
        });
    }
});