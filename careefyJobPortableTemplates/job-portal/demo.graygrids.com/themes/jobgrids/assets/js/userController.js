// $(document).ready(function () {
//     $('#signupForm').submit(function (e) {
//         e.preventDefault();
//
//         var username = $('#registerUsername').val();
//         var email = $('#registerEmail').val();
//         var password = $('#registerPassword').val();
//         var accountType = $("input[name='accountType']:checked").val();
//
//         var userData = {
//             username: username,
//             email: email,
//             password: password,
//             accountType: accountType
//         };
//
//         $.ajax({
//             url: 'http://localhost:8090/users/register',
//             type: 'POST',
//             contentType: 'application/json',
//             data: JSON.stringify(userData),
//             success: function (response) {
//                 // Show SweetAlert on success
//                 Swal.fire({
//                     title: 'Success!',
//                     text: 'User registered successfully!',
//                     icon: 'success',
//                     confirmButtonText: 'OK'
//                 });
//             },
//             error: function (xhr, status, error) {
//                 // Show SweetAlert on error
//                 Swal.fire({
//                     title: 'Error!',
//                     text: 'There was an error: ' + xhr.responseText,
//                     icon: 'error',
//                     confirmButtonText: 'Try Again'
//                 });
//             }
//         });
//     });
// });
//



$(document).ready(function () {
    $('#signupForm').submit(function (e) {
        e.preventDefault();

        var username = $('#registerUsername').val().trim();
        var email = $('#registerEmail').val().trim();
        var password = $('#registerPassword').val().trim();
        var accountType = $("input[name='accountType']:checked").val();

        if (!username || !email || !password || !accountType) {
            Swal.fire("Oops...", "Please fill all fields!", "warning");
            return;
        }
        if (!validateEmail(email)) {
            Swal.fire("Invalid Email", "Enter a valid email address.", "error");
            return;
        }
        if (password.length < 6) {
            Swal.fire("Weak Password", "Password must be at least 6 characters.", "error");
            return;
        }

        var userData = {
            username: username,
            email: email,
            password: password,
            accountType: accountType
        };

        $.ajax({
            url: 'http://localhost:8090/users/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(userData),
            success: function (response) {
                console.log("Response:", response);
                console.log("token", response.data);

                if (response.code === 201) {
                    // Save token to localStorage
                    localStorage.setItem("token", response.data.token);

                    Swal.fire({
                        icon: "success",
                        title: "Registration Successful",
                        text: "Redirecting...",
                        showConfirmButton: false,
                        timer: 2000
                    }).then(() => {
                        if (accountType === "EMPLOYER") {
                            window.location.href = "employerdashboard.html";
                        } else if (accountType === "CANDIDATE") {
                            window.location.href = "index.html";
                        } else {
                            // Default redirect if something unexpected happens
                            window.location.href = "dashboard.html";
                        }

                    });
                } else {
                    Swal.fire("Registration Failed", response.message, "error");
                }
            },
            error: function (xhr) {
                console.log("Error:", xhr.responseText);
                Swal.fire("Error!", "There was an error: " + xhr.responseText, "error");
            }
        });
    });

    function validateEmail(email) {
        let regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    }
});




// $(document).ready(function () {
//     $("#loginForm").submit(function (event) {
//         event.preventDefault();
//
//         let email = $("#loginEmail").val().trim();
//         let password = $("#loginPassword").val().trim();
//
//         if (!email || !password) {
//             Swal.fire("Oops...", "Please fill all fields!", "warning");
//             return;
//         }
//         if (!validateEmail(email)) {
//             Swal.fire("Invalid Email", "Enter a valid email address.", "error");
//             return;
//         }
//         if (password.length < 6) {
//             Swal.fire("Weak Password", "Password must be at least 6 characters.", "error");
//             return;
//         }
//
//         console.log("Sending:", { email, password });
//
//         $.ajax({
//             url: "http://localhost:8090/api/v1/auth/authenticate",
//             type: "POST",
//             contentType: "application/json",
//             data: JSON.stringify({ email, password }),
//             success: function (response) {
//                 console.log("Response:", response);
//
//                 if (response.code === 201 && response.data && response.data.token) {
//                     // Token eka localStorage ekata save karanawa
//                     localStorage.setItem("authToken", response.data.token);
//
//                     Swal.fire({
//                         icon: "success",
//                         title: "Login Successful",
//                         text: "Redirecting...",
//                         showConfirmButton: false,
//                         timer: 2000
//                     }).then(() => {
//                         window.location.href = "dashboard.html";
//                     });
//                 } else {
//                     Swal.fire("Login Failed", "Invalid email or password.", "error");
//                 }
//             },
//             error: function (xhr) {
//                 console.log("Error:", xhr.responseText);
//                 Swal.fire("Login Failed", "Check your email and password.", "error");
//             }
//         });
//     });
//
//     function validateEmail(email) {
//         let regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//         return regex.test(email);
//     }
// });




$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault();

        let email = $("#loginEmail").val().trim();
        let password = $("#loginPassword").val().trim();

        if (!email || !password) {
            Swal.fire("Oops...", "Please fill all fields!", "warning");
            return;
        }
        if (!validateEmail(email)) {
            Swal.fire("Invalid Email", "Enter a valid email address.", "error");
            return;
        }
        if (password.length < 6) {
            Swal.fire("Weak Password", "Password must be at least 6 characters.", "error");
            return;
        }

        console.log("Sending:", { email, password });


        $.ajax({
            url: "http://localhost:8090/api/v1/auth/authenticate",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ email, password }),
            success: function (response) {
                console.log("Response:", response);
                console.log("Token saved:", localStorage.getItem("authToken"));


                if (response.code === 201 && response.data && response.data.token) {
                    // Token eka localStorage ekata save karanawa
                    localStorage.setItem("authToken", response.data.token);

                    // Check if the user is the admin
                    if (email === "careerfylanka@gmail.com") {
                        Swal.fire({
                            icon: "success",
                            title: "Login Successful",
                            text: "Redirecting to Admin Dashboard...",
                            showConfirmButton: false,
                            timer: 2000
                        }).then(() => {
                            window.location.href = "admindashboard.html"; // Redirect to admin dashboard
                        });
                    } else {
                        Swal.fire({
                            icon: "success",
                            title: "Login Successful",
                            text: "Redirecting...",
                            showConfirmButton: false,
                            timer: 2000
                        }).then(() => {
                            // window.location.href = "dashboard.html"; // Redirect to normal dashboard


                            const token = response.data.token;
                            const decoded = parseJwt(token);
                            console.log("Decoded token:", decoded); // <-- MEKA PRINT KARALA BALANNA!

                            if (decoded && decoded.role) { // Change to 'role'
                                const role = decoded.role.toUpperCase(); // Use 'role' instead of 'accountType'

                                if (role === "EMPLOYER") {
                                    window.location.href = "employerdashboard.html";
                                } else if (role === "CANDIDATE") {
                                    window.location.href = "employerdashboard.html";
                                } else {
                                    window.location.href = "dashboard.html";
                                }
                            } else {
                                console.log("role not found in token");
                                // window.location.href = "dashboard.html";
                            }
                        });
                    }
                } else {
                    Swal.fire("Login Failed", "Invalid email or password.", "error");
                }
            },
            error: function (xhr) {
                console.log("Error:", xhr.responseText);
                Swal.fire("Login Failed", "Check your email and password.", "error");
            }
        });
    });

    function validateEmail(email) {
        let regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    }
});



function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload);
    } catch (e) {
        console.error("Invalid token:", e);
        return null;
    }
}




// $(document).ready(function () {
//     $("#loginForm").submit(function (event) {
//         event.preventDefault();
//
//         let email = $("#loginEmail").val().trim();
//         let password = $("#loginPassword").val().trim();
//
//         if (!email || !password) {
//             Swal.fire("Oops...", "Please fill all fields!", "warning");
//             return;
//         }
//         if (!validateEmail(email)) {
//             Swal.fire("Invalid Email", "Enter a valid email address.", "error");
//             return;
//         }
//         if (password.length < 6) {
//             Swal.fire("Weak Password", "Password must be at least 6 characters.", "error");
//             return;
//         }
//
//         console.log("Sending:", { email, password });
//
//         $.ajax({
//             url: "http://localhost:8090/api/v1/auth/authenticate",
//             type: "POST",
//             contentType: "application/json",
//             data: JSON.stringify({ email, password }),
//             success: function (response) {
//                 console.log("Response:", response);
//                 if (response.code === 201) {
//                     Swal.fire({
//                         icon: "success",
//                         title: "Login Successful",
//                         text: "Redirecting...",
//                         showConfirmButton: false,
//                         timer: 2000
//                     }).then(() => {
//                         window.location.href = "dashboard.html";
//                     });
//                 } else {
//                     Swal.fire("Login Failed", "Invalid email or password.", "error");
//                 }
//             },
//             error: function (xhr) {
//                 console.log("Error:", xhr.responseText);
//                 Swal.fire("Login Failed", "Check your email and password.", "error");
//             }
//         });
//     });
//
//     function validateEmail(email) {
//         let regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//         return regex.test(email);
//     }
// });
//
//
//
//
