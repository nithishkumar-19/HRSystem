function addEmployee() {

    const payload = {

        firstName: document.getElementById("firstName").value,

        lastName: document.getElementById("lastName").value,

        personalEmail: document.getElementById("personalEmail").value,

        companyEmail: document.getElementById("companyEmail").value,

        gender: document.getElementById("gender").value,

        dob: document.getElementById("dob").value,

        department: document.getElementById("department").value,

        designation: document.getElementById("designation").value,

        mobile: document.getElementById("countryCode").value + document.getElementById("mobile").value,

        joiningDate: document.getElementById("joiningDate").value
    };

    fetch("http://localhost:8080/employee/create", {

        method: "POST",

        headers: {
            "Content-Type": "application/json",
            ...getAuthHeader()
        },

        body: JSON.stringify(payload)

    })
        .then(res => {

            if (!res.ok) {
                throw new Error("Employee creation failed");
            }

            return res.json();
        })
        .then(() => {

            alert("Employee Added Successfully");

            document.getElementById("firstName").value = "";
            document.getElementById("lastName").value = "";
            document.getElementById("personalEmail").value = "";
            document.getElementById("companyEmail").value = "";
            document.getElementById("mobile").value = "";
            document.getElementById("joiningDate").value = "";
            document.getElementById("gender").value = "";
            document.getElementById("dob").value = "";
            document.getElementById("department").value = "";
            document.getElementById("designation").value = "";

            loadEmployees();
        })
        .catch(err => {
            alert(err.message);
        });
}


function loadEmployees() {

    fetch("http://localhost:8080/employee/all", {

        headers: getAuthHeader()

    })
        .then(res => {

            if (!res.ok) {
                throw new Error("Failed to load employees");
            }

            return res.json();
        })
        .then(data => {

            let table = "";

            data.forEach(emp => {

                table += `
                <tr>

                    <td>${emp.id}</td>

                    <td>${emp.firstName || ""} ${emp.lastName || ""}</td>

                    <td>${emp.department || "-"}</td>

                    <td>${emp.designation || "-"}</td>

                    <td>${emp.gender || "-"}</td>

                    <td>${emp.personalEmail || "-"}</td>

                    <td>
                        <span class="badge ${
                    emp.status === 'COMPLETED'
                        ? 'bg-success'
                        : emp.status === 'IN_PROGRESS'
                            ? 'bg-warning'
                            : 'bg-secondary'
                }">
                            ${emp.status || 'PENDING'}
                        </span>
                    </td>

                </tr>
            `;
            });

            document.getElementById("employeeTable").innerHTML = table;
        })
        .catch(err => {
            console.error(err);
            alert("Unable to load employees");
        });
}