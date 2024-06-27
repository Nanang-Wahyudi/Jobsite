// Get All Talent for Admin
$(document).ready(() => {
    let rowNumber = 1;

    $("#table-talent").DataTable({
        ajax: {
            url: "/api/client/admin/manage-talent",
            dataSrc: ""
        },
        columnDefs: [{ className: "text-center", targets: "_all" }],
        columns: [ 
            { 
                data: null,
                render: () => rowNumber++,
            },
             {
                data: "urlPicture",
                render: (url) => {
                    return `<img src="${url}" alt="" width="60">`;
                },
            },
            { data: "username" },
            { data: "name" },
            { data: "email" },
            { data: "address" },
            {
                data: null,
                render: (data) => {
                    return /*html*/ `
                        <div class="d-flex gap-2 justify-content-center align-items-center">
                            <a class="btn btn-sm btn-outline-primary" href="/talent/detail/${data.id}"
                                title="Detail ${data.name}">Detail</a>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteUserByAdmin('${data.id}', '${data.name}')"
                                title="Delete ${data.name}">Delete</button>
                        </div>
                    `;
                },
            },
        ],
        drawCallback: function(settings) {
            // Update row numbers after each draw
            const api = this.api();
            const start = api.page.info().start;
            api.column(0, { search: "applied", order: "applied" }).nodes().each(function(cell, i) {
                cell.innerHTML = start + i + 1;
            });
        }
    });
});


// Get All Company for Admin
$(document).ready(() => {
    let rowNumber = 1;

    $("#table-company").DataTable({
        ajax: {
            url: "/api/client/admin/manage-company",
            dataSrc: ""
        },
        columnDefs: [{ className: "text-center", targets: "_all" }],
        columns: [ 
            { 
                data: null,
                render: () => rowNumber++,
            },
             {
                data: "urlPicture",
                render: (url) => {
                    return `<img src="${url}" alt="" width="60">`;
                },
            },
            { data: "username" },
            { data: "name" },
            { data: "email" },
            { data: "address" },
            {
                data: null,
                render: (data) => {
                    return /*html*/ `
                        <div class="d-flex gap-2 justify-content-center align-items-center">
                            <a class="btn btn-sm btn-outline-primary" href="/company/detail/${data.id}"
                                title="Detail ${data.name}">Detail</a>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteCompanyByAdmin('${data.id}', '${data.name}')"
                                title="Delete ${data.name}">Delete</button>
                        </div>
                    `;
                },
            },
        ],
        drawCallback: function(settings) {
            // Update row numbers after each draw
            const api = this.api();
            const start = api.page.info().start;
            api.column(0, { search: "applied", order: "applied" }).nodes().each(function(cell, i) {
                cell.innerHTML = start + i + 1;
            });
        }
    });
});

// Delete User Account by Admin
function deleteUserByAdmin(id, name){
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
                url: "/api/client/admin/delete-user/" + id,
                dataType: "JSON",
                beforeSend: addCSRF(),
                contentType: "application/json",
            })
            .done((res) => {
                $("#table-talent").DataTable().ajax.reload();
                swalWithBootstrapButtons.fire({
                    title: "Deleted!",
                    text: name +" account has been deleted",
                    icon: "success"
                });

            })
            .fail((err) => {
                console.log(err);
            });
        } else if (
            result.dismiss === Swal.DismissReason.cancel
        ) {
            swalWithBootstrapButtons.fire({
                title: "Cancelled",
                text: "User account deletion has been cancelled",
                icon: "error"
            });
        }
    });
}

// Delete Company Account by Admin
function deleteCompanyByAdmin(id, name){
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
                url: "/api/client/admin/delete-company/" + id,
                dataType: "JSON",
                beforeSend: addCSRF(),
                contentType: "application/json",
            })
            .done((res) => {
                $("#table-talent").DataTable().ajax.reload();
                swalWithBootstrapButtons.fire({
                    title: "Deleted!",
                    text: name +" account has been deleted",
                    icon: "success"
                });
            })
            .fail((err) => {
                console.log(err);
            });
        } else if (
            result.dismiss === Swal.DismissReason.cancel
        ) {
            swalWithBootstrapButtons.fire({
                title: "Cancelled",
                text: "Company account deletion has been cancelled",
                icon: "error"
            });
        }
    });
}