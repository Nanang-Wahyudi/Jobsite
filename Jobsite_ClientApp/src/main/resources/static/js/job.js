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

// Update status job
function updateStatusJob(id, isActive){
    let jobStatus = !isActive ? "Active" : "Inactive";
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger me-3"
        },
        buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
        title: "Are you sure?",
        text: "Change Job Status to " + jobStatus,
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, change it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                method: "PUT",
                url: "/api/client/job/update/status/" + id,
                dataType: "JSON",
                beforeSend: addCSRF(),
                contentType: "application/json",
            })
            .done((res) => {
                swalWithBootstrapButtons.fire({
                    title: "Changed!",
                    text: "Job status is now " + jobStatus,
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
                text: "Job status change has been cancelled",
                icon: "error"
            });
        }
    });
}

// Delete job
function deleteJob(id, title){;
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
                method: "DELETE",
                url: "/api/client/job/delete/" + id,
                dataType: "JSON",
                beforeSend: addCSRF(),
                contentType: "application/json",
            })
            .done((res) => {
                swalWithBootstrapButtons.fire({
                    title: "Deleted!",
                    text: title +" has been deleted",
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
                text: "Job deletion has been cancelled",
                icon: "error"
            });
        }
    });
}