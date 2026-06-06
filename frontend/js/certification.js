function uploadCertification() {

    const payload = {
        employeeId: document.getElementById("employeeId").value,
        certificationName: document.getElementById("certName").value,
        issuingOrganization: document.getElementById("org").value,
        certificateFilePath: document.getElementById("filePath").value
    };

    fetch("http://localhost:8080/certification/upload", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...getAuthHeader()
        },
        body: JSON.stringify(payload)
    })
        .then(res => {
            if (!res.ok) throw new Error("Upload failed");
            return res.json();
        })
        .then(() => {
            alert("Uploaded Successfully");
            loadCertifications();
        })
        .catch(err => alert(err.message));
}


// ================= LOAD CERTIFICATIONS =================
function loadCertifications() {

    fetch("http://localhost:8080/certification/all", {
        headers: getAuthHeader()
    })
        .then(res => res.json())
        .then(data => {

            let table = "";

            data.forEach(c => {

                table += `
<tr>
    <td>${c.id}</td>
    <td>${c.certificationName}</td>
    <td>${c.issuingOrganization}</td>

    <!-- STATUS COLUMN -->
    <td>
        <span class="badge 
            ${c.status === 'APPROVED' ? 'bg-success' :
                    c.status === 'REJECTED' ? 'bg-danger' : 'bg-warning'}">
            ${c.status}
        </span>
    </td>

    <!-- ACTION COLUMN -->
    <td>

        <button class="btn btn-success btn-sm" onclick="approveCert(${c.id})">
            Approve
        </button>

        <button class="btn btn-danger btn-sm" onclick="rejectCert(${c.id})">
            Reject
        </button>

        <button class="btn btn-warning btn-sm" onclick="addSkill(${c.id})">
            Add Skill
        </button>

    </td>
</tr>
`;
            });

            document.getElementById("certTable").innerHTML = table;
        });
}


// ================= APPROVE =================
function approveCert(id) {

    fetch(`http://localhost:8080/certification/approve/${id}?remarks=ok`, {
        method: "PUT",
        headers: getAuthHeader()
    })
        .then(() => {
            alert("Approved");
            loadCertifications();
        });
}


// ================= REJECT =================
function rejectCert(id) {

    fetch(`http://localhost:8080/certification/reject/${id}?remarks=not approved`, {
        method: "PUT",
        headers: getAuthHeader()
    })
        .then(() => {
            alert("Rejected");
            loadCertifications();
        });
}


// ================= ADD SKILL =================
function addSkill(certId) {

    const skillName = prompt("Enter Skill Name:");
    const level = prompt("Enter Proficiency Level:");

    if (!skillName || !level) {
        alert("Invalid input");
        return;
    }

    fetch(`http://localhost:8080/certification/add-skill/${certId}?skillName=${skillName}&proficiencyLevel=${level}`, {
        method: "POST",
        headers: getAuthHeader()
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to add skill");
            return res.text();
        })
        .then(() => {
            alert("Skill Added Successfully");
        })
        .catch(err => alert(err.message));
}