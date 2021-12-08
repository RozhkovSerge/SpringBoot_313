const editModal = new bootstrap.Modal(document.getElementById('editModal'));
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
const addNewUserForm = document.getElementById('addUserForm');
const editUserForm = document.getElementById('editUserForm');
const deleteUserForm = document.getElementById('deleteUserForm');

addNewUserForm.addEventListener('submit', proceedFormData);
editUserForm.addEventListener('submit', proceedFormData)
deleteUserForm.addEventListener('submit', proceedFormData)

function showAllUsers() {
    fetch('http://localhost:8080/api/users')
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            let row = "";
            data.forEach((user) => {
                let roleList = "";
                row += "<tr>";
                row += "<td>" + user.id + "</td>";
                row += "<td>" + user.firstname + "</td>";
                row += "<td>" + user.lastname + "</td>";
                row += "<td>" + user.age + "</td>";
                row += "<td>" + user.email + "</td>";

                user.roles.forEach((role) => {
                    roleList += `${role.name.substring(5)}, `;
                });
                roleList = roleList.slice(0, -2);
                row += "<td>" + roleList + "</td>";
                row += `<td><button type="button" class="btn editUser" data-user-id=${user.id}>Edit</button></td>`;
                row += `<td><button type="button" class="btn deleteUser" data-user-id=${user.id} >Delete</button></td>`;
                document.querySelector(".all-users").innerHTML = row;

                let editUserBtn = document.querySelectorAll('[data-user-id]');
                editUserBtn.forEach((e) => {
                    e.addEventListener('click', function () {
                        if (e.classList.contains('editUser')) {
                            populateEditModal(this.getAttribute('data-user-id'), 'edit')
                            editModal.show();
                        } else {
                            populateEditModal(this.getAttribute('data-user-id'), 'delete')
                            deleteModal.show();
                        }
                    })
                })
            });
        });
}

function getPrincipal() {
    fetch('http://localhost:8080/api/getPrincipal')
        .then((response) => {
            return response.json();
        })
        .then((user) => {
            let row = "";
            let roleList = "";
            row += "<tr>";
            row += "<td>" + user.id + "</td>";
            row += "<td>" + user.firstname + "</td>";
            row += "<td>" + user.lastname + "</td>";
            row += "<td>" + user.age + "</td>";
            row += "<td>" + user.email + "</td>";

            user.roles.forEach((role) => {
                roleList += `${role.name.substring(5)}, `;
            });
            roleList = roleList.slice(0, -2);

            row += "<td>" + roleList + "</td></tr>";
            document.querySelector(".user-info").innerHTML = row;
            document.querySelector('.principal-info').innerHTML = `Welcome, <b>${user.email}</b> with role ${roleList}`;
        });
}

function proceedFormData(e) {
    let action = this.getAttribute('id');
    e.preventDefault();
    const formData = new FormData(this);
    let json = getJson(this);
     console.log(json)

    switch (action) {
        case  'addUserForm':
            fetch('http://localhost:8080/api/users', {
                method: 'post',
                body: JSON.stringify(json),
                headers:{
                    'Content-Type':'application/json'
                }
            }).then(function (response) {
                return response.text();
            }).then(function (text) {
            }).catch(function (error) {
                console.log(error);
            })
            setTimeout(showAllUsers, 200);
            document.getElementById('home-tab').click();
            break;

        case 'editUserForm':
            fetch('http://localhost:8080/api/users', {
                method: 'put',
                body: JSON.stringify(json),
                headers:{
                'Content-Type': 'application/json'
                }
            }).then(function (response) {
                return response.text();
            }).then(function (text) {
            }).catch(function (error) {
                console.log(error);
            })
            editModal.hide();
            setTimeout(showAllUsers, 200);
            document.getElementById('home-tab').click();
            break;

        case 'deleteUserForm':
            let id = formData.get("id");
            fetch('http://localhost:8080/api/users/'+id, {
                method: 'delete',
                body: JSON.stringify(json),
                headers:{
                    'Content-Type':'application/json'
                }
            }).then(function (response) {
                return response.text();
            }).then(function (text) {
            }).catch(function (error) {
                console.log(error);
            })
            deleteModal.hide();
            setTimeout(showAllUsers, 200);
            document.getElementById('home-tab').click();
    }
}

function populateEditModal(id, action) {
    let editModal = document.getElementById(`${action}Modal`);
    let roleOptions = editModal.querySelectorAll('option');
    roleOptions.forEach((e) => {
        e.removeAttribute("selected");
    })

    fetch('http://localhost:8080/api/users/' + id)
        .then((response) => {
            return response.json();
        })
        .then((user) => {
            editModal.querySelector('[name="fake_id"]').setAttribute("value", user.id);
            editModal.querySelector('[name="id"]').setAttribute("value", user.id);
            editModal.querySelector('[name="firstname"]').setAttribute("value", user.firstname);
            editModal.querySelector('[name="lastname"]').setAttribute("value", user.lastname);
            editModal.querySelector('[name="age"]').setAttribute("value", user.age);
            editModal.querySelector('[name="email"]').setAttribute("value", user.email);
            editModal.querySelector('[name="password"]').setAttribute("value", user.password);
            roleOptions.forEach((e) => {
                user.roles.forEach((role) => {
                    if (role.name.substring(5).trim() === e.textContent.trim()) {
                        e.setAttribute("selected", "selected");
                    }
                })
            })
        });
}

function getJson(form) {
    let js = {};
    let field;
    for (let i = 0; i < form.elements.length; i++) {
        field = form.elements[i];
        if (field.name && field.type !== 'submit') {
            js[field.name] = field.value;
            if (field.type === 'select-multiple') {
                let js_sub = [];
                for (let j = form.elements[i].options.length - 1; j >= 0; j--) {
                    if (field.options[j].selected) {
                        if (js[field.name] === undefined) {
                            js[field.name] = [];
                        }
                        if (field.options[j].value === "ROLE_ADMIN") {
                            js_sub.push({"id": 1, "name": field.options[j].value});
                        } else if (field.options[j].value === "ROLE_USER") {
                            js_sub.push({"id": 2, "name": field.options[j].value});
                        }
                    }
                }
                js[field.name] = js_sub;
            } else {
                js[field.name] = field.value
            }
        }
    }
    return js;
}

showAllUsers();
getPrincipal();