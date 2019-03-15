var LOGGED_IN_USER = getLoggedInUserId();

    function disableButton(button) {
    $(button).prop('disabled', true);
}

function enableButton(button) {
    $(button).prop('disabled', false);
}

function clearFilterInputs() {
$(".fil-id").val('');
$('.filter-sub').val('');
$('.filter-mes').val('');
$('.filter-pri').val('');
$('.filter-creby').val('');
$('.filter-creon').val('');
$('.filter-uid').val('');
$('.filter-act').val('');
$('.filter-clmes').val('');
}

function isHidden(elem) {
        var isHidden = $(elem).css('display') === 'none';
    /*if ($(elem).css('display') === 'none') {
        return true;
    } else {
        return false;
    }*/
    return isHidden;
}

// Used only in 'Top tasks' tab
// as it hides inactive but doesn't show active
function hideInactiveRowsInTop(rows) {
    $(rows).each(function(i, elem) {
        var activeCell = $(elem).find(".is-active-cell").text();
        if(activeCell === 'false') {
            $(elem).hide();
        }
    });
}

function hideInactiveRow(row) {
    var activeCell = $(row).find(".is-active-cell").text();
    if(activeCell === 'false') {
        $(row).hide();
    } else {
        $(row).show();
    }
}

function getNumberOfUsers() {
    var url = '/users/number';
    var methodType = 'GET';
    var number;
        $.ajax({
        url: url,
        type: methodType,
        async: false,
        success: (function(response) {
            number = response;
            console.log('Number of users is: ' + number);
        }),
        error: (function (){
            console.log('Unable to get number of registered users.');
        })
    });
    return number;
}

function getLoggedInUserId() {
    var url = '/users/id';
    var methodType = 'GET';
    var id;
    $.ajax({
        url: url,
        type: methodType,
        async: false,
        success: (function(response) {
            id = response;
        }),
        error: (function (){
            console.log('Unable to get user.');
        })
    });
    return id;
}

// Sorts table by ID
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
    //table.find('.content-row').
    $(rows).sort(function(a, b) {
        if (asc) {
            return $('td:eq('+ n +')', a).text().localeCompare($('td:eq('+ n +')', b).text()); // 'td:first'
        } else {
            return $('td:eq('+ n +')', b).text().localeCompare($('td:eq('+ n +')', a).text());
        }
    }).appendTo(table);

}

function sortByColumn3And5(row1, row2) {
    var v1 = $(row1).find("td:eq(3)").text();
    var v2 = $(row2).find("td:eq(3)").text();
    var r = v1 - v2;
    if (r === 0) {
        // we have a tie in column 1 values, compare column 2 instead
        v1 = $(row1).find("td:eq(5)").text();
        v2 = $(row2).find("td:eq(5)").text();
        if (v1 < v2) {
            r = -1;
        } else if (v1 > v2) {
            r = 1;
        } else {
            r = 0;
        }
    }
    return r;
}



// Validation of fields while adding a new task
function onAddCheck() {
    var subject = $("#subject-input").val();
    var message = $("#message-input").val();
    var priority = $("#priority-input").val();

    if(subject === "" || message === "" || priority === "") {
        alert('All fields should contain data!');
        return false;
    } else if (!$.isNumeric(priority)) {
        alert('Field \'priority\' should contain number!');
        return false;
    } else {
        $(".add-input").reset();
    }
}

// 'Show inactive' checkbox //
function initActiveRows() {
    var currentRows = $("#table").find(".content-row").get();
    var check = $(".onoffswitch-checkbox");
    $(currentRows).each(function(i, elem) {
        var currentRow = $(elem);
       hideInactiveRow(currentRow);

        $(".onoffswitch-checkbox").change(function() {
            if($(check).prop('checked') === false) {

                hideInactiveRow(currentRow);
            } else {
                $(currentRow).show();
            }
        });
    });
}

// Get difference between two dates
function getDiff(dateValue){
    var date = new Date(); // today
    var today = date.getFullYear()+'-0'+(date.getMonth()+1)+'-0'+date.getDate();
    var todayParsed = Date.parse(today);
    var dateValueParsed = Date.parse(dateValue);
    var diffbf = Math.round((todayParsed-dateValueParsed)/(1000*60*60*24)); // Difference bad format

    return Math.abs(diffbf);
}

// Adding the age of a task
function populate() {
    $('#table > tbody > tr').each(function(i, elem){

        var cell = $(elem).find(".created-cell"); // Finds a cell
        var cellIdValue = $(cell).attr('id'); // Get its id
        var date = cellIdValue.replace('created-on-cell-', ''); // Get date out of id
        var difference = getDiff(date);
        var newVal = difference + (difference === 1 ? ' day ' : ' days ') + ' ago on ' + date; // Put new value 'Created x days ago on {date}'

        $(cell).html(newVal);
    });
}


// Method for filtering data by 'Top tasks' and 'All tasks'
function showTable(event, amount) {
    var currentRows = $("#table").find(".content-row").get();
    var quantity = getNumberOfUsers();

 // Hide inactive rows always
    //Hide Switch
    // Hide filters
    // While adding tasks in this mode - check the positioning


    if(amount === 'top') {
        $(".switch-label").hide();
        $(".onoffswitch").hide();
        $(".filter-btn").hide();

        sortTable(currentRows, 'desc', 'date'); // sort rows by date
        hideInactiveRowsInTop(currentRows); // hide inactive rows
        //$(currentRows).hide().slice(0, quantity).show(); // show rows from 0 to 'quantity'


    } else {
        $(".switch-label").show();
        $(".onoffswitch").show();
        $(".filter-btn").show();
        $(currentRows).each(function(i, elem){
            hideInactiveRow($(elem));
        });
        sortTable(currentRows, 'asc', 'id');
    }
}

// Executed on 'Assign to me' button clicked
function onAssignTask() {

    var button = $(this);
    var currentRow = $(button).closest('.content-row');
    var taskId = $(currentRow).find(".id-cell").text();
    var userIdCell = $(currentRow).find(".user-id-cell");

    var data = {
        "id" : taskId
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
        success: (function(response) {
            alert('Task assigned successfully!');
            $(userIdCell).html(response.toString());
            disableButton(button);
        }),
        error: (function (){
            alert('Cannot update!');
        })
    })
}

// On 'Close task" click
function onCloseTask() {

    var button = $(this);
    var row = $(button).closest('.content-row');
    var taskId = $(row).find(".id-cell").text();
    var inactiveRowsHidden = $(".onoffswitch-checkbox").prop('checked') === false;
    var assignedCell = $(row).find(".user-id-cell").text();

    if (assignedCell !== LOGGED_IN_USER) {
        alert('You have to assign the task to yourself before closing it!');
        return false;
    }

    var closeMessage = prompt("Please specify the details of the work done:", "Done in time");
    if (closeMessage === null || closeMessage === "" || closeMessage === " ") {
        return;
    }

    var data = {
       "id": taskId,
        "closeMessage" : closeMessage
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
        success: (function() {
            alert('Task closed successfully!');
            $(row).find(".close-message-cell").html(closeMessage);
            $(row).find(".is-active-cell").html('false');
            disableButton(button);
            if (inactiveRowsHidden) {
                $(row).hide();
            }
        }),
        error: (function (){
            alert('Cannot update!');
        })
    })
}

function onFilterButton(){
        var filterCells = $('#filter-head');
        if (isHidden(filterCells)) {
            $(filterCells).show()
        } else {
            $(filterCells).hide();
            clearFilterInputs();
        }
}

function filterTable(event) {
    var filter = $(this).val().toUpperCase();
    console.log('filter is: .' + filter + '.');
    console.log('filter field val is: .' + $('.filter-id').val() + '.');
    var rows = $("#table").find('.content-row').get();

    for (var i = 0; i < rows.length; i++) {
        var colOne = rows[i].cells[0].textContent.toUpperCase();
        console.log(colOne);
        var colTwo = rows[i].cells[1].textContent.toUpperCase();
        console.log(colTwo);
        var colThree = rows[i].cells[2].textContent.toUpperCase();
        var colFour = rows[i].cells[3].textContent.toUpperCase();
        var colFive = rows[i].cells[4].textContent.toUpperCase();
        var colSix = rows[i].cells[5].textContent.toUpperCase();
        var colSeven = rows[i].cells[6].textContent.toUpperCase();
        var colEight = rows[i].cells[7].textContent.toUpperCase();
        var colNine = rows[i].cells[8].textContent.toUpperCase();

        if (colOne.indexOf(filter) > -1 || colTwo.indexOf(filter) > -1 ||
            colThree.indexOf(filter) > -1 || colFour.indexOf(filter) > -1 ||
            colFive.indexOf(filter) > -1 || colSix.indexOf(filter) > -1 ||
            colSeven.indexOf(filter) > -1 || colEight.indexOf(filter) > -1 ||
            colNine.indexOf(filter) > -1) {
console.log('index');
            $(rows[i]).show();
        } else {
            console.log('not index')
            $(rows[i]).hide();
        }
    }
}

    $('.fil-id, .filter-sub, .filter-mes, .filter-pri, .filter-creby, .filter-creon, .filter-uid, .filter-act, .filter-clmes').each(function (i, elem) {
        $(elem).on('keyup', filterTable);
    });



function init() {
    var currentRows = $("#table").find(".content-row").get();
    $(currentRows).each(function(i, elem){
        var assignedVal = $(elem).find(".user-id-cell").text();
        var assignButton = $(elem).find(".assign"); //  try find
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
$(document).on('click', '.filter-btn', onFilterButton);
//$(document).ready(sortTable('asc', 'id'));
$(document).on('click', '.add-btn', onAddCheck);
$(document).on('click', '.close-task-btn', onCloseTask);
$(document).on('click', '.assign', onAssignTask);