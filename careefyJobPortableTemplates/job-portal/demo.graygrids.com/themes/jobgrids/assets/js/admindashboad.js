$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8090/api/v1/users/getAll',
        type: 'GET',
        success: function (response) {
            let users = response;
            let tableBody = $('#userTable tbody');
            tableBody.empty();

            users.forEach(function (user, index) {
                let row = `
            <tr> 
              <td>${index + 1}</td> 
              <td>${user.username}</td> 
              <td>${user.email}</td>
              <td>${user.accountType}</td>
            </tr>
          `;
                tableBody.append(row);
            });
        },
        error: function (error) {
            console.error('Error fetching users:', error);
        }
    });
});


$(document).ready(function () {

    $.ajax({
        url: 'http://localhost:8090/api/v1/users/totalUsers',
        type: 'GET',
        success: function (count) {
            $('#totalUsers').text(count.toLocaleString());
        },
        error: function (error) {
            console.error('Error fetching total users:', error);
            $('#totalUsers').text('N/A');
        }
    });
});


    $(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8090/api/v1/users/active-count', // adjust if needed
        method: 'GET',
        success: function (data) {
            $('#activeUserCount').text(data);
        },
        error: function (err) {
            console.error("Error fetching active user count:", err);
        }
    });
});


