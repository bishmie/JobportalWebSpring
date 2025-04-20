
    // Simple function to handle candidate actions
    function handleAction(action, candidateName) {
    alert(`${action} ${candidateName}'s application`);
    // In a real application, you would make an API call here
}

    // Attach event listeners to all action buttons
    document.querySelectorAll('.btn-success').forEach(btn => {
    btn.addEventListener('click', function() {
        const candidateName = this.closest('tr').querySelector('h6').textContent;
        handleAction('Approved', candidateName);
    });
});

    document.querySelectorAll('.btn-danger').forEach(btn => {
    btn.addEventListener('click', function() {
        const candidateName = this.closest('tr').querySelector('h6').textContent;
        handleAction('Rejected', candidateName);
    });
});

    document.querySelectorAll('.btn-light').forEach(btn => {
    btn.addEventListener('click', function() {
        const candidateName = this.closest('tr').querySelector('h6').textContent;
        alert(`Downloading ${candidateName}'s application`);
    });
});



    const token = localStorage.getItem("authToken");

    if (!token) {
    alert("User not authenticated!");
} else {
    try {
    const decoded = jwt_decode(token);
    const userEmail = decoded.email || decoded.sub;

    if (!userEmail) {
    alert("Email not found in token!");
}

    // Get user by email to fetch employerId
    $.ajax({
    url: `http://localhost:8090/api/v1/users/getByEmail`,
    method: "GET",
    headers: {
    "Authorization": "Bearer " + token,
    "Content-Type": "application/json"
},
    success: function (userData) {
    const employerId = userData.userId;

    if (!employerId) {
    alert("Employer ID not found in response");
    return;
}

    // Now fetch applications by employerId
    $.ajax({
    url: `http://localhost:8090/api/v1/job-applications/fromEmployer/${employerId}`,
    method: "GET",
    headers: {
    "Authorization": "Bearer " + token
},
    success: function (applications) {
    renderApplications(applications);
},
    error: function () {
    alert("Failed to load applications!");
}
});

},
    error: function () {
    alert("Failed to fetch user by email!");
}
});
} catch (err) {
    console.error("Token decoding error:", err);
}
}

    // Function to render applications to the table


    // Function to render applications to the table
    function renderApplications(applications) {
    const tbody = $("table tbody");
    tbody.empty(); // clear existing

    if (applications.length === 0) {
    tbody.append(`<tr><td colspan="6">No applications found.</td></tr>`);
    return;
}

    applications.forEach((app, index) => {
    // Get first letter of username
    const firstLetter = app.username ? app.username.charAt(0).toUpperCase() : '?';

    // Determine color class (1-3)
    const colorClass = (index % 3) + 1;

    const row = `
        <tr>
            <td>
                <div class="candidate-info">
                    <div class="round-initial round-color-${colorClass}">${firstLetter}</div>
                    <div>
                        <h6 class="mb-0">${app.username || 'N/A'}</h6>
                    </div>
                </div>
            </td>
            <td>${app.jobTitle || 'N/A'}</td>
            <td>${app.companyName || 'N/A'}</td>
            <td>${app.email || 'N/A'}</td>
            <td><span class="status-badge status-${app.status ? app.status.toLowerCase() : 'pending'}">${app.status || 'Pending'}</span></td>
            <td>
               <!-- Download Button -->
                <button onclick="downloadResume(${app.id})" class="btn btn-sm btn-light">
                  <i class="fas fa-download"></i> Resume
                </button>
                <button class="btn btn-sm btn-success action-btn"
                        data-id="${app.id}"
                        data-status="Approved"
                        title="Approve">
                    <i class="fas fa-check"></i>
                </button>
                <button class="btn btn-sm btn-danger action-btn"
                        data-id="${app.id}"
                        data-status="Rejected"
                        title="Reject">
                    <i class="fas fa-times"></i>
                </button>
            </td>
        </tr>
        `;
    tbody.append(row);
});

    // Attach event listeners to the new buttons
    attachActionListeners();
}

    // Function to attach event listeners
    function attachActionListeners() {
    // Download CV button
    $('.btn-light').off('click').on('click', function() {
        const candidateName = $(this).closest('tr').find('h6').text();
        alert(`Downloading ${candidateName}'s application`);
    });

    // Approve/Reject buttons
    $('.btn-success, .btn-danger').off('click').on('click', function() {
    const button = $(this);
    const applicationId = button.data('id');
    const newStatus = button.data('status');
    const row = button.closest('tr');

    // Show loading state
    button.prop('disabled', true);
    button.html('<i class="fas fa-spinner fa-spin"></i>');

    // Prepare the request data
    const requestData = {
    newStatus: newStatus
};

    // Make the PATCH request
    $.ajax({
    url: `http://localhost:8090/api/v1/job-applications/${applicationId}/status`,
    method: 'PATCH',
    contentType: 'application/json',
    headers: {
    "Authorization": "Bearer " + localStorage.getItem("authToken")
},
    data: JSON.stringify(requestData),
    success: function(response) {
    // Update the status in the table
    const statusBadge = row.find('.status-badge');
    statusBadge.removeClass('status-pending status-approved status-rejected')
    .addClass(`status-${newStatus.toLowerCase()}`)
    .text(newStatus);

    // Show success message
    Swal.fire({
    icon: 'success',
    title: 'Status Updated',
    text: `Application status changed to ${newStatus}`,
    timer: 2000,
    showConfirmButton: false,

});
    window.location.href = "mail-success.html"; // Redirect to normal dashboard

},
    error: function(xhr) {
    Swal.fire({
    icon: 'error',
    title: 'Error',
    text: xhr.responseJSON?.message || 'Failed to update status'
});
    window.location.href = "mail-success.html"; // Redirect to normal dashboard

},
    complete: function() {
    // Reset button state
    button.prop('disabled', false);
    button.html(newStatus === 'Approved' ? '<i class="fas fa-check"></i>' : '<i class="fas fa-times"></i>');
}
});
});
}

    // Function to load applications
    function loadApplications() {
    const token = localStorage.getItem("authToken");

    if (!token) {
    alert("User not authenticated!");
    return;
}

    try {
    const decoded = jwt_decode(token);
    const userEmail = decoded.email || decoded.sub;

    if (!userEmail) {
    alert("Email not found in token!");
    return;
}

    // Get user by email to fetch employerId
    $.ajax({
    url: `http://localhost:8090/api/v1/users/getByEmail`,
    method: "GET",
    headers: {
    "Authorization": "Bearer " + token,
    "Content-Type": "application/json"
},
    success: function(userData) {
    const employerId = userData.userId;

    if (!employerId) {
    alert("Employer ID not found in response");
    return;
}

    // Fetch applications by employerId
    $.ajax({
    url: `http://localhost:8090/api/v1/job-applications/fromEmployer/${employerId}`,
    method: "GET",
    headers: {
    "Authorization": "Bearer " + token
},
    success: function(applications) {
    renderApplications(applications);
},
    error: function() {
    alert("Failed to load applications!");
}
});
},
    error: function() {
    alert("Failed to fetch user by email!");
}
});
} catch (err) {
    console.error("Token decoding error:", err);
}
}

    // Initialize when page loads
    $(document).ready(function() {
    loadApplications();
});







    function downloadResume(applicationId) {
    console.log(applicationId+ "applcation idddd")
    $.ajax({
    url: 'http://localhost:8090/api/v1/job-applications/download-resume/' + applicationId,
    method: 'GET',
    xhrFields: {
    responseType: 'blob'
},
    success: function(data, status, xhr) {
    const disposition = xhr.getResponseHeader('Content-Disposition');
    const match = disposition && disposition.match(/filename="(.+)"/);
    const filename = match ? match[1] : 'resume.pdf';

    const url = window.URL.createObjectURL(data);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
},
    error: function(xhr) {
    alert("Resume download failed!");
}
});
}
