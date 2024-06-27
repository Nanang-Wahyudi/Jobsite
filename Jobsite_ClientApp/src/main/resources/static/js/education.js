$(document).ready(function () {
    if ($("#education-update-page").length) {
        let id = getIdFromUrl();
        console.log(id);
        beforeUpdate(id)
    }
    
});

function getIdFromUrl(){
    const url = window.location.href;
    const urlObject = new URL(url);
    const pathSegments = urlObject.pathname.split('/');
    const id = pathSegments[pathSegments.length - 1];

    return id;
}

//Get last education values to show when the page is opened
function beforeUpdate(educationId){
    $.ajax({
        method: "GET",
        url: "/api/client/talent/profile",
        dataType: "JSON",
        contentType: "application/json"
    })
    .done((res) => {

        const educationResponses = res.educationResponses;
        const education = educationResponses.find(edu => edu.id === educationId);
        
        if (education) {
            $("#education-id").val(education.id);
            $("#instansiName").val(education.instansiName);
            $("#major").val(education.major);
            $("#avgScore").val(education.avgScore);
        } else {
            console.error("Education not found");
        }

    })
    .fail((err) => {
        console.log(err);
    }); 
}

// Update Education
$("#update-education").click((event) => {
    event.preventDefault();

    let valueId = $("#education-id").val();
    let valueInstansiName = $("#instansiName").val();
    let valueMajor = $("#major").val();
    let valueAvgScore = $("#avgScore").val();

    let missingFields = [];

    if(!valueInstansiName) missingFields.push('Institution Name')
    if(!valueMajor) missingFields.push('Major')
    if(!valueAvgScore) missingFields.push('Average Score')
    
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
            url: "/api/client/education/update/" + valueId,
            dataType: "JSON",
            beforeSend: addCSRF(),
            contentType: "application/json",
            data: JSON.stringify({
                instansiName: valueInstansiName,
                major: valueMajor,
                avgScore: valueAvgScore
            }),
            success: (res) => {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Education has been Updated!",
                    showConfirmButton: false,
                    timer: 1500,
                });
                $("#instansiName").val("");
                $("#major").val("");
                $("#avgScore").val("");
                
                setTimeout(() => {
                    window.location.href = "/talent/profile";
                }, 1750); 
            },
            error: (err) => {
                console.error(err);
            },
        });
    }
});

// Delete Education
function deleteEducation(valueId){
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger me-3"
        },
        buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {

            $.ajax({
                method: "PUT",
                url: "/api/client/education/delete/" + valueId,
                dataType: "JSON",
                beforeSend: addCSRF(),
                contentType: "application/json",
            })
            .done((res) => {
                swalWithBootstrapButtons.fire({
                    title: "Deleted!",
                    text: "Education has been deleted",
                    icon: "success"
                });

                setTimeout(() => {
                    location.reload();
                }, 2000); 
            })
            .fail((err) => {
                console.log(err);
            });
        } else if (
            result.dismiss === Swal.DismissReason.cancel
        ) {
            swalWithBootstrapButtons.fire({
                title: "Cancelled",
                text: "Education deletion has been cancelled",
                icon: "error"
            });
        }
    });
}