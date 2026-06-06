function login() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    })
        .then(res => {
            if (!res.ok) throw new Error("Invalid Login");
            return res.json();
        })
        .then(data => {

            console.log("LOGIN RESPONSE:", data);

            localStorage.setItem("auth", btoa(username + ":" + password));
            localStorage.setItem("role", data.role);

            // 🔥 FORCE SAFE STRING CONVERSION
            if (data.employeeId !== null && data.employeeId !== undefined) {
                localStorage.setItem("employeeId", String(data.employeeId));
            } else {
                console.warn("Employee ID missing in response");
                localStorage.removeItem("employeeId"); // clean state
            }

            if (data.role === "HR") {
                window.location.href = "hr-dashboard.html";
            } else if (data.role === "MANAGER") {
                window.location.href = "manager-dashboard.html";
            } else {
                window.location.href = "employee-dashboard.html";
            }
        })
        .catch(err => {
            document.getElementById("msg").innerText = err.message;
        });
}