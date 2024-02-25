$(document).ready(function () {
    //set the desired static search status
    const searchStatus = 'DONE';
    //set the per page total result for serach by username method
    const perPageTotal = 1;

    $('input[name="searchType"]').change(function () {
        areaCleaner();
        if ($('#searchByUsername').is(':checked')) {
            $('#usernameInput').removeClass('d-none');
            $('#departmentAndDate').addClass('d-none');
        } else {
            $('#usernameInput').addClass('d-none');
            $('#departmentAndDate').removeClass('d-none');
        }
    });

    $('#searchButton').click(function () {
        let searchData = {};
        let searchUrl = '';
        let searchMethod = '';
        
        if ($('#searchByUsername').is(':checked')) {
            let username = $('#username').val();

            if(username=='') {
                alert("İstifadəçi adı daxil edin!");
                return;
            }

            searchData.username = username
            searchData.size = perPageTotal
            searchData.status = searchStatus
            // Retrieve the URL for username search
            searchUrl = $('#searchByUsername').data('url');
            searchMethod = 'GET';
        } else {
            let departmentName = $('#department').val();
            let startDate = formatDateToYYYYMMDD($('#startDate').val());
            let endDate = formatDateToYYYYMMDD($('#endDate').val());

            if(departmentName=='' || startDate=='' || endDate=='') {
                alert("Axtarış üçün bütün parametrlərin qeyd olunması vacibdir.");
                return;
            }

            searchData.departmentName = departmentName
            searchData.startDate  = startDate
            searchData.endDate    = endDate

            searchData = JSON.stringify(searchData)

                // Retrieve the URL for department search
            searchUrl = $('#searchByDepartment').data('url');
            searchMethod = 'POST';
        }

        ajaxRequester(searchMethod, searchUrl, searchData)

    });

    function ajaxRequester(searchMethod, searchUrl, searchData){
        areaCleaner();
        $.ajax({
            type: searchMethod,
            url: searchUrl,
            contentType: 'application/json',
            data: searchData,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", "Basic " + btoa('Tural:123456'));
            },
            success: function (response) {
                if ($('#searchByUsername').is(':checked')) {
                    handleUsernameSearchResponse(response);
                } else {
                    handleDepartmentSearchResponse(response);
                }
            },
            error: function (error) {
                console.error(error);
                // Handle errors
            }
        });
    }

    function areaCleaner(){
        $('#departmentTable').empty();
        $('#departmentTable').hide();
        $('#usernameTable tbody').empty();
        $('#usernameTable').hide();
        $('#usernamePaginator').html("");
    }

    // Function to format date to YYYY-MM-DD
    function formatDateToYYYYMMDD(dateString) {
        let nd = dateString.split(/\.|-/);
        return nd[0] + '-' + nd[1] + '-' + nd[2];
    }

    function isoDateToNormalDate(dateString){
        date = new Date(dateString);
        year = date.getFullYear();
        month = date.getMonth()+1;
        dt = date.getDate();

        if (dt < 10) {
            dt = '0' + dt;
        }
        if (month < 10) {
            month = '0' + month;
        }

        return dt+'.' + month + '.'+year;
    }

    function loadUsernamePage(pageNumber) {
        let searchData = {
            username: $('#username').val(),
            status: searchStatus,
            size: perPageTotal,
            page: pageNumber
        };

        let searchUrl = $('#searchByUsername').data('url');

        ajaxRequester("GET",searchUrl,searchData)
    }

    function handleUsernameSearchResponse(data) {
        // Check if there are no results
        if (data.items.length === 0) {
            alert('Axtarış üzrə nəticə yoxdur');
            return;
        }

        // Populate the username table
        populateUsernameTable(data);

        // Show the username table
        $('#usernameTable').show();

        // Update the paginator
        updateUsernamePaginator(data);
    }

    function updateUsernamePaginator(data) {
        // Clear existing paginator content
        $('#usernamePaginator').empty();

        // Add pagination controls if there is more than one page
        if (data.pageCount > 1) {
            for (let i = 1; i <= data.pageCount; i++) {
                $('#usernamePaginator').append('<button class="btn btn-secondary mr-2">' + i + '</button>');
            }
        }
    }

    $(document).on('click', '#usernamePaginator button', function () {
        let pageNumber = $(this).text();
        loadUsernamePage(pageNumber);
    });

    function handleDepartmentSearchResponse(data) {
        // Check if there are no results
        if (data.length === 0) {
            alert('Axtarış üzrə heç bir nəticə tapılmadı');
            return;
        }

        // Populate the department table
        populateDepartmentTable(data);

        // Show the department table
        $('#departmentTable').show();
    }

    function populateUsernameTable(data) {
        // Clear existing table content
        $('#usernameTable tbody').empty();

        // Iterate through the data and populate the username table
        $.each(data.items, function (index, item) {
            let row = '<tr>' +
                '<td>' + item.id + '</td>' +
                '<td>' + item.taskName + '</td>' +
                '<td>' + item.employeeName + '</td>' +
                '<td>' + item.taskStatus + '</td>' +
                '</tr>';

            $('#usernameTable tbody').append(row);
        });
    }

    function populateDepartmentTable(data) {
        // Clear existing table content
        $('#departmentTable').empty();

        // Iterate through the data and populate the department table
        $.each(data, function (index, item) {
            // Employee name row
            let employeeRow = '<tr style="border-top: 2px solid black">' +
                '<td colspan="7"><strong>Əməkdaş: </strong>' + item.username + '</td>' +
                '</tr>';
            $('#departmentTable').append(employeeRow);

            // Positions row
            let positionsRow = '<tr>' +
                '<td colspan="7"><strong>Vəzifə:</strong> ' + item.positionNames.join(', ') + '</td>' +
                '</tr>';
            $('#departmentTable').append(positionsRow);

            // Projects row
            if (item.projects && item.projects.length > 0) {
                let projectsRow = '<tr>' +
                    '<td colspan="7"><strong>Layihələr:</strong> ' + item.projects.map(project => project.projectName).join(', ') + '</td>' +
                    '</tr>';
                $('#departmentTable').append(projectsRow);
            }
            let taskRow = '<tr>' +
                '<td colspan="7"><strong>Tapşırıqlar:</strong></td>' + // Empty column for employee name
                '</tr>';
                taskRow += '<tr>' +
                   '<td></td>' +
                   '<td><strong>Tapşırıq</strong></td>' +
                   '<td><strong>Başlama tarixi</strong></td>' +
                   '<td><strong>Bitmə tarixi</strong></td>' +
                   '<td><strong>Vəziyyəti</strong></td>' +
                   '<td><strong>Yaradılma tarixi</strong></td>' +
                   '<td></td>' +
                '</tr>';
            // Tasks rows
            if (item.tasks && item.tasks.length > 0) {
                $.each(item.tasks, function (taskIndex, task) {
                     taskRow += '<tr>' +
                        '<td></td>' + // Empty column for employee name
                        '<td>' + task.name + '</td>' +
                        '<td>' + isoDateToNormalDate(task.startDate) + '</td>' +
                        '<td>' + isoDateToNormalDate(task.endDate) + '</td>' +
                        '<td>' + task.taskStatus + '</td>' +
                        '<td>' + isoDateToNormalDate(task.createdAt) + '</td>' +
                        '<td></td>' + // Empty column for positions and projects
                        '</tr>';
                });
            } else {
                // Add an empty row if there are no tasks
                 taskRow += '<tr>' +
                    '<td></td>' + // Empty column for employee name
                    '<td></td>' + // Empty column for task name
                    '<td></td>' + // Empty column for start date
                    '<td></td>' + // Empty column for end date
                    '<td></td>' + // Empty column for task status
                    '<td></td>' + // Empty column for positions and projects
                    '</tr>';
            }
            $('#departmentTable').append(taskRow);
        });
    }
});