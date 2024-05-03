let close_btn = document.getElementById("close-btn")
if (close_btn) {

    close_btn.addEventListener("click", function () {
            document.getElementById("my-modal").classList.add("close")
            document.location.href = '/admin/user-list'
        }
    )
}


let close_foot = document.getElementById("close-footer")
if (close_foot) {
    close_foot.addEventListener("click", function () {
        document.getElementById("my-modal").classList.add("close")
        document.location.href = '/admin/user-list'
    })
}




function saveUser() {
    const formElement = document.getElementById('userprofile-form');
    const data1 = {};

    let role_container = []
    for (const element of formElement) {

        if (element.name) {
            if (element.name) {

                if (element.name === 'roles') {
                    let select = document.getElementById("roles-select");
                    let selectedValues = Array.from(select.options) // Преобразование HTMLOptionsCollection в массив
                        .filter(option => option.selected)
                        .map(option => option.value);
                    console.log(selectedValues);
                    let id = 1
                    for (let role of selectedValues) {
                        console.log(role)
                        let roleMy = {};
                        // console.log(role.options[select]);

                        roleMy["name"] = role;
                        id++
                        role_container.push(roleMy)
                    }
                } else {
                    data1[element.name === "firstname" ? "name" : element.name] = element.value;
                }
            }
        }
    }


    data1["roles"] = role_container;

    // console.log(data1)
    const searchParams = JSON.stringify(data1);

    fetch('http://localhost:8080/admin/user-update', {
        method: 'PATCH',
        body: searchParams,
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                console.log(response.json())
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Данные успешно отправлены:', data1);
        })
        .catch(error => {
            console.error('There was an error!', error);
        });
    document.location.href = '/admin/user-list'

}

function deleteUser() {
    let id = document.getElementById('id').value;
    console.log(id)
    let url = 'http://localhost:8080/admin/user-delete/' + id
    fetch(url, {
        method: 'DELETE',

    })
        .then(response => {
            if (!response.ok) {
                console.log(response.json())
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Данные успешно удалены:', data1);
        })
        .catch(error => {
            console.error('There was an error!', error);
        });
    document.location.href = '/admin/user-list'
}


