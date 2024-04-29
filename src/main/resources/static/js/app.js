
document.getElementById("close-btn").addEventListener("click", function () {
    document.getElementById("my-modal").classList.add("close")
    document.location.href = '/user-list'

})

document.getElementById("close-footer").addEventListener("click", function () {
    document.getElementById("my-modal").classList.add("close")
    document.location.href = '/user-list'

})

document.getElementById("save").addEventListener("click", function () {
    const formElement = document.getElementById('userprofile-form');
    const data1 = {};

    for (const element of formElement) {
        if (element.name) {
            // if (element.name === "firstname") {
            //     data1["name"] = element.value
            // } else {
            //     if (!(element.name === 'roles')) {
            //         // console.log(element.name)
            //         // console.log(element.value)
                    data1[element.name] = element.value;
            //     }
            // }
        }
    }
    // data1["confirmPassword"] = data1["password"]
    console.log(data1)
    const searchParams = JSON.stringify(data1);

    fetch('http://localhost:8080/user-update', {
                method: 'PATCH',
                body: {"user": searchParams},
                headers: {
                    'Content-Type': 'application/json',
                },
            })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Данные успешно отправлены:', data1);
            // Здесь вы можете обработать ответ от сервера
        })
        .catch(error => {
            console.error('There was an error!', error);
            // Здесь вы можете обработать ошибку
        });

})
// document.getElementById("save").addEventListener("click", function (){
//     fetch("http://localhost:8080/admin/users",
//         {
//             method: "PATCH",
//         }
//
//     )
// })

// $(document).ready(function () {
//     $('.edit-button').on('click', function (event) {
//         event.preventDefault();
//         $('#user-profile').modal("show").find('.modal-dialog').load($(this).attr('href'), function(response, status, xhr) {
//             if (xhr.status === 404) {
//                 $(location).attr('href', '/admin');
//             }
//
//             let submit = $('#user-profile .modal-footer .submit');
//             submit.text('Save');
//             submit.addClass('btn-primary');
//             $('#user-profile #method').val("patch");
//         });
//     });
//
//     $('.delete-button').on('click', function (event) {
//         event.preventDefault();
//         $('#user-profile').modal("show").find('.modal-dialog').load($(this).attr('href'), function(response, status, xhr) {
//             if (xhr.status === 404) {
//                 $(location).attr('href', '/admin');
//             }
//             $('#user-profile .modal-header h3').text('Delete User');
//             $('#user-profile #password-div').remove();
//             $("#user-profile #firstName").prop("readonly", true);
//             $("#user-profile #lastName").prop("readonly", true);
//             // $("#user-profile #age").prop("readonly", true);
//             $("#user-profile #email").prop("readonly", true);
//             $("#user-profile #roles").prop("disabled", true);
//             let submit = $('#user-profile .modal-footer .submit');
//             submit.text('Delete');
//             submit.addClass('btn-danger');
//             $('#user-profile #method').val("delete");
//         });
//     });
// });
