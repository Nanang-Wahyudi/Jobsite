// Get All Talent for Admin
$(document).ready(() => {
    let rowNumber = 1;

    $("#table-talent").DataTable({
        ajax: {
            url: "/api/client/manage-talent",
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
                            <a class="btn btn-sm btn-outline-primary" href="/talent/detail/${data.id}">Detail</a>
                            <button class="btn btn-sm btn-outline-danger">Delete</button>
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
            url: "/api/client/manage-company",
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
                            <a class="btn btn-sm btn-outline-primary" href="/company/detail/${data.id}">Detail</a>
                            <button class="btn btn-sm btn-outline-danger">Delete</button>
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