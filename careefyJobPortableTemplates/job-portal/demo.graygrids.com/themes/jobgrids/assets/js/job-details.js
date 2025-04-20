
    const serverUrl = 'http://localhost:8090';


    $(document).ready(function () {
    // Get jobId from URL (example: ?jobId=1)
    const urlParams = new URLSearchParams(window.location.search);
    const jobId = urlParams.get("jobId");
    console.log(jobId);

    if (jobId) {
    $.ajax({
    url: `http://localhost:8090/api/v1/jobs/get/${jobId}`,
    type: "GET",
    contentType: 'application/json',

    success: function (job) {
    console.log(job)


    const jobTypeMap = {
    1: "Full Time",
    2: "Part Time",
    3: "Contract",
    4: "Internship",
    5: "Office"
};

    // Update the Job Title
    $(".title").text(job.title);

    $("#company-email").text(job.companyName);

    // // Update the Company Name & Link
    // $(".meta strong a").text(job.email);
    // $(".meta strong a").attr("href", `mailto:${job.email}`);

    // Update the Job Location
    $(".meta li:nth-child(2)").html(`<i class="lni lni-map-marker"></i> ${job.location}`);

    // Update the Salary
    $(".salary-range").text(formatSalary(job.salary));
    $("#emp-status-btn").text(jobTypeMap[job.type]);


    // Update Job Description
    $("#job-description").text(job.description);

    $("#job-responsibilities").text(job.responsibilities);

    // Update Job Description
    $("#job-experience").text(job.experience);


    $("#job-status").text(jobTypeMap[job.type]);

    $("#job-overview-location").text(job.location);

    $("#job-overview-salary").text(formatSalary(job.salary));

    $("#job-deadline").text(job.deadline);





    // Update the Company Logo
    $(".company-logo img").attr("src", `${serverUrl}/api/v1/jobs/images/${job.logo}`);

},
    error: function (xhr, status, error) {
    console.error("Error fetching job details:", error);
}
});
}
});

    function formatSalary(salary) {
    return 'LKR ' + Number(salary).toLocaleString('en-LK', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
});
}


    // Initialize Bootstrap Modal
    var applyNowBtn = document.getElementById("applyNowBtn");
    var applyModal = new bootstrap.Modal(document.getElementById('applyModal'));

    applyNowBtn.addEventListener("click", function() {
    applyModal.show();
});







    $(document).ready(function () {
    const token = localStorage.getItem("authToken"); // Get token
    console.log("JWT Token:", token);  // Log the token to see if it's valid

    if (!token) {
    alert("User not authenticated!");
    return;
}

    try {
    const decoded = jwt_decode(token);
    console.log("Decoded Token:", decoded);
    const userEmail = decoded.email || decoded.sub;
    console.log("User Email:", userEmail);

    if (!userEmail) {
    alert("Email not found in token!");
    return;
}

    // Get User ID
    $.ajax({
    url: `http://localhost:8090/api/v1/users/getByEmail`,
    method: "GET",
    headers: {
    "Authorization": "Bearer " + token,
    "Content-Type": "application/json"
},
    success: function (userData) {
    console.log("User data from API:", userData);
    const userId = userData.userId;

    if (!userId) {
    alert("User ID not found in the response");
    return;
}

    console.log("User ID:", userId);

    // Form Submission
    $('#applyForm').submit(function (e) {
    e.preventDefault();
    console.debug("[DEBUG] Form submission initiated");

    const urlParams = new URLSearchParams(window.location.search);
    const jobId = urlParams.get("jobId");
    console.log(jobId)

    // Collect form data
    const formData = {
    jobId:jobId,
    userId: userId, // Make sure this is defined from earlier code
};

    console.log("Form Data:========", formData);

    // Validate required fields
    const missingFields = Object.entries(formData)
    .filter(([key, value]) => !value && key !== 'userId') // userId might come from elsewhere
    .map(([key]) => key);

    if (missingFields.length > 0) {
    Swal.fire({
    icon: 'warning',
    title: 'Missing Information',
    html: `Please fill in: <strong>${missingFields.join(', ')}</strong>`
});
    return;
}

    // File handling
    const fileInput = document.getElementById("resume");
    const file = fileInput.files[0];

    if (!file) {
    Swal.fire('Warning', 'Please select a company logo/image', 'warning');
    return;
}

    // Prepare FormData exactly as backend expects
    const multipartFormData = new FormData();
    multipartFormData.append("jobApplication", JSON.stringify(formData));

    // Backend expects List<MultipartFile> so we need to send array
    multipartFormData.append("files", file); // Note: using "files" as parameter name

    console.debug("[DEBUG] Sending data:", {
    formData: formData,
    fileName: file.name
});

    // AJAX Request
    $.ajax({
    url: 'http://localhost:8090/api/v1/job-applications/add',
    type: 'POST',
    headers: {
    "Authorization": "Bearer " + token
},
    processData: false,
    contentType: false,
    data: multipartFormData,
    beforeSend: function() {
    Swal.fire({
    title: 'Processing...',
    allowOutsideClick: false,
    didOpen: () => Swal.showLoading()
});
},
    success: function(response, status, xhr) {
    Swal.fire({
    icon: 'success',
    title: 'Job Application saved!',
    text: 'Your job has been successfully posted',
    timer: 2000
}).then(() => {
    window.location.href = '../../../index.html'; // Redirect after success
});
},
    error: function(xhr) {
    let errorMsg = "An error occurred while posting the job";

    try {
    // Try to parse backend error response if it's JSON
    const errorResponse = JSON.parse(xhr.responseText);
    errorMsg = errorResponse.message || errorMsg;
} catch (e) {
    // If not JSON, show raw response (truncated)
    errorMsg = xhr.responseText ?
    xhr.responseText.substring(0, 100) :
    "Server error (500)";
}

    Swal.fire({
    icon: 'error',
    title: 'Posting Failed',
    html: `<strong>Error ${xhr.status}</strong><br>${errorMsg}`
});
}
});
});

},
    error: function () {
    alert("User not found");
}
});

} catch (error) {
    console.error("Error:", error);
    alert("Error retrieving user data!");
}
});


