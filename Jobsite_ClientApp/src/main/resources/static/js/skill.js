// Delete skill
function deleteSkill(id, title){;
    console.log(id);
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
                method: "GET",
                url: "/api/client/skill/delete/" + id,
                dataType: "JSON",
                beforeSend: addCSRF(),
                contentType: "application/json",
            })
            .done((res) => {
                swalWithBootstrapButtons.fire({
                    title: "Deleted!",
                    text: title +" skill has been deleted",
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
                text: "Skill deletion has been cancelled",
                icon: "error"
            });
        }
    });
}