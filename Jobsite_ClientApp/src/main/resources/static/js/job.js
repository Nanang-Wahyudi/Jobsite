$(document).ready(function() {
    if ($("#job-update-page").length) {
        let jobId = $("#job-id").val();
        beforeUpdate(jobId);
    }
});

// Add Job
$("#add-job").click((event) => {
    event.preventDefault();

    let valueTitle= $("#title").val();
    let valueType = $("#type").val();
    let valueSalary = $("#salary").val();
    let valueDescription = $("#description").val();
    let valueQualification = $("#qualification").val();

    let missingFields = [];

    if (!valueTitle) missingFields.push('Title');
    if (!valueType) missingFields.push('Type');
    if (!valueSalary) missingFields.push('Salary');
    if (!valueDescription) missingFields.push('Description');
    if (!valueQualification) missingFields.push('Address');

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


//Get last job values to show when the page is opened
function beforeUpdate(id){
    $.ajax({
        method: "GET",
        url: "/api/client/job/detail/" + id,
        dataType: "JSON",
        contentType: "application/json"
    })
    .done((res) => {
        $("#title").val(res.title);
        $("#type").val(res.type);
        $("#salary").val(res.salary);
        $("#description").val(res.description);
        $("#qualification").val(res.qualification);
    })
    .fail((err) => {
        console.log(err);
    }); 
}

// Update Job
$("#update-job").click((event) => {
    event.preventDefault();

    let valueId = $("#job-id").val();
    let valueTitle = $("#title").val();
    let valueType = $("#type").val();
    let valueSalary = $("#salary").val();
    let valueDescription = $("#description").val();
    let valueQualification = $("#qualification").val();

    let missingFields = [];

    if (!valueTitle) missingFields.push('Title');
    if (!valueType) missingFields.push('Type');
    if (!valueSalary) missingFields.push('Salary');
    if (!valueDescription) missingFields.push('Description');
    if (!valueQualification) missingFields.push('Address');

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
            url: "/api/client/job/update/" + valueId,
            dataType: "JSON",
            beforeSend: addCSRF(),
            contentType: "application/json",
            data: JSON.stringify({
                title: valueTitle,
                type: valueType,
                salary: valueSalary,
                description: valueDescription,
                qualification: valueQualification,
            }),
            success: (res) => {
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Job has been Updated!",
                    showConfirmButton: false,
                    timer: 1500,
                });
                $("#job-id").val("");
                $("#title").val("");
                $("#type").val("");
                $("#salary").val("");
                $("#description").val("");
                $("#qualification").val("");
                
                setTimeout(() => {
                    window.location.href = "/company/profile";
                }, 1750); 
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
                }, 1750); 
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
                }, 1750); 
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