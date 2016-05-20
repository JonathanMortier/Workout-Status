$( document ).ready(function() {

    $('#actionToShowUserDetails').click(function () {

        var selectorShowUserDetails = $('#showUserDetails');

        var showUserDisplay = selectorShowUserDetails.css('display');

        if ( showUserDisplay == 'none') {
            selectorShowUserDetails.show();
            $('#actionToShowUserDetails').html('<span class="glyphicon glyphicon-user"></span>Hide User Details');
        }
        else {
            selectorShowUserDetails.hide();
            $('#actionToShowUserDetails').html('<span class="glyphicon glyphicon-user"></span>Show User Details');
        }
    });

    var state = localStorage.getItem("stateButtonShow");
    var id =localStorage.getItem("machineId");

    if (state == 'showButtons') {
        console.log("= 'showButtons'");
        showButtons(id);
    }
    else if (state == 'hideButtons') {
        console.log("= 'hideButtons'");
        hideButtons(id);
    }
    else {
        console.log("= null");
    }

});

function showButtons(id) {
    $('#updateButton'+id).hide();
    $('#listButtons'+id).show();

    localStorage.setItem("stateButtonShow", "showButtons");
    localStorage.setItem("machineId", id);
}

function hideButtons(id) {
    $('#lock'+id).parent().hide();
    $('#updateButton'+id).show();

    localStorage.setItem("stateButtonShow", "hideButtons");
    localStorage.setItem("machineId", id);
}