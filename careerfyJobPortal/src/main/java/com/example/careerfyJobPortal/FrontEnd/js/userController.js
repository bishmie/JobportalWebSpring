$(document).ready(function() {
    $('#loginForm').submit(function(e) {
        e.preventDefault();

        const email = $('#exampleInputEmail1').val();
        const password = $('#exampleInputPassword1').val();

        $.ajax({
            url: 'http://localhost:8090/login', // Endpoint
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ email: email, password: password }), // Send JSON data
            success: function(response) {
                console.log(response);
                if (response.success) {
                    alert('Login successful');
                } else {
                    alert('Login failed');
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });
});
