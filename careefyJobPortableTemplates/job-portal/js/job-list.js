// const serverUrl = 'http://localhost:8090';
//
// $(document).ready(function(){
//
// $.ajax({
//     url:"http://localhost:8090/jobs/getAll",
//     type:"GET",
//     success:(res)=>{
//         console.log(res);
//         loadJobPost(res);
//     },
//     error: function(error) {
//         console.error('Error loading messages:', error);
//     }
//
// })
//
// });
//
// let loadJobPost = (res) => {
//
//     $("#left-post").empty();
//     $("#right-post").empty();
//
//
//     if (!res || res.length === 0) {
//
//       $("#job-post-main-div").append('<div class="no-job-message">No Job data</div>');
//       $("#pagination").hide();
//       return;
//     }
//
//     res.forEach((job, index) => {
//         console.log(job.companyDescription+"jobId")
//
//       let data = `
//         <div class="single-job">
//           <div class="job-image">
//             <img class="jobCardImg" src="${serverUrl}/jobs/images/${job.logo}" alt="#">
//           </div>
//           <div class="job-content">
//             <h4><a href="job-details.html">${job.title}</a></h4>
//             <p>${job.description}</p>
//             <ul>
//            <li><i class="lni lni-website"></i><a href="#"> ${job.email}</a></li>
//             <li><i class="lni lni-dollar"></i> ${job.salary}</li>
//             <li><i class="lni lni-map-marker"></i> ${job.location}</li>
//
//             </ul>
//           </div>
//           <div class="job-button">
//             <ul>
//            <li><a href="job-details.html?jobId=${job.id}">Apply</a></li>
//               <li><span>full time</span></li>
//             </ul>
//           </div>
//         </div>
//       `;
//
//       if (index % 2 === 0) {
//         $("#left-post").append(data);
//       } else {
//         $("#right-post").append(data);
//       }
//
//     });
//   };
//
//
// // Trigger when user types or clicks the button
// $('#searchKeyword, #searchLocation').on('input', function () {
//     filterJobs();
// });
//
// $('#filterJobsBtn').on('click', function () {
//     filterJobs();
// });
//
// function filterJobs() {
//     let keyword = $('#searchKeyword').val().toLowerCase();
//     let location = $('#searchLocation').val().toLowerCase();
//
//     $('.single-job').each(function () {
//         let title = $(this).data('title');
//         let description = $(this).data('description');
//         let jobLocation = $(this).data('location');
//
//         let matchKeyword = keyword === '' || title.includes(keyword) || description.includes(keyword);
//         let matchLocation = location === '' || jobLocation.includes(location);
//
//         $(this).toggle(matchKeyword && matchLocation);
//     });
// }
//



const serverUrl = 'http://localhost:8090';

let allJobs = []; // Global variable to store all job data

$(document).ready(function(){
    // Fetch all job posts
    $.ajax({
        url: `${serverUrl}/jobs/getAll`,
        type: "GET",
        success: (res) => {
            console.log('Loaded Jobs:', res);
            allJobs = res; // Store the fetched jobs
            loadJobPost(res); // Load job posts on the page
        },
        error: function(error) {
            console.error('Error loading jobs:', error);
        }
    });

    // Trigger when user types or clicks the button
    $('#searchKeyword, #searchLocation').on('input', function () {
        filterJobs();
    });

    $('#filterJobsBtn').on('click', function () {
        filterJobs();
    });

    // Function to filter and display jobs based on keyword and location
    function filterJobs() {
        let keyword = $('#searchKeyword').val().toLowerCase().trim(); // Ensure trimming spaces
        let location = $('#searchLocation').val().toLowerCase().trim(); // Ensure trimming spaces

        // Debugging logs
        console.log('Filtering with keyword:', keyword, 'and location:', location);

        // Filter the jobs based on keyword and location
        let filteredJobs = allJobs.filter(job => {
            let matchKeyword = true;
            let matchLocation = true;

            // Check if keyword is provided
            if (keyword) {
                matchKeyword = job.title.toLowerCase().includes(keyword) || job.description.toLowerCase().includes(keyword);
            }

            // Check if location is provided
            if (location) {
                matchLocation = job.location.toLowerCase().includes(location);
            }

            // Debugging log to check the matching status
            console.log('Job:', job.title, 'Match Keyword:', matchKeyword, 'Match Location:', matchLocation);

            return matchKeyword && matchLocation;
        });

        console.log('Filtered Jobs:', filteredJobs); // See the filtered jobs

        // Load the filtered jobs to the UI
        loadJobPost(filteredJobs);
    }

    let loadJobPost = (res) => {
        $("#left-post").empty();
        $("#right-post").empty();

        // Check if the response is empty
        if (!res || res.length === 0) {
            $("#job-post-main-div").append('<div class="no-job-message">No Job data</div>');
            $("#pagination").hide();
            return;
        }

        res.forEach((job, index) => {
            console.log(job.companyDescription+"jobId");

            let data = `
            <div class="single-job">
                <div class="job-image">
                    <img class="jobCardImg" src="${serverUrl}/jobs/images/${job.logo}" alt="#">
                </div>
                <div class="job-content">
                    <h4><a href="job-details.html">${job.title}</a></h4>
                    <p>${job.description}</p>
                    <ul>
                        <li><i class="lni lni-website"></i><a href="#"> ${job.email}</a></li>
                        <li><i class="lni lni-dollar"></i> ${job.salary}</li>
                        <li><i class="lni lni-map-marker"></i> ${job.location}</li>
                    </ul>
                </div>
                <div class="job-button">
                    <ul>
                        <li><a href="job-details.html?jobId=${job.id}">Apply</a></li>
                        <li><span>full time</span></li>
                    </ul>
                </div>
            </div>
        `;

            if (index % 2 === 0) {
                $("#left-post").append(data);
            } else {
                $("#right-post").append(data);
            }
        });
    };

});
