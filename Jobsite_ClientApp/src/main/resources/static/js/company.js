$(document).ready(function () {
    if ($("#company-update-page").length) {
        beforeUpdate();
    }
});

//Get last company profile values to show when the page is opened
function beforeUpdate(){
    $.ajax({
        method: "GET",
        url: "/api/client/company/profile",
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

// Update Company Profile
$("#update-company").click((event) => {
    event.preventDefault();

    let formData = new FormData();
    formData.append('name', $("#name").val());
    formData.append('email', $("#email").val());
    formData.append('address', $("#address").val());
    formData.append('description', $("#description").val());
    formData.append('picture', $("#picture")[0].files[0]);
    formData.append('banner', $("#banner")[0].files[0]);

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
            url: "/api/client/company/profile/update",
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
                title: "Company has been successfully updated!",
                showConfirmButton: false,
                timer: 1500
            });
            $("#name").val("")
            $("#email").val("")
            $("#address").val("")
            $("#description").val("")
            $("#picture").val("")
            $("#banner").val("")

            setTimeout(() => {
                window.location.href = "/company/profile";
            }, 1750);
        })
        .fail((err) => {
            console.log(err);
        });
    }
});