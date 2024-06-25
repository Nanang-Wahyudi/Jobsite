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
