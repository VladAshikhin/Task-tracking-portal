var LOGGED_IN_USER = getLoggedInUserId();

function disableButton(button) {
    $(button).prop('disabled', true);
}

function enableButton(button) {
    $(button).prop('disabled', false);
}

function clearFilterInput() {
    $(".filter-field").val('');
}

function isHidden(elem) {
    var isHidden = $(elem).css('display') === 'none';
    return isHidden;
}

function hideInactiveRowsInTop(rows) {
    $(rows).each(function (i, elem) {
        var activeCell = $(elem).find(".is-active-cell").text();
        if (activeCell === 'false') {
            $(elem).hide();
        }
    });
}

function hideInactiveRow(row) {
    var activeCell = $(row).find(".is-active-cell").text();
    if (activeCell === 'false') {
        $(row).hide();
    } else {
        $(row).show();
    }
}

/**
 * @returns number of users registered in db
 */
function getNumberOfUsers() {
    var url = '/users/number';
    var methodType = 'GET';
    var number;
    $.ajax({
        url: url,
        type: methodType,
        async: false,
        success: (function (response) {
            number = response;
        }),
        error: (function () {
            console.log('Unable to get number of registered users.');
        })
    });
    return number;
}

/**
 * @returns ID of currently logged in user
 */
function getLoggedInUserId() {
    var url = '/users/id';
    var methodType = 'GET';
    var id;
    $.ajax({
        url: url,
        type: methodType,
        async: false,
        success: (function (response) {
            id = response;
        }),
        error: (function () {
            console.log('Unable to get user.');
        })
    });
    return id;
}

/**
 * Sorts table using params:
 * @param rows to be sorted
 * @param order of format 'asc' of 'desc'
 * @param column name to be sorted
 */
function sortTable(rows, order, column) {
    var asc = order === 'asc';
    var n;
    if (column === 'id') {
        n = 0;
    } else if (column === 'priority') {
        n = 3;
    } else if (column === 'createdby') {
        n = 4
    } else if (column === 'date') {
        n = 5;
    }

    var table = $("#table");
    $(rows).sort(function (a, b) {
        if (asc) {
            return $('td:eq(' + n + ')', a).text().localeCompare($('td:eq(' + n + ')', b).text());
        } else {
            return $('td:eq(' + n + ')', b).text().localeCompare($('td:eq(' + n + ')', a).text());
        }
    }).appendTo(table);
}

/**
 * Validation of fields for adding data
 * When fields are empty or 'priority' !isNumeric => alerts
 */
function onAddCheck() {
    var subject = $("#subject-input").val();
    var message = $("#message-input").val();
    var priority = $("#priority-input").val();

    if (subject === "" || message === "" || priority === "") {
        alert('All fields should contain data!');
        return false;
    } else if (!$.isNumeric(priority)) {
        alert('Field \'priority\' should contain number!');
        return false;
    } else if (new Number(priority) < 1) {
        alert('Value of priority field can\'t be negative or equal to 0!');
        return false;
    } else {
        $(".add-input").reset();
    }
}

/**
 * Shows inactive rows when checkbox is checked
 * Hides inactive rows when checkbox is unchecked
 */
function initActiveRows() {
    var currentRows = $("#table").find(".content-row").get();
    var check = $(".onoffswitch-checkbox");
    $(currentRows).each(function (i, elem) {
        var currentRow = $(elem);
        hideInactiveRow(currentRow);

        $(".onoffswitch-checkbox").change(function () {
            if ($(check).prop('checked') === false) {
                hideInactiveRow(currentRow);
            } else {
                $(currentRow).show();
            }
        });
    });
}

/**
 * Gets difference in days between two days
 * @param dateValue is 2nd date to calculate difference with
 * @returns {number} of days between the two dates
 */
function getDiff(dateValue) {
    var date = new Date(); // today
    var today = date.getFullYear() + '-0' + (date.getMonth() + 1) + '-0' + date.getDate();
    var todayParsed = Date.parse(today);
    var dateValueParsed = Date.parse(dateValue);
    var diffbf = Math.round((todayParsed - dateValueParsed) / (1000 * 60 * 60 * 24)); // Difference bad format

    return Math.abs(diffbf);
}

/**
 * Adding the age of task to its creation date
 */
function populate() {
    $('#table > tbody > tr').each(function (i, elem) {

        var cell = $(elem).find(".created-cell");
        var cellIdValue = $(cell).attr('id');
        var date = cellIdValue.replace('created-on-cell-', ''); // Get date out of cellId
        var difference = getDiff(date);
        var newVal = difference + (difference === 1 ? ' day ' : ' days ') + ' ago on ' + date;

        $(cell).html(newVal);
    });
}

/**
 * Method for filtering data by 'Top tasks' and 'All tasks'
 * @param amount is 'top' or 'all'
 */
function showTable(event, amount) {
    $("#myonoffswitch").prop("checked", false);
    clearFilterInput();
    var currentRows = $("#table").find(".content-row").get();
    hideInactiveRowsInTop(currentRows);
    var quantity = getNumberOfUsers();

    if (amount === 'top') {
        $(".switch-label").hide();
        $(".onoffswitch").hide();
        $('#filter-head').hide();
        $(".filter-btn").hide();

        sortTable(currentRows, 'asc', 'priority');

        $(currentRows).each(function (i, elem) {
            var status = $(elem).find('.is-active-cell').text();

            if (i < quantity && status === 'true') {
                $(elem).show();
            } else if (status === 'false') {
                quantity++;
            } else {
                $(elem).hide();
            }
        });

    } else {
        $(".switch-label").show();
        $(".onoffswitch").show();
        $('#filter-head').hide();
        $(".filter-btn").show();

        $(currentRows).each(function (i, elem) {
            hideInactiveRow($(elem));
        });
    }
}

/**
 * Executed when 'Assign to me' button clicked
 * Sets the current row's 'userId' value to logged in user id
 */
function onAssignTask() {

    var button = $(this);
    var currentRow = $(button).closest('.content-row');
    var taskId = $(currentRow).find(".id-cell").text();
    var userIdCell = $(currentRow).find(".user-id-cell");

    var data = {
        "id": taskId
    };

    var url = '/tasks/assign';
    var methodType = 'POST';

    $.ajax({
        url: url,
        type: methodType,
        crossDomain: true,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: (function (response) {
            alert('Task assigned successfully!');
            $(userIdCell).html(response.toString());
            disableButton(button);
        }),
        error: (function () {
            alert('Cannot update!');
        })
    })
}

/**
 * Executed when 'Close task' button clicked
 * Asks for the close message and sets row status = 'false'
 */
function onCloseTask() {

    var button = $(this);
    var row = $(button).closest('.content-row');
    var taskId = $(row).find(".id-cell").text();
    var inactiveRowsHidden = $("#myonoffswitch").prop('checked') === false;
    var assignedCell = $(row).find(".user-id-cell").text();

    if (assignedCell !== LOGGED_IN_USER) {
        alert('You have to assign the task to yourself before closing it!');
        return false;
    }

    var closeMessage = prompt("Please specify the details of the work done:", "Done in time");
    if (closeMessage === null || closeMessage === "" || closeMessage === " ") {
        alert('Close message can\'t be empty!');
        return false;
    } else if (closeMessage.length < 2) {
        alert('Too short close message!');
        return false;
    }

    var data = {
        "id": taskId,
        "closeMessage": closeMessage
    };

    var url = '/tasks/close';
    var methodType = 'POST';

    $.ajax({
        url: url,
        type: methodType,
        crossDomain: true,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: (function () {
            alert('Task closed successfully!');
            $(row).find(".close-message-cell").html(closeMessage);
            $(row).find(".is-active-cell").html('false');
            disableButton(button);
            if (inactiveRowsHidden) {
                $(row).hide();
            }
        }),
        error: (function () {
            alert('Cannot update!');
        })
    })
}

function onFilterButton() {
    var filter = $('#filter-head');
    if (isHidden(filter)) {
        $(filter).show();
    } else {
        $(filter).hide();
        clearFilterInput();
        showTable(event, 'all');
    }
}

/**
 * Executed while filtering data
 * Triggered on each keyup
 */
function filter() {
    $(".filter-field").on("keyup", function () {

        var value = $(this).val();
        $("#table .content-row").each(function (index) {
            if (index !== 0) {
                var row = $(this);
                //filters tds that match indexOf check
                var matches = row.find('.table-content-cell').filter(function (ix, item) {
                    var input = $(item).text().toUpperCase();
                    return input.indexOf(value.toUpperCase()) > -1;
                });

                //if matches exist then show, else hide
                if (matches.length !== 0) {
                    $(row).show();
                }
                else {
                    $(row).hide();
                }
            }
        });
    });
}

function init() {
    var currentRows = $("#table").find(".content-row").get();
    $(currentRows).each(function (i, elem) {
        var assignedVal = $(elem).find(".user-id-cell").text();
        var assignButton = $(elem).find(".assign");
        var activeVal = $(elem).find(".is-active-cell").text();
        var closeMsgVal = $(elem).find(".close-message-cell").text();
        var closeButton = $(elem).find(".close-task-btn");

        if (assignedVal === null || assignedVal === "" || assignedVal === "0") {
            enableButton(assignButton);
        } else {
            disableButton(assignButton);
        }
        if (activeVal === "true" || closeMsgVal === null || closeMsgVal === "") {
            enableButton(closeButton);
        } else {
            disableButton(closeButton);
        }
    });
}

$(document).ready(init);
$(document).ready(populate);
$(document).ready(initActiveRows);
$(document).ready(filter);
$(document).ready(showTable(event, 'top'));
$(document).on('click', '.filter-btn', onFilterButton);
$(document).on('click', '.add-btn', onAddCheck);
$(document).on('click', '.close-task-btn', onCloseTask);
$(document).on('click', '.assign', onAssignTask);