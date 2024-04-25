
let data = fetch("http://localhost:8080/admin/users")
.then(res => res.json())
.then(data =>{
    JSON.stringify(data)
for (let key in data) {

    let btnEdit = document.createElement("a");
    btnEdit.textContent="Edit"
    btnEdit.title = "Edit";
    let id = data[key].id;
    btnEdit.href = 'http://localhost:8080/'+id+'/profile';
    btnEdit.classList.add("btn-sm")
    btnEdit.classList.add("btn-info")
    btnEdit.classList.add("edit-button")


    let btnDelete = document.createElement("a");
    btnDelete.textContent="Delete"
    btnDelete.title = "Delete";
    let idD = data[key].id;
    btnDelete.href = 'http://localhost:8080/'+idD+'/profile';
    btnDelete.classList.add("btn-sm")
    btnDelete.classList.add("btn-danger")
    btnDelete.classList.add("delete-button")


    let row = document.createElement('tr')
    row.innerHTML = `
                        <td>${data[key].id}</td>
                        <td>${data[key].name}</td>
                      <td>${data[key].lastname}</td>
                      <td>${data[key].username}</td>
                      <td>${data[key].email}</td>
                      <td>${data[key].roles.map(role=>{return role.name})}</td>
                     `
    let tdEdit = document.createElement("td");
    tdEdit.appendChild(btnEdit);
    let tdDelete = document.createElement("td");
    tdDelete.appendChild(btnDelete);
    row.appendChild(tdEdit);
    row.appendChild(tdDelete);

    document.querySelector('.table').appendChild(row)
}})

// console.log(data[key].sizes[key2].name);