function generatePayslip() {

    const payload = {
        employeeId: Number(document.getElementById("employeeId").value),
        month: document.getElementById("month").value,
        year: Number(document.getElementById("year").value),
        basicSalary: Number(document.getElementById("basic").value),
        allowances: Number(document.getElementById("allowances").value),
        deductions: Number(document.getElementById("deductions").value)
    };

    fetch("http://localhost:8080/payslip/generate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...getAuthHeader()
        },
        body: JSON.stringify(payload)
    })
        .then(res => {
            if (!res.ok) throw new Error("Payslip generation failed");
            return res.json();
        })
        .then(() => {
            alert("Payslip Generated 💰");
        })
        .catch(err => alert(err.message));
}

function loadPayslips() {

    const empId = document.getElementById("searchEmpId").value;

    fetch(`http://localhost:8080/payslip/employee/${empId}`, {
        headers: getAuthHeader()
    })
        .then(res => res.json())
        .then(data => {

            let table = "";

            data.forEach(p => {

                table += `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.month}</td>
                    <td>${p.year}</td>
                    <td>${p.netSalary}</td>
                    <td>
                        <a href="${p.pdfPath}" target="_blank" class="btn btn-sm btn-warning">
                            View PDF
                        </a>
                    </td>
                </tr>
            `;
            });

            document.getElementById("payslipTable").innerHTML = table;
        });
}