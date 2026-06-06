function getAuthHeader() {

    const auth = localStorage.getItem("auth");

    return {
        "Authorization": "Basic " + auth
    };
}

function loadDashboard() {

    fetch("http://localhost:8080/dashboard/hr", {
        headers: getAuthHeader()
    })
        .then(res => {
            if (!res.ok) throw new Error("Dashboard API failed");
            return res.json();
        })
        .then(data => {

            document.getElementById("totalEmployees").innerText = data.totalEmployees ?? 0;
            document.getElementById("pendingLeaves").innerText = data.onboardingPending ?? 0;
            document.getElementById("pendingCerts").innerText = data.pendingDocuments ?? 0;

        })
        .catch(err => {
            console.log(err);
            alert("Session expired / login again");
            window.location.href = "login.html";
        });
}