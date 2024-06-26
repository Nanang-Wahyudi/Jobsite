$(document).ready(function() {
    // Extract the applicant ID from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const applicantId = urlParams.get('applicantId');

    // Ensure the applicant ID is correctly retrieved
    if (!applicantId) {
        Swal.fire({
            position: "center",
            icon: "error",
            title: "Applicant ID not found!",
            showConfirmButton: false,
            timer: 1500,
        });
        return;
    }

    // Create Feedback
    $("#add-feedback").click((event) => {
        event.preventDefault();

        let valueRating = $("#rating").val();
        let valueComment = $("#comment").val();

        if (!valueRating || !valueComment) {
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
                url: `/api/client/create-feedback/${applicantId}`,
                dataType: "JSON",
                beforeSend: addCSRF(),
                contentType: "application/json",
                data: JSON.stringify({
                    rating: valueRating,
                    comment: valueComment
                }),
                success: (res) => {
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Feedback has been created.",
                        showConfirmButton: false,
                        timer: 1500,
                    });
                    $("#rating").val("");
                    $("#comment").val("");

                    window.location.href = "/applicant-history";
                },
                error: (err) => {
                    console.error(err);
                },
            });
        }
    });
});
