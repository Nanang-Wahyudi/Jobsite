// Add Job
$("#add-job").click((event) => {
    event.preventDefault();

    let valueTitle= $("#title").val();
    let valueType = $("#type").val();
    let valueSalary = $("#salary").val();
    let valueDescription = $("#description").val();
    let valueQualification = $("#qualification").val();

    if (!valueTitle || !valueType || !valueSalary || !valueDescription || !valueQualification) {
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
            url: "/api/client/job/create",
            dataType: "JSON",
            beforeSend: addCSRF(),
            contentType: "application/json",
            data: JSON.stringify({
                title: valueTitle,
                type: valueType,
                salary: valueSalary,
                description: valueDescription,
                qualification: valueQualification
            }),
            success: (res) => {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Job has been Add.",
                    showConfirmButton: false,
                    timer: 1500,
                });
                $("#title").val("");
                $("#type").val("");
                $("#salary").val("");
                $("#description").val("");
                $("#qualification").val("");
                
                window.location.href = "/company/profile";
            },
            error: (err) => {
                console.error(err);
            },
        });
    }
});