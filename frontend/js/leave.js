function getEmployeeId() {
    const id = localStorage.getItem("employeeId");

    if (!id || id === "null" || id === "undefined") {
        alert("Employee ID missing. Please login again.");
        window.location.href = "login.html";
        return null;
    }

    return Number(id);
}

function applyLeave() {

    const empId = getEmployeeId();
    if (!empId) return;

    const payload = {
        employeeId: empId,
        fromDate: document.getElementById("fromDate").value,
        toDate: document.getElementById("toDate").value,
        reason: document.getElementById("reason").value,
        noOfDays: calculateDays()
    };

    fetch("http://localhost:8080/leave", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...getAuthHeader()
        },
        body: JSON.stringify(payload)
    })
        .then(res => {
            if (!res.ok) throw new Error("Leave Apply Failed");
            return res.json();
        })
        .then(() => {
            alert("Leave Applied Successfully");
            loadLeaves();
        })
        .catch(err => alert(err.message));
}

function loadLeaves() {

    const empId = getEmployeeId();
    if (!empId) return;

    fetch(`http://localhost:8080/leave/employee/${empId}`, {
        headers: getAuthHeader()
    })
        .then(res => res.json())
        .then(data => {

            let table = "";

            data.forEach(l => {
                table += `
                <tr>
                    <td>${l.id}</td>
                    <td>${l.fromDate}</td>
                    <td>${l.toDate}</td>
                    <td>${l.reason}</td>
                    <td>
                        <span class="badge ${
                    l.status === 'APPROVED' ? 'bg-success' :
                        l.status === 'MANAGER_APPROVED' ? 'bg-info' :
                            l.status === 'REJECTED' ? 'bg-danger' :
                                'bg-warning'
                }">
                            ${l.status}
                        </span>
                    </td>
                </tr>
            `;
            });

            document.getElementById("leaveTable").innerHTML = table;
        });
}