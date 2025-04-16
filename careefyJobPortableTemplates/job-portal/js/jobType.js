$(document).ready(function () {
    
    loadCategories();
    let editingCategoryId = null;

    // Add Category
    $('#addCategory').click(function () {
        let categoryName = $('#categoryName').val().trim();
        if (categoryName === '') {
            alert("Please enter a category name.");
            return;
        }

        if (editingCategoryId) {
            updateCategory(editingCategoryId, categoryName);
        } else {
            addCategory(categoryName);
        }
    });

    // Add new Category
    function addCategory(categoryName) {
        $.ajax({
            url: 'http://localhost:8090/jobType/save',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ name: categoryName }),
            success: function () {
                $('#categoryName').val('');
                loadCategories();
            },
            error: function () {
                alert("Error adding category.");
            }
        });
    }

    // Load Categories
    function loadCategories() {
        $.ajax({
            url: 'http://localhost:8090/jobType/getAll',
            type: 'GET',
            success: function (categories) {
                let tableBody = $('#categoryTableBody');
                tableBody.empty();

                categories.forEach((category, index) => {
                    let row = `
              <tr>
                <td>${index + 1}</td>
                <td>${category.name}</td>
                <td class="action-btns">
                  <button class="btn btn-warning btn-sm editCategory" data-id="${category.id}" data-name="${category.name}">
                    <i class="fas fa-edit"></i> Edit
                  </button>
                  <button class="btn btn-danger btn-sm deleteCategory" data-id="${category.id}">
                    <i class="fas fa-trash"></i> Delete
                  </button>
                </td>
              </tr>
            `;
                    tableBody.append(row);
                });

                // Edit Category
                $('.editCategory').click(function () {
                    let id = $(this).data('id');
                    let name = $(this).data('name');
                    $('#categoryName').val(name); // Populate the input field with the category name
                    editingCategoryId = id; // Store the category ID for updating
                });

                // Delete Category
                $('.deleteCategory').click(function () {
                    let id = $(this).data('id');
                    deleteCategoryWithConfirmation(id);
                });
            },
            error: function () {
                alert("Error loading categories.");
            }
        });
    }

    // Delete Category with confirmation
    function deleteCategoryWithConfirmation(id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "Do you want to delete this category?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                deleteCategory(id);
            }
        });
    }

    // Delete Category
    function deleteCategory(id) {
        $.ajax({
            url: `http://localhost:8090/jobType/delete/${id}`,
            type: 'DELETE',
            success: function () {
                Swal.fire(
                    'Deleted!',
                    'The job category has been deleted.',
                    'success'
                );
                loadCategories();
            },
            error: function () {
                Swal.fire(
                    'Error!',
                    'There was an issue deleting the category.',
                    'error'
                );
            }
        });
    }

    // Update Category
    function updateCategory(id, newName) {
        $.ajax({
            url: `http://localhost:8090/jobType/update/${id}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({ name: newName }),
            success: function () {
                $('#categoryName').val('');
                editingCategoryId = null; // Reset the editing ID
                loadCategories();
            },
            error: function () {
                alert("Error updating category.");
            }
        });
    }
});