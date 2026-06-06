function loadDocuments() {

    fetch("http://localhost:8080/employee/documents", {
        headers: getAuthHeader()
    })
        .then(res => res.json())
        .then(data => {

            let table = "";

            data.forEach(doc => {

                table += `
                <tr>
                    <td>${doc.id}</td>
                    <td>${doc.employee.firstName}</td>
                    <td>${doc.documentType}</td>
                    <td>${doc.status}</td>
                    <td>

                        <button class="btn btn-success btn-sm"
                            onclick="approveDoc(${doc.id})">
                            Approve
                        </button>

                        <button class="btn btn-danger btn-sm"
                            onclick="rejectDoc(${doc.id})">
                            Reject
                        </button>

                    </td>
                </tr>
            `;
            });

            document.getElementById("docTable").innerHTML = table;
        });
}

function approveDoc(id) {

    fetch(`http://localhost:8080/employee/documents/approve/${id}`, {
        method: "PUT",
        headers: getAuthHeader()
    })
        .then(() => {
            alert("Document Approved");
            loadDocuments();
        });
}

function rejectDoc(id) {

    const remarks = prompt("Enter rejection reason:");

    fetch(`http://localhost:8080/employee/documents/reject/${id}?remarks=${remarks}`, {
        method: "PUT",
        headers: getAuthHeader()
    })
        .then(() => {
            alert("Document Rejected");
            loadDocuments();
        });
}