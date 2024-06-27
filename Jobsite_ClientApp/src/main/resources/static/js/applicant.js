$(document).ready(function() {
    $('#applyForm').on('submit', function(event) {
        event.preventDefault();

        var fileInput = $('#fileInput')[0].files[0];
        var jobId = $('#jobId').val();

        if (!fileInput) {
            Swal.fire({
                position: "center",
                icon: "error",
                title: "Please select a file!",
                showConfirmButton: false,
                timer: 1500,
            });
            return;
        }

        var maxSize = 5 * 1024 * 1024;
        if (fileInput.size > maxSize) {
            Swal.fire({
                position: "center",
                icon: "error",
                title: "File size exceeds the maximum limit of 5MB!",
                showConfirmButton: false,
                timer: 1500,
            });
            return;
        }

        var formData = new FormData();
        formData.append('file', fileInput);

        $.ajax({
            url: '/api/client/applicant/create/' + jobId,
            type: 'POST',
            data: formData,
            beforeSend: addCSRF(),
            contentType: false,
            processData: false,
            success: function(response) {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Applicant has been Created",
                    showConfirmButton: false,
                    timer: 1500,
                });
                window.location.href = "/applicant-history";
            },
            error: function(xhr, status, error) {
                console.log(xhr, status, error);
                Swal.fire({
                    position: "center",
                    icon: "error",
                    title: "Failed to create applicant",
                    showConfirmButton: true,
                });
            }
        });
    });
});

//Update Applicant Status
$("#update-applicant-status").click((event) => {
    event.preventDefault();

    let valueStatus= $("#applicant-status").val();
    let valueId = $("#applicant-id").val();

    if (!valueStatus) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Please choose a status for the applicant!",
            showConfirmButton: false,
            timer: 1500,
        });
    } else {
        $.ajax({
            method: "PUT",
            url: "/api/client/applicant/status/" + valueId,
            dataType: "JSON",
            beforeSend: addCSRF(),
            contentType: "application/json",
            data: JSON.stringify({
                status: valueStatus,
            }),
            success: (res) => {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Applicant Status Updated!",
                    showConfirmButton: false,
                    timer: 1500,
                });
                $("#applicant-status").val("");
                
                setTimeout(() => {
                    location.reload();
                }, 1750); 
            },
            error: (err) => {
                console.error(err);
            },
        });
    }
});